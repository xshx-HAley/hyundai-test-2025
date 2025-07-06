package hyundai.web.admin.controller;

import hyundai.web.admin.dto.AdminUserUpdateRequest;
import hyundai.web.admin.service.AdminUserService;
import hyundai.web.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping
    public ApiResponse<Object> findAll(Pageable pageable) {
        return ApiResponse.success(adminUserService.findAll(pageable));
    }

    @PatchMapping("/{userId}")
    public ApiResponse<Void> updateUser(@PathVariable String userId,
                                        @RequestBody AdminUserUpdateRequest request) {
        adminUserService.updateUser(userId, request);
        return ApiResponse.success();
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<Void> deleteUser(@PathVariable String userId) {
        adminUserService.deleteUser(userId);
        return ApiResponse.success();
    }
}
