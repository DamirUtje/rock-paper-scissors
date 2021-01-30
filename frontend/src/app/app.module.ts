import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
//import { MatCardModule } from '@angular/material/card';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
//import { MatInputModule}  from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatExpansionModule } from '@angular/material/expansion';
import { GameComponent } from './game/game.component';
import { HttpClientModule } from '@angular/common/http';
import { GameService } from './shared/game.service';
import { GameStartComponent } from './game-start/game-start.component';
//import { MatTooltipModule } from '@angular/material/tooltip';

@NgModule({
  declarations: [
    AppComponent,
    GameComponent,
    GameStartComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    //MatCardModule,
    MatButtonToggleModule,
    //MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatExpansionModule,
    //MatTooltipModule,
  ],
  providers: [
    GameService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
