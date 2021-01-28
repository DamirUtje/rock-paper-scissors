# Rock-Paper-Scissors
This repository contains an implementation of the Rock-Paper-Scissors game. Its backend provides a RESTful API service which is based on Spring Boot. The associated frontend was implemented with Angular8.

## Rules
In addition to the standard mode with allowed hand signs rock, paper and scissors, the game contains an extended mode with the hand sign well.

Default mode:
* Rock beats Scissors
* Scissors beats Paper
* Paper beats Rock

Extended mode:
* Rock beats Scissors
* Scissors beats Paper
* Paper beats Rock and Fountain
* Fountain beats Rock and Scissors

The extended mode can be enabled via REST-api parameters.
