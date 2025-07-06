package hyundai.web.admin.service;

import hyundai.web.admin.dto.AdminUserUpdateRequest;
import hyundai.web.global.response.PageResponse;
import hyundai.web.user.domain.UserEntity;
import hyundai.web.user.dto.UserInformation;
import hyundai.web.user.dto.UserSearchCondition;
import hyundai.web.user.dto.UserSearchResponse;
import hyundai.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;

    //todo
    public UserSearchResponse findAll(UserSearchCondition conditions, Pageable pageable) {
        Page<UserEntity> userList = userRepository.searchUsersByConditions(conditions,pageable);


        return new UserSearchResponse<>(PageResponse.from(userList),
                userList.getContent().stream().map(UserInformation.All::from).collect(Collectors.toList()));
    }

    //todo
    public void updateUser(String userId, AdminUserUpdateRequest request) {
    }
    //todo
    public void deleteUser(String userId) {
    }
}
