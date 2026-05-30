FROM maven:3.9.6-eclipse-temurin-11 AS build
WORKDIR /workspace
COPY . .
ARG MODULE=NewLink_Accounting
RUN mvn -pl ${MODULE} -am -DskipTests package
RUN mvn -pl ${MODULE} -DskipTests spring-boot:repackage

FROM eclipse-temurin:11-jre
WORKDIR /app
ARG MODULE=NewLink_Accounting
COPY --from=build /workspace/${MODULE}/target/*.war /app/app.war
ENV JAVA_OPTS=""
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.war"]
