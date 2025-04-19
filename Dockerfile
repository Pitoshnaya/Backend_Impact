FROM openjdk:21

COPY ./ /app
COPY target/Impact-0.0.1-SNAPSHOT.jar app.jar
WORKDIR /app

RUN sh ./mvnw clean package




