package cwchoiit.ecommerce.user.controller;

import cwchoiit.ecommerce.user.service.UserService;
import cwchoiit.ecommerce.user.service.response.UserPageResponse;
import cwchoiit.ecommerce.user.service.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "user-controller", description = "User controller for normal users.")
public class UserController {
    private final UserService userService;
    private final Environment environment;

    @Operation(summary = "Health check API", description = "Check user service status is OK.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR (Service Down...)")
    })
    @GetMapping("/healthz")
    public String healthCheck() {
        log.info("[healthCheck:25] server port: {} | config server : {}",
                environment.getProperty("local.server.port"), environment.getProperty("config.ecommerce"));
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
    public ResponseEntity<UserResponse> getUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUserByUserId(userId));
    }
}
