package it.numble.numbledanggeun.infrastructure.persistence.entity;

import it.numble.numbledanggeun.domain.user.model.UserRole;
import it.numble.numbledanggeun.infrastructure.persistence.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
    name = "tbl_user",
    uniqueConstraints = {
        @UniqueConstraint(name = "user_nickname_unique", columnNames = "nickname"),
        @UniqueConstraint(name = "user_email_unique", columnNames = "email")
    }
)
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@Where(clause = "deleted=0")
@Entity
public class UserEntity extends BaseEntity {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 양식을 지켜주세요.")
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "010-\\d{4}-\\d{4}", message = "전화번호는 010-XXXX-XXXX 형식으로 입력해주세요.")
    @Column(name = "phone_number", nullable = false, length = 13)
    private String phoneNumber;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Column(name = "nickname", nullable = false)
    private String nickname;

    @NotBlank
    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @NotBlank
    @Column(name = "refresh_token", length = 500)
    private String refreshToken;

    @NotNull
    @Column(name = "role")
    private UserRole role;

    @Builder
    public UserEntity(String email, String password, String username, String phoneNumber, String nickname, String refreshToken) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.profileImageUrl = "~~s3.default.profile.image.png";
        this.refreshToken = refreshToken;
        this.role = UserRole.ROLE_USER;
    }
}
