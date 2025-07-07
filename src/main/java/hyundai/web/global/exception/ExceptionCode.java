package hyundai.web.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    USER_NOT_FOUND("USER_NOT_FOUND", "사용자를 찾을 수 없습니다."),
    INVALID_INPUT("INVALID_INPUT", "입력값이 유효하지 않습니다."),

    //BAD_REQUEST
    IS_EXIST_SOCIAL_NUMBER("IS_EXIST_SOCIAL_NUMBER", "이미 등록된 주민등록번호입니다."),
    IS_EXIST_USER_ID("IS_EXIST_USER_ID", "이미 등록된 아이디입니다."),
    PASSWORD_NOT_MATCHED("PASSWORD_NOT_MATCHED","아이디 또는 비밀번호가 올바르지 않습니다.")
    ;

    private final String code;
    private final String message;
    ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
