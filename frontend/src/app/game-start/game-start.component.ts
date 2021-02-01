import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GameMode } from '../shared/game-mode.enum';
import { GameStartDto } from '../shared/game-start-dto.model';
import { GameService } from '../shared/game.service';

@Component({
  selector: 'app-game-start',
  templateUrl: './game-start.component.html',
  styleUrls: ['./game-start.component.scss']
})
export class GameStartComponent implements OnInit {

  bestOfRounds = 3;
  readonly maxRounds = 5;
  readonly gameModes = GameMode;
  selectedMode = GameMode.Classic;

  constructor(
    private gameService: GameService,
    private router: Router,
    ) { }

  ngOnInit(): void {
  }

  onModeChange(event: any) {
    this.selectedMode = event.value;
  }

  onRoundsReduce() {
    const rounds = this.bestOfRounds - 2;
    if (rounds >= 1) {
      this.bestOfRounds = rounds;
    }
  }

  canReduceRounds(): boolean {
    return this.bestOfRounds <= 1;
  }

  onRoundsIncrease() {
    const rounds = this.bestOfRounds + 2;
    if (rounds <= this.maxRounds) {
      this.bestOfRounds = rounds;
    }
  }

  canIncreaseRounds(): boolean {
    return this.bestOfRounds >= this.maxRounds;
  }

  getModeDescription(mode: GameMode): string {
    let description: string;
    if (mode === GameMode.Classic) {
      description = 'Rock, Paper and Scissors';
    } else {
      description = 'Rock, Paper, Scissors and Well';
    }
    return description;
  }

  startGame(mode: GameMode) {
    const game = {
      mode: `${mode}`,
      bestOfRounds: this.bestOfRounds
    } as GameStartDto;

    this.gameService.startGame(game).subscribe(res => {
      const newGameLocation = res.headers.get('Location');
      const gameId = newGameLocation.split('/').pop();
      this.router.navigate([`game/${gameId}`]);
    });
  }
}
