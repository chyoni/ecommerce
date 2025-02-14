package cwchoiit.ecommerce.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public CustomFilter() {
        super(Config.class);
    }

    /**
     * Custom Filter
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

            log.info("[apply:32] Custom Pre Filter : request id : {}", request.getId());

            // 요청이 그 다음 필터, 서비스에서 실제로 처리한 후 응답으로 돌아왔을 때 호출되는 코드 (Post Filter)
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() ->
                                    log.info("[apply:37] Custom Post Filter : response code : {}", response.getStatusCode())
                            )
                    );
        };
    }

    public static class Config {

    }
}
