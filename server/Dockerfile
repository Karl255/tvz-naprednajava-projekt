FROM eclipse-temurin:24-jdk as builder
WORKDIR /app
COPY server/ ./server/
WORKDIR /app/server

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:24-jre
WORKDIR /app
COPY --from=builder /app/server/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
