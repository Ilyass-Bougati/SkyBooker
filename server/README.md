# Server
To run this server you'll need to generate the private and public keys necessary for encoding and decoding the JWT.
That could be done following the commands.
```bash
cd src/main/resources/certs
openssl genrsa -out keypair.pem 2048
openssl rsa -in keypair.pem -pubout -out public.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
```
And you should have a running postgreSQL server. you can also update the `application.properties` to change the connection port and the database name...
