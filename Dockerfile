# FROM openjdk:25-bookworm
FROM eclipse-temurin:21

# RUN ln -sf /bin/bash /bin/sh

# RUN useradd -ms /bin/bash spring
# RUN usermod -aG spring spring
# USER spring:spring

WORKDIR /app

# COPY .mvn/ .mvn
# COPY mvnw pom.xml ./
# RUN ./mvnw dependency:go-offline

# COPY src ./src

# package the application with `mvn clean package -DskipTests`
# RUN ./mvnw package -DskipTests

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

HEALTHCHECK CMD curl --fail http://localhost:8083/api/health || exit 1

ENTRYPOINT ["java","-jar","app.jar"]