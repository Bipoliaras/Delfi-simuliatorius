FROM openjdk:16-jdk-buster
VOLUME /tmp
COPY target/*.jar scraper.jar
ENTRYPOINT ["java","-jar","/scraper.jar"]
