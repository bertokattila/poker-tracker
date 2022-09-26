FROM openjdk:17-jdk-slim

#alkalmazas mappaja
WORKDIR /social-service

#atmasoljuk az artifactot
COPY target/social-core-1.0-SNAPSHOT.jar social-core.jar

#futtatjuk az alkalmazast
ENTRYPOINT ["java","-jar","/social-service/social-core.jar"]
