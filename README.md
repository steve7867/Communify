# 🌏 Communify

- 다양한 주제에 대한 글을 공유하는 커뮤니티 서비스
- 대용량 트래픽을 가정하여 성능에 초점을 두고 개발 진행

## 🎯 Technical Issue

- 캐싱(Redis)
  - Look Aside 전략을 활용한 읽기 성능 향상
  - Write Behind 전략으로 좋아요, 조회 수 집계를 구현해 DB 접근 횟수 경감
  - 캐시, 세션 저장소 분리
  - Keys 대신 Scan 명령을 사용한 키 조회
  - Redis Transaction을 사용해 Redis에 다수 접근하는 로직을 atomic하게 구현
  - Redis Pipelining을 사용해 RTT Latency와 Socket I/O 부하 경감
- DB(MySQL)
  - DB 복제(Replication)를 통한 DB 부하 분산
    - LazyConnectionDataSourceProxy를 사용해 DB 커넥션 획득 시점 지연
  - 인덱스를 통한 조회 성능 향상
  - Bulk Insert를 이용한 삽입 성능 향상
  - 무한 스크롤 방식의 페이징
- 푸시 알림(FireBase)
  - 비동기 방식을 적용해 푸시 알림 성능 향상
- 기타
  - 중복되는 로그인 체크 로직을 Spring AOP로 구현
  - Nginx의 Reversed-Proxy를 이용한 로드밸런싱

## 🖼️ Application UI

![Application UI](https://github.com/steve7867/Communify/assets/115217247/76e5efdc-4106-4fc5-b820-c687abab72bd)

## 📈 ERD

![ERD](https://github.com/user-attachments/assets/0ba02fc1-194d-4959-bb21-b6ada24b2308)

## 🏛️ 프로젝트 구조도

![프로젝트 구조](https://github.com/user-attachments/assets/75f1179b-4a96-4738-b8b0-33b9f11dc5d8)

## ⚙️ 주요 기능

### 🙋🏻‍♂️ 회원

- 이메일 인증을 통한 가입
- 비밀번호 인증을 통한 탈퇴
- 비밀번호 변경
- 로그인, 로그아웃
- 회원 조회
- 다른 회원 팔로우 / 언팔로우

### 📝 게시글

- 게시글 작성
- 카테고리별 게시글 목록 조회
- 인기 게시글 조회
- 게시글 상세 조회
- 게시글 수정
- 게시글 삭제
- 게시글 좋아요

### 💬 댓글

- 댓글 작성
- 게시글별 댓글 조회
- 댓글 수정
- 댓글 삭제

### 🔔 푸시 알림

- 다른 회원이 자신을 팔로우한 경우
- 팔로우 중인 회원이 새로운 게시글을 작성한 경우
- 다른 회원이 자신의 게시글에 댓글 작성한 경우
- 다른 회원이 자신의 게시글에 좋아요 버튼을 누른 경우
