FROM openjdk:16-jdk-buster
VOLUME /tmp
ARG JAR_FILE
COPY target/${JAR_FILE} scraper.jar
ENTRYPOINT ["java","-jar","/scraper.jar"]
