package cwchoiit.ecommerce.user.service.response;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class UserResponse {
    private String email;
    private String name;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static UserResponse from(String email,
                                    String name,
                                    String userId,
                                    LocalDateTime createdAt,
                                    LocalDateTime modifiedAt) {
        UserResponse userResponse = new UserResponse();
        userResponse.email = email;
        userResponse.name = name;
        userResponse.userId = userId;
        userResponse.createdAt = createdAt;
        userResponse.modifiedAt = modifiedAt;
        return userResponse;
    }
}
