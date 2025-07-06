package hyundai.web.user.dto;

import hyundai.web.user.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

public class UserInformation {

    @Getter
    @AllArgsConstructor
    @SuperBuilder
    public static class Basic {
        private String userId;
        private String name;
        private String phone;
        private String region;
    }

    @Getter
    @SuperBuilder
    public static class All extends Basic {
        private String socialNumber;

        public All(String userId, String name, String phone, String region, String socialNumber) {
            super(userId, name, phone, region); // 상위 클래스 초기화
            this.socialNumber = socialNumber;
        }

        public static All from(UserEntity user) {
            return All.builder()
                    .userId(user.getUserId())
                    .name(user.getName())
                    .phone(user.getPhone())
                    .region(user.getAddress() != null ? user.getAddress().getSido() : null)
                    .socialNumber(user.getSocialNumber())
                    .build();
        }
    }
}
