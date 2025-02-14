package cwchoiit.ecommerce.apigateway.filter;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
    public GlobalFilter() {
        super(Config.class);
    }

    /**
     * Global Filter
     *
     * @param config config
     * @return {@link GatewayFilter}
     */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // 요청이 서비스 또는 그 다음 필터에 도달하기 전에 호출되는 코드 (Pre Filter)
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("[apply:26] Global filter baseMessage : {}", config.getBaseMessage());

            if (config.isPreLogger()) {
                log.info("[apply:29] Global filter start: request id : {}", request.getId());
            }

            // 요청이 그 다음 필터, 서비스에서 실제로 처리한 후 응답으로 돌아왔을 때 호출되는 코드 (Post Filter)
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                                if (config.isPostLogger()) {
                                    log.info("[apply:36] Global filter end : response code : {}", response.getStatusCode());
                                }
                            })
                    );
        };
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
