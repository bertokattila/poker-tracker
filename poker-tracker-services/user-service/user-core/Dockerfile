FROM openjdk:17-jdk-slim

#alkalmazas mappaja
WORKDIR /user-service

#atmasoljuk az artifactot
COPY target/user-core-1.0-SNAPSHOT.jar user-core.jar

#futtatjuk az alkalmazast
ENTRYPOINT ["java","-jar","/user-service/user-core.jar"]
