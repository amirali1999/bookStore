FROM openjdk:17
MAINTAINER /tmp
EXPOSE 8080
COPY target/bookStore-0.0.1-SNAPSHOT.jar bookstore-1.0.0.jar
ENTRYPOINT ["java","-jar","/bookstore-1.0.0.jar"]