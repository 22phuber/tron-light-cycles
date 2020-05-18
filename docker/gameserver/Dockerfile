FROM maven:3-openjdk-11 AS build_stage

# ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

RUN mkdir -p "/app" "/builded/_modules"
COPY files/gameserver /app

WORKDIR /app
RUN mvn clean package
RUN find /app -iname "*.jar" -exec cp -n --target-directory="/builded/_modules" {} +

# ---------------------------
FROM openjdk:11-jdk-slim

RUN mkdir -p /app
WORKDIR /app
COPY --from=build_stage "/builded/_modules" ./

EXPOSE 9000

CMD ["java","--module-path","/app","-m","ch.tron.appmain/ch.tron.appmain.GameServer"]
