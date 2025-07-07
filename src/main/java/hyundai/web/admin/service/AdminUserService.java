package hyundai.web.admin.service;

import hyundai.web.admin.dto.AdminUserUpdateRequest;
import hyundai.web.global.crypto.PasswordEncryptor;
import hyundai.web.global.exception.BaseException;
import hyundai.web.global.exception.ExceptionCode;
import hyundai.web.global.response.PageResponse;
import hyundai.web.user.domain.Address;
import hyundai.web.user.domain.UserEntity;
import hyundai.web.user.dto.UserInformation;
import hyundai.web.user.dto.UserSearchCondition;
import hyundai.web.user.dto.UserSearchResponse;
import hyundai.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;
    private final PasswordEncryptor passwordEncryptor;

    @Transactional(readOnly = true)
    public UserSearchResponse findAllUserBySearchCondition(UserSearchCondition conditions, Pageable pageable) {
        Page<UserEntity> userList = userRepository.searchUsersByConditions(conditions,pageable);


        return new UserSearchResponse<>(PageResponse.from(userList),
                userList.getContent().stream().map(UserInformation.All::from).collect(Collectors.toList()));
    }


    @Transactional
    public void updateUser(AdminUserUpdateRequest request) {
        List<String> userIds = request.extractUserIds();
        List<UserEntity> users = userRepository.findAllByUserIdIn(userIds);
        Map<String, AdminUserUpdateRequest.AdminUserUpdateItem> updateMap = request.getUsers().stream()
                .collect(Collectors.toMap(AdminUserUpdateRequest.AdminUserUpdateItem::getUserId, Function.identity()));

        for(UserEntity user : users){
            AdminUserUpdateRequest.AdminUserUpdateItem item = updateMap.get(user.getUserId());
            if (item == null) continue;

            // 비밀번호가 넘어왔으면 업데이트
            boolean hasPassword =  item.getPassword() != null && !item.getPassword().isBlank();
            if (hasPassword) {
                user.updatePassword(passwordEncryptor.encode(item.getPassword()));
            }
            // 주소 값이 있으면 업데이트
            boolean hasAddressField = Stream.of(
                    item.getSido(), item.getSigungu(), item.getEupmyeondong(), item.getDetail()
            ).anyMatch(Objects::nonNull);
            if (hasAddressField) {
                Address newAddr = Address.of(item.getSido(), item.getSigungu(), item.getEupmyeondong(), item.getDetail());
                user.updateAddress(newAddr);
            }
        }
        userRepository.saveAll(users);
    }

    @Transactional
    public void deleteUser(Long userSequence) {
        UserEntity user = userRepository.findById(userSequence)
                .orElseThrow(() -> new BaseException(ExceptionCode.USER_NOT_FOUND));

        user.updateIsDelete(Boolean.TRUE);
        userRepository.save(user);
    }
}
