# Outbox Message Relay

- Producer 들을 위한 모듈
- Kafka 에 이벤트를 전송하는 Producer 들은, 이 모듈을 통해서 이벤트를 전송
- Outbox 패턴을 사용하여, 이벤트 전송이 정상적으로 전달되지 않을 때에도 Outbox 데이터베이스에 저장된 이벤트들을 polling 하여 전송하게 구현
- 트랜잭션이 정상 커밋되면 이벤트가 비동기적으로 Kafka 에 전송되게 구현