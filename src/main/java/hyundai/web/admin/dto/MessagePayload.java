package hyundai.web.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MessagePayload {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Kakao {
        private String userName;
        private String phone;
        private String message;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Sms {
        private String userName;
        private String phone;
        private String message;
    }
}
