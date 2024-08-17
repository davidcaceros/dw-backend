# Usa una imagen base de OpenJDK
FROM openjdk:11-jre-slim
WORKDIR /app
COPY build/libs/servicios-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
