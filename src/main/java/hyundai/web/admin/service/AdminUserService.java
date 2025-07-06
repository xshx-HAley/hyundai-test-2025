package hyundai.web.admin.service;

import hyundai.web.admin.dto.AdminUserUpdateRequest;
import hyundai.web.user.domain.UserEntity;
import hyundai.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;

    //todo
    public ResponseEntity<Object> findAll(Pageable pageable) {
        List<UserEntity> userList ;
        return null;
    }

    //todo
    public void updateUser(String userId, AdminUserUpdateRequest request) {
    }
    //todo
    public void deleteUser(String userId) {
    }
}
