FROM openjdk:11
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} Dj.jar
ENTRYPOINT ["java", "-jar", "/Dj.jar"]