FROM openjdk:21-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged jar file into the container
COPY target/template-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port that your Spring Boot application uses (default is 8080)
EXPOSE 8081

# Command to run the application when the container starts
CMD ["java", "-jar", "app.jar"]