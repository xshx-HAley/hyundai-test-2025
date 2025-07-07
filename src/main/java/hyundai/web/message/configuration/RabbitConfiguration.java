package hyundai.web.message.configuration;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RabbitConfiguration {// 1) 사용할 Exchange 이름
    public static final String MESSAGE_EXCHANGE = "hyundai.message.exchange";

    // 2) Queue 이름
    public static final String KAKAO_QUEUE = "hyundai.message.kakao";
    public static final String SMS_QUEUE   = "hyundai.message.sms";

    // 3) Routing Key (필요시)
    public static final String KAKAO_ROUTING_KEY = "message.kakao";
    public static final String SMS_ROUTING_KEY   = "message.sms";

    @Bean
    public RateLimiter kakaoRateLimiter() {
        return RateLimiter.of("kakao", RateLimiterConfig.custom()
                .limitForPeriod(100)
                .limitRefreshPeriod(Duration.ofMinutes(1))
                .timeoutDuration(Duration.ofSeconds(5))
                .build()
        );
    }
    // --- Exchange ---
    @Bean
    public TopicExchange messageExchange() {
        return ExchangeBuilder
                .topicExchange(MESSAGE_EXCHANGE)
                .durable(true)
                .build();
    }

    // --- Queues ---
    @Bean
    public Queue kakaoQueue() {
        return QueueBuilder
                .durable(KAKAO_QUEUE)
                .build();
    }

    @Bean
    public Queue smsQueue() {
        return QueueBuilder
                .durable(SMS_QUEUE)
                .build();
    }

    // --- Bindings ---
    @Bean
    public Binding kakaoBinding(Queue kakaoQueue, TopicExchange messageExchange) {
        return BindingBuilder
                .bind(kakaoQueue)
                .to(messageExchange)
                .with(KAKAO_ROUTING_KEY);
    }

    @Bean
    public Binding smsBinding(Queue smsQueue, TopicExchange messageExchange) {
        return BindingBuilder
                .bind(smsQueue)
                .to(messageExchange)
                .with(SMS_ROUTING_KEY);
    }

    // --- RabbitTemplate (메시지 발송용) ---
    //    필요하다면 Jackson2JsonMessageConverter 등 컨버터 설정도 가능
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        // JSON 직렬화를 쓰고 싶다면:
        // template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }
}