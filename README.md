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

## 🖼️ Application UI

![Application UI](https://github.com/user-attachments/assets/8677fa61-ad06-42d4-9935-0ac33d642ed6)

## 📈 ERD

![ERD](https://github.com/user-attachments/assets/999c1367-aca2-468d-bb86-44c0d12e629e)

## 🏛️ 프로젝트 구조도

![프로젝트 구조](https://github.com/user-attachments/assets/b2198273-8dec-45ed-9ab4-1de19aac7641)

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
