# Multi-stage build

# Stage 1: Build
FROM maven:3.8.6-openjdk-8 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM tomcat:9.0-jdk8
WORKDIR /usr/local/tomcat

# Remove default webapps
RUN rm -rf webapps/*

# Copy WAR file
COPY --from=build /app/target/ios-1.0.0-BUILD-SNAPSHOT.war webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
