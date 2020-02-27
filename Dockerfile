FROM openjdk:13-jdk-alpine

# Add Maintainer Info
LABEL maintainer="lukas.david@ebcont.com"

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

