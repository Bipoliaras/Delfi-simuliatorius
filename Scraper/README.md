# Delfi-simuliatorius back-end

### Required to build and run locally

* Install OpenJDK 11 and Maven

* https://adoptopenjdk.net/index.html?variant=openjdk11

* https://mkyong.com/maven/how-to-install-maven-in-windows/

### Instructions

* Run "mvn clean install"

* Run "docker build . "

* Run the built docker image with "docker run -d -p 8080:8080 [docker image name]"

* The /stories/ endpoint becomes available locally on http://localhost:8080/stories

* Initially use the POST request to scrape comments, headlines and images

* Using the GET request after scraping finishes gets random stories
