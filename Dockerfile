#Image containing the Java Runtime
FROM  openjdk:11-slim as build

#information around who maintains the image
MAINTAINER coop.bank.com

#BankApp Jar copying from target to the container
COPY target/BankApp-0.0.1-SNAPSHOT.jar BankApp-0.0.1-SNAPSHOT.jar

#BankApp Jar to be started in start the container
ENTRYPOINT ["java","-jar","/BankApp-0.0.1-SNAPSHOT.jar"]