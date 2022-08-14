FROM openjdk:11-jre-slim

ADD target/auth-service-0.0.1-SNAPSHOT.jar AuthService-app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/AuthService-app.jar"]