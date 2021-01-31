import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Game } from '../shared/game.model';
import { GameService } from '../shared/game.service';
import { HandSign } from '../shared/hand-sign.enum';
import { MoveResult } from '../shared/move-result.enum';
import { Move } from '../shared/move.model';

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
      .subscribe((game: Game) => this.game = game);
  }

  onSignClicked(event: any, sign: HandSign) {
    this.gameService.makeMove(this.game.id, sign)
      .subscribe((game: Game) => {
        this.game = game;
      });
  }

  getCurrentMove(): Move {
    const lastRound = Math.max(...this.game.moves.map(move => move.round));
    return this.game.moves.find(move => move.round === lastRound);
  }

}
