FROM gradle:jdk17-alpine AS build
RUN adduser -D builder
USER builder
WORKDIR /my-project
COPY . .
RUN gradle clean build -x test

FROM amazoncorretto:17-alpine
RUN adduser -D builder
USER builder
WORKDIR /spring-app
COPY --from=build /my-project/build/libs/mapsAdvisor-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
