# Rock-Paper-Scissors

This repository represents a web-based rock-paper-scissors game. The goal was to contrast Java and .NET technologies as well as backend architecture best practices. The Java backend provides a RESTful API service which is based on Spring Boot 2.4.2. Framework version is 11 and the build tool is Maven. C# backend is implemented in .NET Core5. The associated frontend is implemented with Angular Matrial 11.1.

## Rules and settings

The game is played in best-of mode. If a player wins the predefined odd number of rounds, the entire game is considered won. In addition to the standard set of allowed hand signs rock, paper and scissors, the game contains an extended set with the hand sign "Well".

Default mode:

- Rock beats Scissors
- Scissors beats Paper
- Paper beats Rock

Extended mode:

- Rock beats Scissors
- Scissors beats Paper
- Paper beats Rock and Well
- Well beats Rock and Scissors

Both settings can optionally be configured via the REST API parameters. Otherwise, the classic set of hand signs in the best-of-3 mode applies.

## Install
Before installing the project, make sure you have gone through the [Spring Boot](https://spring.io/guides/gs/spring-boot/)
and [Angular](https://angular.io/cli) / [Angular Material](https://material.angular.io/guide/getting-started) instructions.

### Backend
After you downloaded the code you can build it with Maven

```
$ mvn package
```
and execute it with

```
$ mvn spring-boot:run
```
the application starts a webserver that listens on port 8080

### Frontend
After the download, navigate to the appropriate folder and execute the commands:

```
$ npm install
```
and 
```
$ ng serve
```
Make sure you have nodeJS and angular-cli installed on your computer.
