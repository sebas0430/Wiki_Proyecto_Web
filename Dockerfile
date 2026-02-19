# ============================
# Etapa 1: Compilar el proyecto
# ============================
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar archivos de configuracion de Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY mvnw.cmd .

# Descargar dependencias primero (aprovecha cache de Docker)
RUN mvn dependency:go-offline -B

# Copiar el codigo fuente
COPY src ./src

# Compilar y empaquetar sin ejecutar tests
RUN mvn clean package -DskipTests

# ============================
# Etapa 2: Ejecutar la aplicacion
# ============================
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copiar el WAR generado desde la etapa de compilacion
COPY --from=build /app/target/*.war app.war

# Puerto por defecto de Spring Boot
EXPOSE 8080

# Ejecutar la aplicacion
ENTRYPOINT ["java", "-jar", "app.war"]
