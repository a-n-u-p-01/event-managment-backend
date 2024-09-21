# Use a base image that includes Java and Maven (Java 17 for stability)
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project directory into the container
COPY . .

# Build the project using Maven
RUN mvn clean package

# Second stage: Use a smaller base image to run the Java application
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled artifact from the build stage
COPY --from=build /app/target/event-managment-system-0.0.1-SNAPSHOT.jar /app/app.jar

# Command to run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
