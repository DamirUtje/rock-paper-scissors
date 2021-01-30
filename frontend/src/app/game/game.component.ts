import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Game } from '../shared/game.model';
import { GameService } from '../shared/game.service';
import { HandSign } from '../shared/hand-sign.enum';
import { MoveResult } from '../shared/move-result.enum';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent implements OnInit {

  game: Game;
  readonly signs = HandSign;

  constructor(
    private gameService: GameService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.getGame();
  }

  private getGame(): void {
    const id = +this.route.snapshot.paramMap.get('id');

    this.gameService.getGame(id)
      .subscribe(game => this.game = game);
  }

  onSignClicked(event: any, sign: HandSign) {
    this.gameService.makeMove(this.game.id, 1)
      .subscribe((game: Game) => this.game);
  }

  getSignIcon(sign: HandSign): string {
    switch (sign) {
      case HandSign.Rock:
        return `${sign.toLocaleString()} 👊`;;
      case HandSign.Paper:
        return `${sign.toLocaleString()} 🤚`;;
      case HandSign.Scissors:
        return `${sign.toLocaleString()} ✌️`;;
      case HandSign.Well:
        return `${sign.toLocaleString()} 👌`;;
    }
  }

  getMoveResult(): string {
  console.log("fsd");


    const lastRound = Math.max(...this.game.moves.map(move => move.round));
    const lastMove = this.game.moves.find(move => move.round === lastRound);

    switch (lastMove.result) {
      case MoveResult.Win:
        return `${lastMove.result.toLocaleString()}! 🙂`;
      case MoveResult.Loose:
        return `${lastMove.result.toLocaleString()}! 🙁`;
      case MoveResult.Draw:
        return `${lastMove.result.toLocaleString()}! 😐`;
    }
  }

}
