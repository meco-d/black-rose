# Stage 1: Build the application using Maven and JDK 25
FROM maven:3.9.16-eclipse-temurin-25 AS builder

WORKDIR /build

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

# Stage 2: Create the final lightweight runtime image using JRE/JDK 25
FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
