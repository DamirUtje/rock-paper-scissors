import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment";
import { GameStartDto } from "./game-start-dto.model";
import { Game } from "./game.model";

@Injectable({ providedIn: 'root' })
export class GameService {

  private gameUrl = `${environment.baseUrl}/game`;  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    observe: "response" as 'body',
  };

  constructor(private http: HttpClient) { }

  /** Start a new game with given game settings */
  startGame(game?: GameStartDto): Observable<any> {
    return this.http.post(`${this.gameUrl}/start`, game, this.httpOptions).pipe();
  }

  /** GET game by id */
  getGame(id: number): Observable<Game> {
    const url = `${this.gameUrl}/${id}`;
    return this.http.get<Game>(url).pipe();
  }

}
