FROM openjdk:19
ARG JAR_FILE
COPY target/sneakersShop-web-service-exam-0.0.1-SNAPSHOT.jar /sneakersShop-web-service-exam-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/sneakersShop-web-service-exam-0.0.1-SNAPSHOT.jar"]