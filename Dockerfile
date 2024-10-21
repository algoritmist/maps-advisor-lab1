FROM openjdk:17
WORKDIR /my-project
#CMD ["./gradlew", "build", "-x", "tests"]
COPY ./build/libs/mapsAdvisor-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
