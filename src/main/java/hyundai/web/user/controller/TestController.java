package hyundai.web.user.controller;

import hyundai.web.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/test")
    public ApiResponse<Object> test(){

        return ApiResponse.success("success");
    }
}
