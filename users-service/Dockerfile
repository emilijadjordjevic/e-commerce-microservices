# build stage
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /workspace

# copy pom and source
COPY pom.xml ./
COPY src ./src

# build jar without tests
RUN mvn -B -DskipTests package

# runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# copy jar from build stage
COPY --from=build /workspace/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]
