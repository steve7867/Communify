# 🌏 Communify

- 다양한 주제에 대한 글을 공유하는 커뮤니티 서비스
- 대용량 트래픽을 가정하여 성능에 초점을 두고 개발 진행

## 🎯 Technical Issue

- 캐싱(Redis)
  - Look Aside 전략을 통한 읽기 성능 향상
  - 좋아요, 조회 수를 비동기적으로 DB에 반영
  - 캐시, 세션 저장소 분리
  - Keys 대신 Scan 명령 사용
  - Redis Transaction을 사용해 Redis에 다수 접근하는 로직을 atomic하게 구현
  - Redis Pipelining을 사용해 RTT Latency와 Socket I/O 부하 경감
- DB(MySQL)
  - DB Replication을 통한 DB 부하 분산
  - 인덱스를 통한 조회 성능 향상
  - Bulk Insert를 이용한 삽입 성능 향상
  - 무한 스크롤 방식의 페이징
- 기타
  - Spring AOP을 사용해 중복되는 로그인 체크 로직 구현
  - Nginx Reversed-Proxy를 이용한 로드밸런싱

## 🤔 TroubleShooting & 기술적 고민
[🔗링크](https://github.com/steve7867/Communify/wiki/%F0%9F%A4%94-TroubleShooting-&-%EA%B8%B0%EC%88%A0%EC%A0%81-%EA%B3%A0%EB%AF%BC)

## 🏛️ Architecture

![프로젝트 구조](https://github.com/user-attachments/assets/b2198273-8dec-45ed-9ab4-1de19aac7641)

## 📈 ERD

![ERD](https://github.com/user-attachments/assets/999c1367-aca2-468d-bb86-44c0d12e629e)

## 🖼️ Application UI

![Application UI](https://github.com/user-attachments/assets/8677fa61-ad06-42d4-9935-0ac33d642ed6)
