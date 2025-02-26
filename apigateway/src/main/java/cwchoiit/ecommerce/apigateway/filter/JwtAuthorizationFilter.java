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

import java.security.SignatureException;
import java.util.Objects;

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

            if (!isJwtValid(token)) {
                return onError(exchange, "JWT is not valid");
            }

            return chain.filter(exchange);
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        log.error("[onError:43] Authorization failure: {}", errorMessage);
        return response.setComplete();
    }

    private boolean isJwtValid(String token) {
        try {
            String userId = JwtManager.getUserIdByToken(token);
            return userId != null && !userId.isEmpty() && !userId.equals("null");
        } catch (Exception e) {
            log.error("[isJwtValid:60] JWT validation failed, token : {}", token, e);
            return false;
        }
    }

    public static class Config {
    }
}
