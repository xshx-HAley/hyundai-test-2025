package hyundai.web.user.dto;

import hyundai.web.user.domain.Address;

public record UserSignUpRequest (
        String userId,
        String password,
        String name,
        String socialNumber,
        String phone,
        String sido,
        String sigungu,
        String eupmyeondong,
        String detail
) {
    public Address toAddress() {
        return Address.of(sido, sigungu, eupmyeondong, detail);
    }
}