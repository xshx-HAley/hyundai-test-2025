# 1. Java 21이 포함된 JDK 이미지 사용
FROM eclipse-temurin:21-jdk

# 2. JAR 파일을 컨테이너로 복사
ARG JAR_FILE=target/web-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# 3. 포트 설정 (Spring Boot의 기본 포트는 8080)
EXPOSE 8080

# 4. JAR 실행
ENTRYPOINT ["java","-jar","/app.jar"]