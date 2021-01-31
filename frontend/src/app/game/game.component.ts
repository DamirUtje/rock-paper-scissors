import { trigger, state, style, transition, animate } from '@angular/animations';
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
  styleUrls: ['./game.component.scss'],
  animations: [
    trigger('slideInMe', [
      transition(':enter', [
        style({ transform: 'translateX(-100%)', opacity: 0 }),
        animate('600ms ease-in', style({ transform: 'translateX(0%)', 'opacity': 1 }))
      ]),
    ]),
    trigger('slideInBecky', [
      transition(':enter', [
        style({ transform: 'translateX(100%)', opacity: 0 }),
        animate('600ms ease-in', style({ transform: 'translateX(0%)', 'opacity': 1 }))
      ]),
    ])
  ]
})
export class GameComponent implements OnInit {

  readonly signs = HandSign;
  readonly results = MoveResult;

  game: Game;
  moving: boolean = false;

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

  get currentMove(): Move {
    let move: Move;
    if (this.game.moves.length > 0) {
      move = this.game.moves[this.game.moves.length - 1];
    }
    return move;
  }

  get currentResult(): MoveResult {
    let result: MoveResult = MoveResult.None;
    if (this.currentMove) {
      result = this.currentMove.result;
    }
    return result;
  }

  onSignClicked(event: any, sign: HandSign) {
    this.moving = true;
    this.gameService.makeMove(this.game.id, sign)
      .subscribe((game: Game) => {
        this.game = game;
        setTimeout(() => this.moving = false, 2000);
      });
  }

  getCurrentMove(): Move {
    const lastRound = Math.max(...this.game.moves.map(move => move.round));
    return this.game.moves.find(move => move.round === lastRound);
  }

  restartGame(): void {

  }
}
