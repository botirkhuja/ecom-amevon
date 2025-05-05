FROM openjdk:25-bookworm

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /app

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]