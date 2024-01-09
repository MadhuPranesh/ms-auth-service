FROM openjdk:11-jre-slim
VOLUME /tmp
COPY target/*.jar AuthService-app.jar
ENTRYPOINT ["java","-jar","/AuthService-app.jar"]
EXPOSE 8081
