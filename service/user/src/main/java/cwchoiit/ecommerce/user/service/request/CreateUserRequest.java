package cwchoiit.ecommerce.user.service.request;

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
public class CreateUserRequest {
    @NotNull(message = "Email cannot be null")
    @Size(min = 8, message = "Email not be less than 8 characters")
    @Email
    private String email;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name not be less than 2 characters")
    private String name;

    @NotNull(message = "Password cannot be null")
    @Size(min = 4, message = "Password not be less than 4 characters")
    private String password;
}
