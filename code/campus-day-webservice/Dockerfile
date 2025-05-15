FROM maven AS build
LABEL authors="ubuntu"
WORKDIR /app
COPY . .
#RUN mvn clean
RUN mvn package -Dmaven.test.skip

FROM openjdk:17
LABEL authors="ubuntu"
WORKDIR /app
COPY --from=build app/target/server-0.0.1-SNAPSHOT.jar .
EXPOSE 80
CMD ["java", "-jar", "server-0.0.1-SNAPSHOT.jar"]

