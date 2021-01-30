import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GameStartComponent } from './game-start/game-start.component';

const routes: Routes = [
  { path: '', component: GameStartComponent },
  // otherwise redirect to home
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
