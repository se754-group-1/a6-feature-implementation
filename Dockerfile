# Multi-stage Dockerfile: build with Maven on JDK21, run on a smaller JRE
FROM maven:3.9.10-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml ./
COPY src ./src
RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/target/*.jar app.jar
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
    CMD curl -f -H 'Content-Type: application/json' -d '{"questionId":"q1","answer":"A","userId":"health"}' http://localhost:8080/api/answer > /dev/null || exit 1
ENTRYPOINT ["java","-jar","/app/app.jar"]
