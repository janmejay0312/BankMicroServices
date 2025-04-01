#start with a base image java runtime
FROM openjdk:17-jdk-slim

#information around who maintains the image
MAINTAINER Janmejay_Test

#add the application jar to the image
COPY target/account-0.0.1-SNAPSHOT.jar account-0.0.1-SNAPSHOT.jar
#Execute the application
ENTRYPOINT ["java","-jar","account-0.0.1-SNAPSHOT.jar"]