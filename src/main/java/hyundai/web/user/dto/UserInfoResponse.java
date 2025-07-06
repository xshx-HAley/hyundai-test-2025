package hyundai.web.user.dto;

import hyundai.web.global.util.DateUtil;
import hyundai.web.user.domain.UserEntity;

public record UserInfoResponse(
        String userId,
        String name,
        String phone,
        String createdAt,
        String region // 시/도만 포함
) {
    public static UserInfoResponse of(UserEntity user) {
        return new UserInfoResponse(
                user.getUserId(),
                user.getName(),
                user.getPhone(),
                DateUtil.localDateTimeToString(user.getCreateDateTime()),
                user.getAddress().getSido() // 가장 상위 행정구역만
        );
    }
}
