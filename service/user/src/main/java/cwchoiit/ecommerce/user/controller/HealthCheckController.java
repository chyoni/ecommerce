package cwchoiit.ecommerce.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    @GetMapping("/first-service")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/second-service")
    public String welcome2() {
        return "welcome";
    }
}
