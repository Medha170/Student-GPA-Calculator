# ----------------------------------------------------------------
# STAGE 1: Build the Application
# ----------------------------------------------------------------
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ----------------------------------------------------------------
# STAGE 2: Run the Application
# ----------------------------------------------------------------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# FIX: Update OS packages to patch libpng and gnupg vulnerabilities
RUN apk update && apk upgrade --no-cache

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=builder /app/target/Student-GPA-Calculator-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]