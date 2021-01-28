import { GameMode } from "./game-mode.enum";
import { GameState } from "./game-state.enum";
import { Move } from "./move.model";

export class Game {
  id: number;
  mode: GameMode;
  bestOfRounds: number;
  moves: Move[];
  state: GameState;
}
