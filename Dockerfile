FROM eclipse-temurin:17
WORKDIR /home
COPY ./target/rbventure-0.0.1-SNAPSHOT.jar rbventure.jar
ENTRYPOINT ["java", "-jar", "rbventure.jar"]