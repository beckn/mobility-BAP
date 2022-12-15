> **Disclaimer:** This project is still in beta phase. Unstable for Production Use

# Beckn-In-A-Box BAP Client

Provides BAP Client APIs for Beckn-In-A-Box

### Installation
1. Install JDK 11 <br />
    for Windows from [here](https://adoptium.net/?variant=openjdk11)<br />
    for Ubuntu from [here](https://www.ubuntu18.com/ubuntu-install-openjdk-11/)
3. Install the current version of Mongo DB from [here](https://docs.mongodb.com/manual/installation/)

or you can start local mongo instance 
```
 docker run -d --network "host" --name mongodb mongo
```

4. Install Docker from [here](https://docs.docker.com/engine/install/)
4. Setup https://github.com/beckn/beckn-protocol-dtos
5. Start Docker run `./gradlew build` to build the service
6. To run the application, start Mongo DB and run `./gradlew bootRun`

#### installation using docker 
you can change version as per your versioning or use short git sha
```
export VERSION=$(git rev-parse --short HEAD)
docker build -t backend:$VERSION -f client.dockerfile .
```
Running backend service in an container 
```
 docker run -d --network "host" --name backend backend:$VERSION
```
