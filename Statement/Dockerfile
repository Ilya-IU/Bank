FROM amazoncorretto:17-alpine-jdk
LABEL authors="iustiugov"
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","/app.jar"]