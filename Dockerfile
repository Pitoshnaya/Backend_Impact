FROM amazoncorretto:21

WORKDIR /app

COPY target/springbootapp-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["make start", "make stop"]