package cwchoiit.ecommerce.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/healthz")
public class HealthCheckController {

    private final Environment environment;

    @GetMapping
    public String healthCheck() {
        log.info("[healthCheck:17] server port: {}", environment.getProperty("local.server.port"));
        return "welcome";
    }
}
