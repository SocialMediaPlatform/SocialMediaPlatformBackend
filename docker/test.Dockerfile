FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src /app/src
COPY src/main/java/com/social_media_platform/social_media_platform_backend/databaseTables/dictValues src/main/java/com/social_media_platform/social_media_platform_backend/databaseTables/dictValues

RUN chmod +x ./mvnw

CMD ["./mvnw", "-Dspring.profiles.active=test-containers", "test"]

