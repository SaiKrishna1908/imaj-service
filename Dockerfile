# BUILD STAGE

FROM maven:3.6.0-jdk-11-slim AS build 

COPY src /app/src
COPY pom.xml /app

RUN mvn -f /app/pom.xml clean package


# PACKAGE STAGE

FROM openjdk:11-jdk

ARG JAR_FILE=target/*.jar


RUN mkdir /app
# Image Layer
WORKDIR /app

# Image Layer: with application

COPY --from=build /app/${JAR_FILE} /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar","/usr/local/lib/app.jar" ]



