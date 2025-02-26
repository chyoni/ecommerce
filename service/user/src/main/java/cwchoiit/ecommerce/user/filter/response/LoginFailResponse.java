package cwchoiit.ecommerce.user.filter.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginFailResponse {
    private String error;

    public static LoginFailResponse from(String error) {
        LoginFailResponse loginFailResponse = new LoginFailResponse();
        loginFailResponse.error = error;
        return loginFailResponse;
    }
}
