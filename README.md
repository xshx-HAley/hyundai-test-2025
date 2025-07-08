## 프로젝트 개요

```jsx
이 프로젝트는 Spring Boot 기반 웹 서비스로 사용자 회원가입 및 로그인 기능을 제공합니다.
관리자가 회원 목록을 조회/수정/삭제할 수 있는 API도 포함되어 있습니다.
```

## ⚙ 기술 스택

- Java 21
- Spring Boot 3.5.1
- Maven
- MariaDB
- RabbitMQ
- Docker / Docker Compose
- JWT 인증

## 🔧 실행 전 준비사항

- Java 21
- RabbitMQ
- MariaDB
- Docker / Docker Compose

포트:

- API 서버: `8080`
- RabbitMQ: `5672`

## 🚀 서버 실행 방법

### 1. Git Clone

```jsx
https://github.com/xshx-HAley/hyundai-test-2025
```

- 프로젝트 구조

```jsx
📁 hyundai/
├── Dockerfile
├── docker-compose.yml
├── init.sql                     ← DB 초기화 스크립트
├── pom.xml
├── README.md
├── src/
└── src/main/resources/
    ├── application.properties   ← local 개발용
    └── application-docker.properties ← docker 전용 설정
```

### 2.  Docker Compose 실행 명령어

```jsx
# 1. 패키지 빌드 (JAR 생성)
./mvnw clean package -DskipTests

# 2. Docker Compose 실행
docker-compose up --build

# 전체 컨테이너 및 볼륨 제거 (init.sql 다시 실행 가능)
docker-compose down --volumes
```

## API 명세서

https://documenter.getpostman.com/view/12638964/2sB34eHged