package cwchoiit.ecommerce.user.service.response;

import cwchoiit.ecommerce.user.entity.Users;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserResponse {
    private Long userId;
    private String email;
    private String name;
    private LocalDateTime createdAt;

    public static CreateUserResponse from(Users user) {
        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.email = user.getEmail();
        createUserResponse.name = user.getName();
        createUserResponse.userId = user.getUserId();
        createUserResponse.createdAt = user.getCreatedAt();
        return createUserResponse;
    }
}
