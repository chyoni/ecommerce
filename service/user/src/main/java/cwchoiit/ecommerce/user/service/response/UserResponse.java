package cwchoiit.ecommerce.user.service.response;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

import static cwchoiit.ecommerce.user.client.OrderServiceClient.*;
import static cwchoiit.ecommerce.user.entity.Users.Role;

@Getter
@ToString
public class UserResponse {
    private String email;
    private String name;
    private Long userId;
    private Role role;
    private UserOrdersPreviewResponse orderPreview;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static UserResponse from(String email,
                                    String name,
                                    Long userId,
                                    Role role,
                                    UserOrdersPreviewResponse orderPreview,
                                    LocalDateTime createdAt,
                                    LocalDateTime modifiedAt) {
        UserResponse userResponse = new UserResponse();
        userResponse.email = email;
        userResponse.name = name;
        userResponse.role = role;
        userResponse.userId = userId;
        userResponse.orderPreview = orderPreview;
        userResponse.createdAt = createdAt;
        userResponse.modifiedAt = modifiedAt;
        return userResponse;
    }
}
