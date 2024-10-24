FROM openjdk:17
LABEL maintainer="Görkem"
ADD target/Tivibu-0.0.1-SNAPSHOT.jar tivibu.jar
ENTRYPOINT ["java","-jar","/tivibu.jar"]