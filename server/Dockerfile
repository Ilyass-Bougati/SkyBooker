FROM openjdk:17-slim AS builder

# Install dependencies
RUN apt-get update && apt-get install -y \
    maven \
    openssl \
 && rm -rf /var/lib/apt/lists/*

WORKDIR /java
COPY . /java

# Generate keypair
RUN mkdir -p ./src/main/resources/certs
RUN openssl genrsa -out ./src/main/resources/certs/keypair.pem 2048
RUN openssl rsa -in ./src/main/resources/certs/keypair.pem -pubout -out ./src/main/resources/certs/public.pem
RUN openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in ./src/main/resources/certs/keypair.pem -out ./src/main/resources/certs/private.pem

# Build the JAR
RUN mvn package -Dmaven.test.skip=true

EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/java/target/server-0.0.1-SNAPSHOT.jar"]
