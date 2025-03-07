# E-commerce 

- Spring Cloud Eureka (Discovery Service)
- Spring Cloud Gateway (API Gateway)
- Spring Cloud Config (Configuration Synchronization)
- Spring Security 
- Microservice (User, Order, ...)
- Kafka (Messaging Queue, Event Streaming Architecture)
- RabbitMQ (Cloud Bus)
- MySQL (Database)
- Redis (Cache, Database)
- Swagger OpenAPI (https://github.com/springdoc/springdoc-openapi-demos) (https://springdoc.org/)
- And so on...

## Spring Cloud Eureka
- 유레카와 같은 디스커버리 서비스를 사용하는 목적은, 여러 필요한 서버들(Gateway, 마이크로서비스)들을 관리하기 위함.
- 유저, 주문, 카테고리와 같이 구분되는 마이크로서비스뿐 아니라, 같은 유저 마이크로서비스도 5개를 띄우던 10개를 띄우던 모두 한 곳에서 관리되어야
요청을 적절하게 보낼 수 있는데 그것을 관리하는 게 바로 이 유레카(디스커버리 서비스)

## Spring Cloud Gateway
- 요청을 모두 이 Gateway가 받아서, 해당 요청이 어떤 서비스로 전달되어야 하는지를 판단하여 전달.
- 전달할 때도 물론 부하분산을 위한 라운드 로빈 기법과 같은 방식을 사용해서 전달

## Spring Cloud Config
- 각 마이크로서비스의 설정 파일이 따로 존재할 수도 있지만, 공통적으로 사용하거나 한 곳에서 설정 파일을 관리하고 동시에 전파한다면
거기서 오는 장점도 꽤나 큰데 그 설정을 한 곳에서 관리할 수 있게 해주는 모듈 