# Dockerfile (provider) – FIXED WITH ECLIPSE TEMURIN ALPINE (stable OpenJDK 17 on Alpine)
FROM eclipse-temurin:17-jdk-alpine

LABEL maintainer="Roman Islam" \
      description="X-Road Provider Service" \
      version="1.0"

# Create a non-root user (best practice for security)
RUN addgroup -g 1000 spring && \
    adduser -D -s /bin/sh -u 1000 -G spring -g spring spring

# Set working directory
WORKDIR /app

# Copy the JAR (matches your exact file: Provider-0.0.1-SNAPSHOT.jar)
COPY target/Provider-0.0.1-SNAPSHOT.jar app.jar

# Change ownership to the spring user
RUN chown spring:spring app.jar

# Run as non-root
USER spring:spring

# Expose the port your app uses
EXPOSE 8080

# Start the app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]