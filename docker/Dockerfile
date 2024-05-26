FROM eclipse-temurin:17-jdk AS build-stage

WORKDIR /app

COPY mvnw ./
COPY .mvn .mvn

COPY pom.xml .

COPY src src

RUN chmod +x ./mvnw

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jdk AS run-stage

ENV USER=springuser
ENV UID=1001
ENV GROUP=springgroup
ENV GID=1001

RUN groupadd --gid $GID $GROUP && \
    useradd --uid $UID --gid $GID --create-home $USER

ENV HOME=/home/$USER

WORKDIR $HOME

COPY --from=build-stage --chown=$UID:$GID /app/target/social_media_platform_backend-0.0.1-SNAPSHOT.jar $HOME/app.jar

USER $UID:$GID

EXPOSE 8080

CMD ["java", "-jar", "/home/springuser/app.jar"]
