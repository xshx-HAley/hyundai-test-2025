package hyundai.web.user.repository;

import hyundai.web.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, UserQueryRepository {
    Boolean existsBySocialNumber(String socialNumber);
    Boolean existsByUserId(String socialNumber);
    Optional<UserEntity> findByUserId(String userId);
}
