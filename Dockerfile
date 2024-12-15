# === Etap 1: Budowanie aplikacji ===
FROM eclipse-temurin:21-jdk-alpine AS build

# Ustawiamy katalog roboczy w kontenerze
WORKDIR /app

# Kopiujemy Maven Wrapper oraz plik konfiguracyjny pom.xml
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Nadajemy uprawnienia do uruchomienia Maven Wrapper (mvnw)
RUN chmod +x mvnw

# Pobieramy zależności w celu ich cache'owania
RUN ./mvnw dependency:resolve dependency:resolve-plugins -Dmaven.test.skip=true

# Kopiujemy pozostałe pliki projektu
COPY . .

# Budujemy aplikację przy użyciu Maven
RUN ./mvnw -Dmaven.test.skip=true package

# === Etap 2: Uruchamianie aplikacji ===
FROM eclipse-temurin:21-jre-alpine

# Ustawiamy katalog roboczy
WORKDIR /app

# Kopiujemy zbudowaną aplikację z pierwszego etapu
COPY --from=build /app/target/*.jar app.jar

# Wystawiamy port 8080
EXPOSE 8080

# Uruchamiamy aplikację
ENTRYPOINT ["sh", "-c", "java -jar app.jar"]