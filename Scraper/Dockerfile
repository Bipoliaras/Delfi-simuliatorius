FROM openjdk:15-alpine
VOLUME /tmp
ARG JAR_FILE
COPY target/${JAR_FILE} scraper.jar
ENTRYPOINT ["java","-jar","/scraper.jar"]
