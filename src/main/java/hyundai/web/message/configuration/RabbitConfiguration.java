package hyundai.web.message.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {// 1) 사용할 Exchange 이름
    public static final String MESSAGE_EXCHANGE = "hyundai.message.exchange";

    // 2) Queue 이름
    public static final String KAKAO_QUEUE = "hyundai.message.kakao";
    public static final String SMS_QUEUE   = "hyundai.message.sms";

    // 3) Routing Key (필요시)
    public static final String KAKAO_ROUTING_KEY = "message.kakao";
    public static final String SMS_ROUTING_KEY   = "message.sms";

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


    /**
     * 메시지를 JSON으로 직렬화/역직렬화하는 MessageConverter
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * RabbitTemplate 에 JSON 컨버터를 설정
     */
    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            MessageConverter jsonMessageConverter
    ) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }

}