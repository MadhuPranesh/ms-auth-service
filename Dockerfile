FROM openjdk:11-jre-slim
VOLUME /tmp
COPY target/auth-service-0.0.1-SNAPSHOT.jar AuthService-app.jar
ENTRYPOINT ["java","-jar","/AuthService-app.jar"]
EXPOSE 8081
