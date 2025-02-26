package cwchoiit.ecommerce.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users implements UserDetails {
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

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Users of(Long userId, String email, String name, String encryptedPassword, Role role) {
        Users users = new Users();
        users.userId = userId;
        users.email = email;
        users.name = name;
        users.role = role;
        users.encryptedPassword = encryptedPassword;
        users.createdAt = LocalDateTime.now();
        users.modifiedAt = LocalDateTime.now();

        return users;
    }

    public static Users loadUser(String email, String encryptedPassword, Role role) {
        Users users = new Users();
        users.email = email;
        users.encryptedPassword = encryptedPassword;
        users.role = role;
        return users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add((GrantedAuthority) () -> role.getRole());
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return encryptedPassword;
    }

    @Getter
    @AllArgsConstructor
    public enum Role {
        ROLE_ADMIN("ROLE_ADMIN"),
        ROLE_USER("ROLE_USER");

        private final String role;

        public static Role getRole(String targetRole) {
            return Arrays.stream(Role.values())
                    .filter(r -> r.role.equals(targetRole))
                    .findFirst()
                    .orElse(null);
        }
    }
}
