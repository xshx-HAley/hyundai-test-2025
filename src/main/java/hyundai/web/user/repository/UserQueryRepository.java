package hyundai.web.user.repository;

import hyundai.web.user.domain.UserEntity;
import hyundai.web.user.dto.UserSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQueryRepository {
    Page<UserEntity> searchUsersByConditions(UserSearchCondition condition, Pageable pageable);
}
