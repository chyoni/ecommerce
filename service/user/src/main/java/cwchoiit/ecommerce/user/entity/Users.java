package cwchoiit.ecommerce.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {
    @Id
    @Column(nullable = false, unique = true)
    private Long userId;
    @Column(nullable = false, length = 50)
    private String email;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false)
    private String encryptedPassword;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static Users of(Long userId, String email, String name, String encryptedPassword) {
        Users users = new Users();
        users.userId = userId;
        users.email = email;
        users.name = name;
        users.encryptedPassword = encryptedPassword;
        users.createdAt = LocalDateTime.now();
        users.modifiedAt = LocalDateTime.now();

        return users;
    }
}
