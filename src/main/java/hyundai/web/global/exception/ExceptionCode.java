package hyundai.web.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {

    USER_NOT_FOUND("USER_NOT_FOUND", "사용자를 찾을 수 없습니다."),
    INVALID_INPUT("INVALID_INPUT", "입력값이 유효하지 않습니다."),

    //BAD_REQUEST
    IS_EXIST_SOCIAL_NUMBER("IS_EXIST_SOCIAL_NUMBER", "이미 등록된 주민등록번호입니다."),
    IS_EXIST_USER_ID("IS_EXIST_USER_ID", "이미 등록된 아이디입니다."),
    PASSWORD_NOT_MATCHED("PASSWORD_NOT_MATCHED","아이디 또는 비밀번호가 올바르지 않습니다."),

    //FEIGN 관련 서버에러
    FEIGN_BAD_REQUEST("FEIGN_BAD_REQUEST", "요청한 서버로부터 400 에러가 발생했습니다. "),
    FEIGN_UNAUTHORIZED("FEIGN_UNAUTHORIZED","요청한 서버로부터 401 에러가 발생했습니다. "),
    FEIGN_NOT_FOUND("FEIGN_NOT_FOUND","요청한 서버로부터 404 에러가 발생했습니다. "),
    FEIGN_METHOD_NOT_ALLOWED("FEIGN_METHOD_NOT_ALLOWED","요청한 서버로부터 405 에러가 발생했습니다. "),
    FEIGN_UNSUPPORTED_MEDIA_TYPE("FEIGN_UNSUPPORTED_MEDIA_TYPE","요청한 서버로부터 415 에러가 발생했습니다. "),
    FEIGN_TOO_MANY_REQUEST("FEIGN_METHOD_NOT_ALLOWED","요청한 서버로부터 429 에러가 발생했습니다. "),
    FEIGN_CLIENT_DEFAULT_EXCEPTION("FEIGN_CLIENT_DEFAULT_EXCEPTION"," FeignClient 예외처리되지 않은 default 예외입니다."),
    FEIGN_INTERNAL_SERVER_ERROR("FEIGN_INTERNAL_SERVER_ERROR","요청한 서버로부터 500 에러가 발생했습니다. "),
    FEIGN_BAD_GATEWAY("FEIGN_BAD_GATEWAY","요청한 서버로부터 502 에러가 발생했습니다. "),
    FEIGN_SERVICE_UNAVAILABLE("FEIGN_SERVICE_UNAVAILABLE","요청한 서버로부터 503 에러가 발생했습니다. "),
    FEIGN_GATEWAY_TIME_OUT("FEIGN_GATEWAY_TIME_OUT", "요청한 서버로부터 504 에러가 발생했습니다. " ),
    FEIGN_SERVER_EXCEPTION("FEIGN_SERVER_EXCEPTION","FeignClient 서버에러가 발생했습니다. "),
    FEIGN_SSL_AUTHENTICATE("FEIGN_SSL_AUTHENTICATE", "SSL 인증서 오류가 발생했습니다."),
    SITE_SSL_AUTHENTICATE("SITE_SSL_AUTHENTICATE", "사이트로부터 SSL 인증서 오류가 발생했습니다."),
    SITE_403_FORBIDDEN("SITE_403_FORBIDDEN", "사이트로 부터 403 에러가 발생했습니다. "),

            ;

    private final String code;
    private final String message;
    ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
