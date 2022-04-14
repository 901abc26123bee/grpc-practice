FROM openjdk:17
ENV JAVA_OPTS="-server -XX:+UseG1GC -verbose:gc -Xlog:gc:stdout -XX:InitialRAMPercentage=50 -XX:MaxRAMPercentage=90 -XX:MinRAMPercentage=50"

# https://spring.io/guides/gs/spring-boot-docker/

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar

EXPOSE 8080





