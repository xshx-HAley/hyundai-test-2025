package hyundai.web.user.controller;

import hyundai.web.global.response.ApiResponse;
import hyundai.web.user.dto.UserSignUpRequest;
import hyundai.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse<Object> signUp(@RequestBody @Validated UserSignUpRequest request) {
        return ApiResponse.success(userService.signUp(request));
    }

    @GetMapping("/me")
    public ApiResponse<Object> getMyInfo() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ApiResponse.success(userService.getMyInfo(userId));
    }
}