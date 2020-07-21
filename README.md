# A simple example for CQRS and Hexagonal Architecture

## Overview
##### This project simulates a Bitcoin Store, it was used the API from https://www.mercadobitcoin.net. For more details about the project Architecture I recommend this [resume](https://herbertograca.com/2017/11/16/explicit-architecture-01-ddd-hexagonal-onion-clean-cqrs-how-i-put-it-all-together/#fundamental-blocks-of-the-system)

## Setup

#### Run on local environment

- JDK 11 (https://pkgs.org/download/openjdk-11-jdk)
- Maven >=3.6.0 (https://maven.apache.org/install.html) (Check if Java target version for your Maven is Java ^11)
- PostgreSQL 12.3

#### Run on Docker

- Docker (https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-20-04)
- Docker Compose (https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-compose-on-ubuntu-20-04)
- In the project root path run:
```
docker-compose -f docker-compose.yml up
```


## Using the API

####  Swagger UI

###### The Project provide the Swagger UI to mapping and consuming the API, you can access on local URL
```
http://localhost:8080/swagger-ui.html
```

#### Authentication
###### For to do the OAuth2 authentication you will need to use the Postman or others similar tools

- In the Header tab add: 
    - key: **Authorization**
    - value: **Basic Yml0Y29pbi1pbnZlc3RtZW50czplZHV6el90ZXN0**
- In the Body tab configure:
    - Serialization: **form-data**
    - Add the properties:
        - grant_type: password
        - username: CreatedUsername
        - password: CreatedPassword
- If your credentials are OK you should get this return
```
{
    "access_token": "YDFYGIGIggyGGYUGyguUYiIsInR5cCI6IkpXVCJ9.eyJleHAiOWllcmLSWERTWERTWERWERmU0NjIzYyIsImNsaWVudF9pZCI6ImJpdGNvaW4taW52ZERTI6WyJyZWERTEJdfQ.aV6oBLMzP7zERTERTERTblHHo5t9CtLp0",
    "token_type": "bearer",
    "refresh_token": "WERWETRoihjoiOIHOIJouyhOIJoupo.eyJleHAiOjE3NzMsdf7dfgfh90ertt9ertnJpZWxqYWNxdWllcm1lQGdtYWlsLmNvbSIsImp0aSdfg9870dfg09dfZi05NDM4LWE1NjJjZGVhZDI5MCIsImNsaWVudF9pZCI6ImJpdGNvaW4taW52ZXN0bWVudHMiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiLCJ0cnVzdCJdLCJhdGkiOiJlYmI0Y2ZhMi1iNDgxLTRkZDgtOWQzYS0zZTY0NjJlNDYyM2MifQ.hk7XB8u2AVAnlnflqzhrcjmUcHpbRliXEEk-d815fdM",
    "expires_in": 179999999,
    "scope": "read write trust",
    "jti": "ebb4cfa2-b481-4dd8-9d3a-3e6462e4623c"
}
```

- So you can copy the **"access_token"** payload e put on Swagger UI  Authorize button. The value should be put how this **Bearer {your_access_token}**, for example:
```
    Bearer YDFYGIGIggyGGYUGyguUYiIsInR5cCI6IkpXVCJ9.eyJleHAiOWllcmLSWERTWERTWERWERmU0NjIzYyIsImNsaWVudF9pZCI6ImJpdGNvaW4taW52ZERTI6WyJyZWERTEJdfQ.aV6oBLMzP7zERTERTERTblHHo5t9CtLp0
```

## Local Development

#### For the development environment you can create only the local database with docker, to do this run:
```
 docker run --name bitcoin-investment -e POSTGRES_PASSWORD=bitcoin123 -e POSTGRES_USER=bitcoin -p 5432:5432 -d postgres:12.3
```
