package hyundai.web.user.domain;

import hyundai.web.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "userId"),
        @UniqueConstraint(columnNames = "socialNumber")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class UserEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, length = 50)
    private String userId;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    private Integer age;

    @Column(nullable = false, length = 100)
    private String socialNumber;

    @Column(length = 11)
    private String phone;

    @Embedded
    private Address address;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Boolean isDeleted = false;


    public void updateAddress(Address address){
        this.address = address;
    }


    public void updatePassword(String password){
        this.password = password;
    }

    public void updateIsDelete(Boolean value){
        this.isDeleted = value;
    }
}
