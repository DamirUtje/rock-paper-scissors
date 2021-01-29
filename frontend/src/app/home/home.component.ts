import { Component, OnInit } from '@angular/core';
import { GameMode } from '../shared/game-mode.enum';
import { GameStartDto } from '../shared/game-start-dto.model';
import { GameService } from '../shared/game.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  bestOfRounds = 3;
  readonly maxRounds = 7;

  constructor(private gameService: GameService) { }

  ngOnInit(): void {
  }

  onLessRounds(event: any) {
    const rounds = this.bestOfRounds - 2;
    if (rounds >= 1) {
      this.bestOfRounds = rounds;
    }
  }

  onMoreRounds(event: any) {
    const rounds = this.bestOfRounds + 2;
    if (rounds <= this.maxRounds) {
      this.bestOfRounds = rounds;
    }
  }

  startGame(event: any, gameMode: string) {
    const mode = GameMode[gameMode] || GameMode.Classic;
    const game = {
      mode: mode.toString(),
      bestOfRounds: this.bestOfRounds || 3
    } as GameStartDto;



    this.gameService.startGame(game).subscribe(res => {
      //console.log(res);

      console.log(res.headers.get('Location'));
    });
  }

}
