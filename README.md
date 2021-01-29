# Rock-Paper-Scissors

This repository contains an implementation of the Rock-Paper-Scissors game. Its backend provides a RESTful API service which is based on Spring Boot. The associated frontend is implemented with Angular 11.

## Rules

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
