FROM openjdk:11-jdk-stretch
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-Dspring.datasource.url=jdbc:postgresql://db:5432/delfi","-jar","/app.jar"]