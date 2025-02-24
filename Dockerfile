# === Etap 1: Budowanie aplikacji ===
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Kopiowanie plików wymaganych do pobrania zależności
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Nadaj uprawnienia do uruchomienia Maven Wrapper
RUN chmod +x mvnw

# Instalacja Mavena na Alpine (zamiast apt-get używamy apk)
RUN apk update && apk add maven

# Pobieramy zależności w celu ich cache'owania
RUN mvn dependency:resolve dependency:resolve-plugins -Dmaven.test.skip=true

# Kopiowanie pozostałych plików projektu
COPY . .

# Budowanie aplikacji
RUN mvn -Dmaven.test.skip=true package

# === Etap 2: Uruchamianie aplikacji ===
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Kopiowanie zbudowanej aplikacji
COPY --from=build /app/target/*.jar app.jar

EXPOSE 10000
# Uruchomienie aplikacji (teraz zmienna PORT będzie działać poprawnie)
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT}"]

