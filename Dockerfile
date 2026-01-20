# ----------------------------------------------------------------
# STAGE 1: Build the Application
# ----------------------------------------------------------------
# We use a standard Maven image to build the jar
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Copy the project files
COPY pom.xml .
COPY src ./src

# Build the application (skipping tests since CI already ran them)
RUN mvn clean package -DskipTests

# ----------------------------------------------------------------
# STAGE 2: Run the Application
# ----------------------------------------------------------------
# We use a lightweight Alpine image for the final runtime as per proposal
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Create a non-root user for security (Best Practice)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy only the compiled jar from the builder stage
COPY --from=builder /app/target/Student-GPA-Calculator-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]