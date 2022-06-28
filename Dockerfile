FROM ubuntu:18.04 as builder

WORKDIR /app

LABEL Description="worldProfe proyect"

ENV DEBIAN_FRONTEND noninteractive

ARG JAVA_VERSION=8
ARG JAVA_RELEASE=JDK

ENV JAVA_HOME=/usr

RUN bash -c ' \
    set -euxo pipefail && \
    apt-get update && \
    pkg="openjdk-$JAVA_VERSION"; \
    if [ "$JAVA_RELEASE" = "JDK" ]; then \
        pkg="$pkg-jdk-headless"; \
    else \
        pkg="$pkg-jre-headless"; \
    fi; \
    apt-get install -y --no-install-recommends wget unzip "$pkg" openjfx=8u161-b12-1ubuntu2 libopenjfx-java=8u161-b12-1ubuntu2  libopenjfx-jni=8u161-b12-1ubuntu2 && \
    apt-mark hold openjfx libopenjfx-java libopenjfx-jni && \
    apt-get clean'


CMD /bin/bash

#install Gradle
RUN wget -q https://services.gradle.org/distributions/gradle-4.5.1-bin.zip \
    && unzip gradle-4.5.1-bin.zip -d /opt \
    && rm gradle-4.5.1-bin.zip

# Set Gradle in the environment variables
ENV GRADLE_HOME /opt/gradle-4.5.1
ENV PATH $PATH:/opt/gradle-4.5.1/bin

COPY --chown=root:root . .

RUN ./gradlew war --stacktrace --info

####Tomcat
FROM tomcat:8.0
COPY --from=builder /app/build/libs/worldprofe.war /usr/local/tomcat/webapps/

#despliegue server iomos
RUN mkdir -p /usr/local/tomcat/cert
COPY ./deploy/server.xml /usr/local/tomcat/conf/server.xml
COPY ./deploy/_.plataformawp.es_private_key.pfx /usr/local/tomcat/cert/_.plataformawp.es_private_key.pfx
CMD ["catalina.sh", "run"]