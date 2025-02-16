package cwchoiit.ecommerce.user.service.response;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class UserPageResponse {
    private List<UserResponse> users;
    private Long userCount;

    public static UserPageResponse from(List<UserResponse> users, Long userCount) {
        UserPageResponse userPageResponse = new UserPageResponse();
        userPageResponse.users = users;
        userPageResponse.userCount = userCount;
        return userPageResponse;
    }
}
