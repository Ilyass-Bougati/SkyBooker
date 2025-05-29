FROM openjdk:17-alpine AS builder
RUN apk add --no-cache maven
RUN apk add --no-cache openssl
WORKDIR /java
COPY . /java
RUN mkdir -p ./src/main/resources/certs
RUN openssl genrsa -out ./src/main/resources/certs/keypair.pem 2048
RUN openssl rsa -in ./src/main/resources/certs/keypair.pem -pubout -out ./src/main/resources/certs/public.pem
RUN openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in ./src/main/resources/certs/keypair.pem -out ./src/main/resources/certs/private.pem
RUN mvn package -Dmaven.test.skip=true
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/java/target/server-0.0.1-SNAPSHOT.jar"]

FROM builder

RUN <<EOF
apk update
apk add git bash
EOF

RUN <<EOF
addgroup -S docker
adduser -S --shell /bin/bash --ingroup docker vscode
EOF
# install Docker tools (cli, buildx, compose)
COPY --from=gloursdocker/docker / /