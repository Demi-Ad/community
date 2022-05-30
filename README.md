# Spring Boot Community


## Getting Started

## Inmemory DB
```
1. Build Jar
2. java -jar -Dspring.profiles.active=h2 /build/libs/community-0.0.1-SNAPSHOT.jar
```

## Docker-Compose
```
1. docker pull votm777/community
2. ./Docker-compose $ : docker-compose up -d
```

---
### Prerequisites /

```
JAVA 11
```
---
### Build
Location = [gradlew](/gradlew)
```shell
gradelw build
```
> ### args = {-x test} 테스트 생략
> ### output = /build/libs/*.jar

---