FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
ARG JAR_FILE=out/artifacts/jpaapi_jar/jpaapi.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]
RUN addgroup -S spring && adduser -S spring -G spring -h /app -s /bin/false
USER spring:spring