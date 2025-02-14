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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String email;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, unique = true)
    private String userId;
    @Column(nullable = false)
    private String encryptedPassword;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static Users of(String email, String name, String userId, String encryptedPassword) {
        Users users = new Users();
        users.email = email;
        users.name = name;
        users.userId = userId;
        users.encryptedPassword = encryptedPassword;
        users.createdAt = LocalDateTime.now();
        users.modifiedAt = LocalDateTime.now();

        return users;
    }
}
