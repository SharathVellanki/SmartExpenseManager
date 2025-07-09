FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/SmartExpenseManager-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application.properties application.properties
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
