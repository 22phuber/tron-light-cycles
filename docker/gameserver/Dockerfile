FROM maven:3-openjdk-11 AS build_stage

ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

RUN mkdir -p /app
COPY files/gameserver /app

WORKDIR /app
RUN mvn -T 1C install

# FROM openjdk:11-jdk-slim
# # ------------------------------------------------------------------------------

# RUN mkdir -p /app
# WORKDIR /app
# COPY --from=build_stage /app/ch.tron.game/target/game-1.0-SNAPSHOT.jar /app/ch.tron.middleman/target/middleman-1.0-SNAPSHOT.jar /app/ch.tron.appmain/target/appmain-1.0-SNAPSHOT.jar /app/ch.tron.transport/target/transport-1.0-SNAPSHOT.jar ./

# # RUN java --module-path /app/ch.tron.game/target/game-1.0-SNAPSHOT.jar:/app/ch.tron.middleman/target/middleman-1.0-SNAPSHOT.j:/app/ch.tron.appmain/target/appmain-1.0-SNAPSHOT.j:/app/ch.tron.transport/target/transport-1.0-SNAPSHOT.jar -m ch.tron.appmain/ch.tron.appmain.GameServer