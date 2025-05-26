FROM docker-remote-docker-hub.jfrog.adeo.cloud/eclipse-temurin:21-jdk AS builder
WORKDIR /app
ARG MODULE_NAME
ARG JAR_FILE=${MODULE_NAME}/target/${MODULE_NAME}.jar

COPY ${JAR_FILE} app.jar
# Extracts the application to multiple layer folders
RUN java -Djarmode=layertools -jar app.jar extract

FROM adeo-docker.jfrog.io/dockerfiles-distroless-collection/distroless-adeo-java21:latest
WORKDIR /app

COPY --from=builder app/dependencies/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
