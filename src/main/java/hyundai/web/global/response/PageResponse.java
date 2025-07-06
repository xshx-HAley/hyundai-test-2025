package hyundai.web.global.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor(staticName = "of")
public class PageResponse<T> {
    private int page;         // 현재 페이지 번호 (0-base 또는 1-base 선택)
    private int size;         // 페이지 크기
    private long totalElements;
    private int totalPages;

    public static <T> PageResponse<T> from(Page<T> page) {
        return PageResponse.of(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}