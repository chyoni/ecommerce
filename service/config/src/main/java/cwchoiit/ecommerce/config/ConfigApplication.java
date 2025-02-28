package cwchoiit.ecommerce.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;


/**
 * 설정값들을 관리하는 Config Server <br>
 * <p>
 * 흐름은 다음과 같다. <br>
 * 1 - 설정 파일만을 저장하는 Git Repository 를 준비한다. 이 프로젝트의 경우, common/config 경로에 설정 파일들을 저장한다. <br>
 * 2 - 이 Config Server 가 가진 application.yaml 파일에서 설정파일들을 관리하고 있는 Git Repository 를 바라보게 설정한다. <br>
 * 3 - 다른 서비스의 경우 {@code org.springframework.cloud:spring-cloud-starter-bootstrap} 이 의존성을 내려받고, bootstrap.yaml 파일을 만들어서
 * 해당 파일에 이 Config Server 의 URL + common/config 디렉토리에 있는 바라볼 대상의 yaml 파일이름을 등록한다. <br>
 * 4 - 각 서비스는 자기의 application.yaml 파일 포함 + bootstrap.yaml 파일에서 정의한 이 Config Server 가 관리하는 설정 파일을 추가적으로 바라본다. <br>
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }
}
