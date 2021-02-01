import { trigger, style, transition, animate } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { GameStartDto } from '../shared/game-start-dto.model';
import { GameState } from '../shared/game-state.enum';
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
  readonly states = GameState;

  game: Game;
  moving: boolean = false;

  constructor(
    private gameService: GameService,
    private route: ActivatedRoute,
    private router: Router,
    public confirmDialog: MatDialog
  ) {
    route.params.subscribe(params => {
      const id = params['id'];
      this.getGame(id);
    });
  }

  ngOnInit(): void {
  }

  private getGame(gameId?: number): void {
    const id = gameId || +this.route.snapshot.paramMap.get('id');

    this.gameService.getGame(id)
      .subscribe((game: Game) => this.game = game, error => {
        if (error && error.status == 404) {
          this.router.navigate(['not-found']);
        }
      });
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

  onSignClicked(sign: HandSign) {
    this.moving = true;
    this.gameService.makeMove(this.game.id, sign)
      .subscribe((game: Game) => {
        this.game = game;
        setTimeout(() => this.moving = false, 2000);
      });
  }

  restartGame(): void {
    if (this.game.state === GameState.Started) {
      this.abortGame(true);
    } else {
      this.restartNewGame();
    }
  }

  private restartNewGame() {
    const newGame = {
      mode: this.game.mode.toString(),
      bestOfRounds: this.game.bestOfRounds
    } as GameStartDto;

    this.gameService.startGame(newGame).subscribe(res => {
      const newGameLocation = res.headers.get('Location');
      const gameId = newGameLocation.split('/').pop();
      this.router.navigate([`game/${gameId}`]);
    });
  }

  newGame(): void {
    if (this.game.state === GameState.Started) {
      this.abortGame(false);
    } else {
      this.router.navigate(['start']);
    }
  }

  private abortGame(isRestart: boolean) {
    const dialogRef = this.confirmDialog.open(ConfirmDialogComponent, {
      width: '350px',
      data: {
        dialogTitle: 'Abort this game?',
        confirmText: 'This game is not lost yet. Do you really want to abort it?'
      }
    });

    dialogRef.afterClosed().subscribe((abortConfirmed: boolean) => {
      if (abortConfirmed) {
        this.gameService.abortGame(this.game.id).subscribe(() => {
          if (isRestart) {
            this.restartNewGame();
          } else {
            this.router.navigate(['start']);
          }
        });
      }
    });
  }
}
