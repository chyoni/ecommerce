package cwchoiit.ecommerce.apigateway.filter;

import cwchoiit.ecommerce.common.jwt.JwtManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class JwtAuthorizationFilter extends AbstractGatewayFilterFactory<JwtAuthorizationFilter.Config> {

    public JwtAuthorizationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization header");
            }

            String authorization = Objects.requireNonNull(request.getHeaders().get(HttpHeaders.AUTHORIZATION)).getFirst();
            String token = authorization.replace("Bearer ", "");

            Optional<String> userIdByToken = getUserIdByToken(token);
            if (userIdByToken.isEmpty()) {
                return onError(exchange, "JWT is not valid");
            }

            String userId = userIdByToken.get();
            if (userId.isEmpty() || userId.equals("null")) {
                return onError(exchange, "JWT is not valid");
            }

            // 우선적으로 API Gateway 가 인증을 처리하고, 인증 처리가 되면 토큰의 Subject 인 userId를 각 서비스에게 전달하여 서비스에서도 한번 더 인증을 수행할 것
            ServerHttpRequest updateRequest = request.mutate().header("X-User-Id", userId).build();
            return chain.filter(
                    exchange.mutate().request(updateRequest).build()
            );
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        log.error("[onError:43] Authorization failure: {}", errorMessage);
        return response.setComplete();
    }

    private Optional<String> getUserIdByToken(String token) {
        try {
            return Optional.ofNullable(JwtManager.getUserIdByToken(token));
        } catch (Exception e) {
            log.error("[isJwtValid:60] JWT validation failed, token : {}", token, e);
            return Optional.empty();
        }
    }

    public static class Config {
    }
}
