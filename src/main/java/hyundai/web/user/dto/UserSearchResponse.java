package hyundai.web.user.dto;

import hyundai.web.global.response.PageResponse;

import java.util.List;

public record UserSearchResponse<T> (
        PageResponse pageInformation,
        List<T> userList
){}
