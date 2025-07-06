package hyundai.web.global.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseTimeEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDateTime;   // 생성일시

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updateDateTime;

}
