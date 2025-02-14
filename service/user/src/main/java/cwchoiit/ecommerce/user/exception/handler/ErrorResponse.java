package cwchoiit.ecommerce.user.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ErrorResponse {
    private String errorMessage;
}
