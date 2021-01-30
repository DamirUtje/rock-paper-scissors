import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GameStartComponent } from './game-start/game-start.component';
import { GameComponent } from './game/game.component';

const routes: Routes = [
  { path: 'start', component: GameStartComponent },
  { path: 'game/:id', component: GameComponent },
  // otherwise redirect to game start
  { path: '**', redirectTo: 'start' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
