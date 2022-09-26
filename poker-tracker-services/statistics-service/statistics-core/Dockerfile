FROM openjdk:17-jdk-slim

#alkalmazas mappaja
WORKDIR /statistics-service

#atmasoljuk az artifactot
COPY target/statistics-core-1.0-SNAPSHOT.jar statistics-core.jar

#futtatjuk az alkalmazast
ENTRYPOINT ["java","-jar","/statistics-service/statistics-core.jar"]
