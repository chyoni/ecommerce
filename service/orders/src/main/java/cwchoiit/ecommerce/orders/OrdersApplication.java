package cwchoiit.ecommerce.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
// 기본적으로, Spring Boot 애플리케이션은 이 엔트리 클래스가 위치한 패키지 이하의 컴포넌트를 자동으로 스캔하지만,
// outbox-message-relay 모듈을 사용하는 이 OrdersApplication 은 모듈에서 사용하고 있는 OutboxRepository 까지 사용해야 하고 이 레포지토리는 기본 스캔 범위를 벗어나기 때문에
// @EntityScan, @EnableJpaRepositories 애노테이션을 추가해야 함
@EntityScan(basePackages = "cwchoiit.ecommerce")
@EnableJpaRepositories(basePackages = "cwchoiit.ecommerce")
public class OrdersApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);
    }
}
