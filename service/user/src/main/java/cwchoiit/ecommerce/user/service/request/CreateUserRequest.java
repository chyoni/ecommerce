package cwchoiit.ecommerce.user.service.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "A requested user object for create.")
public class CreateUserRequest {
    @NotNull(message = "Email cannot be null")
    @Size(min = 8, message = "Email not be less than 8 characters")
    @Email
    @Schema(title = "Email", description = "email for new user")
    private String email;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name not be less than 2 characters")
    @Schema(title = "Name", description = "name for new user")
    private String name;

    @NotNull(message = "Password cannot be null")
    @Size(min = 4, message = "Password not be less than 4 characters")
    @Schema(title = "Password", description = "password for new user")
    private String password;

    public static CreateUserRequest of(String email, String name, String password) {
        CreateUserRequest request = new CreateUserRequest();
        request.email = email;
        request.name = name;
        request.password = password;
        return request;
    }
}
