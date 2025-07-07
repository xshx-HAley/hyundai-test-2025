package hyundai.web.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {

    //시/도
    @Column(nullable = false)
    private String sido;
    //시/군/구
    private String sigungu;
    //읍/면/동
    private String eupmyeondong;
    //상세
    private String detail;

    private Address(String sido, String sigungu, String eupmyeondong, String detail) {
        this.sido = sido;
        this.sigungu = sigungu;
        this.eupmyeondong = eupmyeondong;
        this.detail = detail;
    }
    public static Address of(String sido, String sigungu, String eupmyeondong, String detail) {
        return new Address(sido, sigungu, eupmyeondong, detail);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address that)) return false;
        return Objects.equals(sido, that.sido) &&
                Objects.equals(sigungu, that.sigungu) &&
                Objects.equals(eupmyeondong, that.eupmyeondong) &&
                Objects.equals(detail, that.detail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sido, sigungu, eupmyeondong, detail);
    }
}
