# Use a base image with JDK 17
FROM eclipse-temurin:17-jdk-alpine

# Set app directory inside the container
WORKDIR /app

# Copy the Maven build output
COPY target/user-management-application-0.0.1-SNAPSHOT.jar app.jar

# Expose port (matches your Spring Boot port)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
