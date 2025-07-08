package hyundai.web.message.exception;

import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import hyundai.web.global.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Feign을 통해 호출한 서버의 응답값에 따른 에러 핸들러
 */
@Slf4j
@Component
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        log.info("요청이 정상적으로 성공하지 못했습니다. time = {}, status = {}, responseReason = {}",
                LocalDateTime.now(), response.status(), response.reason());

        switch (response.status()){
            case 400:
                return new FeignClientException(ExceptionCode.FEIGN_BAD_REQUEST, HttpStatus.BAD_REQUEST);
            case 401:
                return new FeignClientException(ExceptionCode.FEIGN_UNAUTHORIZED, HttpStatus.BAD_REQUEST);
            case 404:
                return new FeignClientException(ExceptionCode.FEIGN_NOT_FOUND, HttpStatus.BAD_REQUEST);
            case 405:
                return new FeignClientException(ExceptionCode.FEIGN_METHOD_NOT_ALLOWED, HttpStatus.BAD_REQUEST);
            case 415:
                return new FeignClientException(ExceptionCode.FEIGN_UNSUPPORTED_MEDIA_TYPE, HttpStatus.BAD_REQUEST);
            case 429:
                return new FeignClientException(ExceptionCode.FEIGN_TOO_MANY_REQUEST, HttpStatus.BAD_REQUEST);
            case 500:
                return new FeignClientException(ExceptionCode.FEIGN_INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            case 502:
                return new FeignClientException(ExceptionCode.FEIGN_BAD_GATEWAY, HttpStatus.INTERNAL_SERVER_ERROR);
            case 503:
                return new FeignClientException(ExceptionCode.FEIGN_SERVICE_UNAVAILABLE, HttpStatus.INTERNAL_SERVER_ERROR);
            case 504:
                return new FeignClientException(ExceptionCode.FEIGN_GATEWAY_TIME_OUT, HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                return new FeignClientException(ExceptionCode.FEIGN_CLIENT_DEFAULT_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
