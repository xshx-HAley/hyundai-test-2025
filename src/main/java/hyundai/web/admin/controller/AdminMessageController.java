package hyundai.web.admin.controller;

import hyundai.web.admin.dto.MessageJobResponse;
import hyundai.web.admin.service.MessageDispatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/messages")
public class AdminMessageController {

    private final MessageDispatchService messageDispatchService;

    @PostMapping("/age-group")
    public ResponseEntity<Object> sendByAgeGroup(
            @RequestParam int ageFrom,
            @RequestParam int ageTo,
            @RequestParam String content
    ) {
        messageDispatchService.dispatchKakaoMessageByAgeGroup(ageFrom, ageTo, content);
        return ResponseEntity.accepted().build();
    }
}
