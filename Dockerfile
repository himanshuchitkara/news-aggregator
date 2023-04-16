FROM maven:3.6-jdk-11 AS MAVEN_BUILD
WORKDIR /app
COPY pom.xml ./
RUN mvn verify --fail-never
COPY . ./
RUN mvn package -Dmaven.test.skip=true

# copying certificates
FROM openjdk:11-jre-slim as ssl
WORKDIR /app
COPY --from=MAVEN_BUILD /app/target/news-aggregator-*.jar /app/news-aggregator.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","news-aggregator.jar"]
