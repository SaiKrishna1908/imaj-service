FROM openjdk:11-jdk

ARG JAR_FILE=target/*.jar

RUN mkdir /app
# Image Layer
WORKDIR /app

# Image Layer: with application
COPY ${JAR_FILE} app.jar
EXPOSE 8443
RUN ls
ENTRYPOINT [ "java","-jar","app.jar" ]


