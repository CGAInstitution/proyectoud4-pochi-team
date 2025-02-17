# Imagen base de OpenJDK 17
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el JAR dentro del contenedor
COPY mads-todolist-1.0.0.jar app.jar

# Exponer el puerto en el que corre Spring Boot (puedes modificarlo si tu app usa otro puerto)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
