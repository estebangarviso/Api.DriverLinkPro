##
##  Spring Boot Dockerfile
##

# Labels
LABEL maintainer="Esteban Garviso <e.garvisovenegas@gmail.com>

# Global variables
ARG JAVA_VERSION=17
ARG APP_DIR=/app
# Base image
FROM openjdk:${JAVA_VERSION}-jdk-alpine AS builder
ARG APP_DIR
WORKDIR ${APP_DIR}

# Copy Maven Wrapper
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

# Copy the project source and build
COPY src ./src
RUN ./mvnw package -DskipTests

# Copy the built artifact into a second stage image
FROM openjdk:${JAVA_VERSION}-jre-alpine AS runner
ARG APP_DIR
WORKDIR ${APP_DIR}

# Copy the built artifact from the first stage image
COPY --from=builder ${APP_DIR}/target/*.jar ./app.jar

# Run the Spring Boot application
EXPOSE 8080
ENTRYPOINT ["java","-jar","./app.jar"]



