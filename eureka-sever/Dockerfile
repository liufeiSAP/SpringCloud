FROM openjdk:8-jre
ARG PACKAGE="eureka-sever-0.0.1-SNAPSHOT.jar"
ARG workdir=/app
WORKDIR ${workdir}
COPY target/${PACKAGE} /${workdir}/${PACKAGE}
EXPOSE 7070
ENTRYPOINT ["java","-jar","eureka-sever-0.0.1-SNAPSHOT.jar"]