FROM openjdk:17
WORKDIR /my-project
#CMD ["./gradlew", "clean", "bootJar"]
COPY ./build/libs/mapsAdvisor-0.0.1-SNAPSHOT.jar app.jar
CMD echo "127.17.0.1 host.docker.internal" >> /etc/hosts

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]