# Spring Boot 호텔 예약 시스템

## 프로젝트 소개 📑
- 관리자가 호텔 및 객실을 생성
- 사용자는 호텔에 객실을 보고 예약을 진행한다.

## 개발 💻
### 개발환경 📝
- **Langage** : `JAVA 17`
- **IDE** : `IntelliJ`
- **Framework** : `Spring Framework` `Spring Boot(3.x)` `JPA` `QueryDSL`
- **DataBase** : `h2`
- **ORM** : `hibernate`
- **Auth** : `JWT Token`
- **UI** : `Swagger UI`
- **API Tool** : `Postman`

### 주요기능 📌
#### 사용자
- `사용자`가 회원가입을 통해 AccessToken & RefreshToken을 취득하게 되며, Role은 `ROLE_USER`를 취득하게 된다.
- AccessToken으로 사용자의 정보를 조회 하며, 해당 토큰으로 예약을 진행할 수 있다.
---
#### 관리자
- `관리자`가 회원가입을 통해 AccessToken & RefreshToken을 취득하게 되며, Role은 `ROLE_ADMIN`를 취득하게 된다.
- 최초의 생성된 `관리자`는 활성화 여부가 `비활성` 상태로 생성되며, 다른 `관리자`가 활성화 여부를 변경해줘야 로그인이 가능하다.
- `관리자`는 호텔 정보 ➡️ 객실 정보 ➡️ 객실 카테고리 저장하며 관리한다.
- `Event`들은 반드시 AccessToken이 존재해야 가능하다.

### 체크포인트 ✅
- 호텔 Entity Domain 확장 🚧
- 객실 Entity Domain 확장 🚧
- 예약 Process 개발 진행중 🚧
