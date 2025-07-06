package hyundai.web.user.controller;

import hyundai.web.global.response.ApiResponse;
import hyundai.web.user.dto.LoginRequest;
import hyundai.web.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ApiResponse<Object> login(@RequestBody LoginRequest request) {
        return ApiResponse.success(loginService.login(request));
    }
}
