# Build Stage
FROM adoptopenjdk/openjdk11 AS build

WORKDIR /app

# 앱 실행에 필요한 파일 복사
COPY /home/ubuntu/todaktodak-6846e-firebase-adminsdk-v719d-f41e0aa4e9.json /app/src/main/resources/

# 앱 소스 코드 복사 및 빌드
COPY . .
RUN ./gradlew build

# Run Stage
FROM adoptopenjdk/openjdk11

WORKDIR /app

# Build Stage에서 빌드한 JAR 파일 복사
COPY --from=build /app/build/libs/app.jar .

CMD ["java", "-jar", "app.jar"]
