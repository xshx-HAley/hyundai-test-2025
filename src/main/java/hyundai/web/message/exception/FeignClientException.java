package hyundai.web.message.exception;

import hyundai.web.global.exception.BaseException;
import hyundai.web.global.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

public class FeignClientException extends BaseException {
    public FeignClientException(ExceptionCode errorCode, HttpStatus status) {
        super(errorCode, status);
    }
    public FeignClientException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
