package hyundai.web.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUserUpdateRequest {
    private List<AdminUserUpdateItem> users;

    /**
     * 요청 목록에서 userId만 꺼내서 String 리스트로 반환
     */
    public List<String> extractUserIds() {
        if (users == null) {
            return Collections.emptyList();
        }
        return users.stream()
                .map(item -> item.getUserId())
                .collect(Collectors.toList());
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AdminUserUpdateItem {
        private String userId;
        private String password;
        private String sido;
        private String sigungu;
        private String eupmyeondong;
        private String detail;
    }
}

