FROM openjdk:18-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
RUN touch app_logs.log && chmod 777 app_logs.log && ls
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]
