# Spring Auth Test

## Private and public key 

### Running in terminal (Linux)

Private
```Shell
openssl genrsa > app.key
```
Public 
```Shell
openssl rsa -in app.key -pubout -out app.pub
```
!! Put them in "src/main/resources"

### Getting keys (No Linux)
[RSA Key Generator](https://cryptotools.net/rsagen)

## Run project (API locally)

```Shell
sudo docker compose -f src/main/docker/docker-compose.db.yml up -d
```

```Shell
mvn spring-boot:run
```

## Run Project (Full Docker)

```Shell
sudo docker build -t spring-auth .
```

```Shell
sudo docker compose up -d
```

## Postgres UUID

To activate postgres function gen_random_uuid(), run this on database

```SQL
CREATE EXTENSION IF NOT EXISTS "pgcrypto";
```