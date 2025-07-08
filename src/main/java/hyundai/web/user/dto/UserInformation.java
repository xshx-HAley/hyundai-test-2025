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
        private String sido;
    }

    @Getter
    @SuperBuilder
    public static class All extends Basic {
        private Long sequence;
        private String socialNumber;
        private String sigungu;
        private String eupmyeondong;
        private String detail;
        private Boolean isDeleted;

        public static All from(UserEntity user) {
            return All.builder()
                    .sequence(user.getId())
                    .userId(user.getUserId())
                    .name(user.getName())
                    .phone(user.getPhone())
                    .sido(user.getAddress() != null ? user.getAddress().getSido() : null)
                    .sigungu(user.getAddress() != null ? user.getAddress().getSigungu() : null)
                    .eupmyeondong(user.getAddress() != null ? user.getAddress().getEupmyeondong() : null)
                    .detail(user.getAddress() != null ? user.getAddress().getDetail() : null)
                    .isDeleted(user.getIsDeleted())
                    .build();
        }
    }
}
