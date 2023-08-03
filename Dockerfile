# Build stage
FROM maven:3.8.4-openjdk-17 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

# Package stage
FROM openjdk:17-jdk-slim
COPY --from=build /target/skilltrade-0.0.1-SNAPSHOT.jar skilltrade.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","skilltrade.jar"]
