package cwchoiit.ecommerce.user.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "User Service API specifications.",
                description = "User Service API specifications with spring cloud",
                version = "v0.0.1"
        )
)
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
        String[] paths = {"/users/healthz", "/signup"};

        return GroupedOpenApi.builder()
                .group("users")
                .pathsToMatch(paths)
                .build();
    }
}
