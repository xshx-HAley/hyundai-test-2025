package hyundai.web.admin.controller;

import hyundai.web.admin.dto.AdminUserUpdateRequest;
import hyundai.web.admin.service.AdminUserService;
import hyundai.web.global.response.ApiResponse;
import hyundai.web.user.dto.UserSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping("")
    public ApiResponse<Object> findAll(@ModelAttribute UserSearchCondition condition,
                                       Pageable pageable) {
        return ApiResponse.success(adminUserService.findAllUserBySearchCondition(condition, pageable));
    }

    @PatchMapping("")
    public ApiResponse<Void> updateUser(@RequestBody AdminUserUpdateRequest request) {
        adminUserService.updateUser(request);
        return ApiResponse.success();
    }

    @DeleteMapping("/{userSequence}")
    public ApiResponse<Void> deleteUser(@PathVariable Long userSequence) {
        adminUserService.deleteUser(userSequence);
        return ApiResponse.success();
    }
}
