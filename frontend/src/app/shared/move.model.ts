import { HandSign } from "./hand-sign.enum";
import { MoveResult } from "./move-result.enum";

export class Move {
  id: number;
  userSign: HandSign;
  botSign: HandSign;
  result: MoveResult;
  round: number;
}
