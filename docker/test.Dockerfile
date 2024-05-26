FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src /app/src

RUN chmod +x ./mvnw

CMD ["./mvnw", "-Dspring.profiles.active=test-containers", "test"]

