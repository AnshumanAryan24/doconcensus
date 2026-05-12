# Stage 1: Build the application
FROM gradle:jdk21-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew build --no-daemon -x test

# Stage 2: Create the runtime image
FROM openjdk:21-jdk-slim
EXPOSE 8080
RUN mkdir /app
# Copy the built JAR from the first stage
COPY --from=build /home/gradle/src/build/libs/*.jar /app/votemanager.jar
ENTRYPOINT ["java", "-jar", "/app/votemanager.jar"]
