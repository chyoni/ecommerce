package cwchoiit.ecommerce.user.controller;

import cwchoiit.ecommerce.user.service.UserService;
import cwchoiit.ecommerce.user.service.request.CreateUserRequest;
import cwchoiit.ecommerce.user.service.response.CreateUserResponse;
import cwchoiit.ecommerce.user.service.response.UserPageResponse;
import cwchoiit.ecommerce.user.service.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final Environment environment;

    @GetMapping("/healthz")
    public String healthCheck() {
        log.info("[healthCheck:25] server port: {}", environment.getProperty("local.server.port"));
        return "welcome";
    }

    @GetMapping
    public ResponseEntity<UserPageResponse> getUsers(@RequestParam("page") Long page,
                                                     @RequestParam("pageSize") Long pageSize) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUsers(page, pageSize));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.getUserByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Validated CreateUserRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(request));
    }
}
