mkdir -p ./src/main/resources/certs
openssl genrsa -out ./src/main/resources/certs/keypair.pem 2048
openssl rsa -in ./src/main/resources/certs/keypair.pem -pubout -out ./src/main/resources/certs/public.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in ./src/main/resources/certs/keypair.pem -out ./src/main/resources/certs/private.pem
