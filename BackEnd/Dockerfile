# Build Stage
FROM adoptopenjdk/openjdk11 AS build

WORKDIR /app

# Copy necessary files to resources
COPY /path/to/todaktodak-6846e-firebase-adminsdk-v719d-f41e0aa4e9.json /app/src/main/resources/

# Copy app source code and build
COPY . .
RUN ./gradlew build

# Run Stage
FROM adoptopenjdk/openjdk11

WORKDIR /app

# Copy JAR file from Build Stage
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]