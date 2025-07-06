package hyundai.web.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {

    private final ExceptionCode errorCode;
    private final HttpStatus status;

    public BaseException(ExceptionCode errorCode, HttpStatus status) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.status = status;
    }

    public BaseException(ExceptionCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
