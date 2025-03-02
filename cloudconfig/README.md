# RabbitMQ + Spring Cloud Bus + Spring Cloud Config 

- Cloud Config 사용해서, 공통으로 설정 정보를 한 곳에서 관리하고 이 설정 정보를 다른 서비스들이 모두 따라가도록 구현
- 설정 값이 변경될 때 각 서비스들이 변경된 값을 적용하는 방법은 크게 세가지.
  - 서비스 재시작
  - actuator refresh
  - Spring Cloud Bus

- Spring Cloud Bus를 사용해서, Bus에 연결된 모든 서비스들이 동시에 모두 변경사항이 적용되도록 구현
- 이를 위해, RabbitMQ를 사용
- RabbitMQ는 도커를 사용해서 띄운다.
```bash
 # 5672는 버스 포트, 15672는 웹 사이트 포트 

docker run -d --name ecommerce-rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management 
```