import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Game } from '../shared/game.model';
import { GameService } from '../shared/game.service';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent implements OnInit {

  game: Game;

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

}
