import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GameStartComponent } from './game-start/game-start.component';
import { GameComponent } from './game/game.component';
import { NotFoundPageComponent } from './not-found-page/not-found-page.component';

const routes: Routes = [
  { path: 'start', component: GameStartComponent },
  { path: 'game/:id', component: GameComponent },
  { path: 'not-found', component: NotFoundPageComponent },
  // otherwise redirect to game start
  { path: '**', redirectTo: 'start' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
