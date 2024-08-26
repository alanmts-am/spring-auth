# Usar uma imagem Maven com JDK 17 como base
FROM maven:3.8.5-eclipse-temurin-17-alpine

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

CMD ["java", "-jar", "target/app.jar"]

EXPOSE 8080