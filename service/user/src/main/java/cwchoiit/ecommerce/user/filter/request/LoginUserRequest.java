package cwchoiit.ecommerce.user.filter.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginUserRequest {
    @NotNull(message = "Email cannot be null")
    @Size(min = 5, message = "Email not be less than five characters")
    @Email
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 4, message = "Password must be equals or greater than 4 characters")
    private String password;
}
