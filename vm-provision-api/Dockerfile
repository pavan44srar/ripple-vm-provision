#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:8-jdk-alpine
MAINTAINER pavan44srar@gmail.com
COPY --from=build /home/app/target/vm-provision-1.0.0.jar /usr/local/lib/vm-provision.jar
EXPOSE 3000
ENTRYPOINT ["java","-jar","/usr/local/lib/vm-provision.jar"]