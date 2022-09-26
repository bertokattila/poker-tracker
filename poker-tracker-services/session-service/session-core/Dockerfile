FROM openjdk:17-jdk-slim

#alkalmazas mappaja
WORKDIR /session-service

#atmasoljuk az artifactot
COPY target/session-core-1.0-SNAPSHOT.jar session-core.jar

#futtatjuk az alkalmazast
ENTRYPOINT ["java","-jar","/session-service/session-core.jar"]
