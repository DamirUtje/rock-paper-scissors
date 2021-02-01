import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { catchError } from 'rxjs/operators';
import { environment } from "../../environments/environment";
import { GameStartDto } from "./game-start-dto.model";
import { Game } from "./game.model";

@Injectable()
export class GameService {

  private gameUrl = `${environment.baseUrl}/game`;  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  constructor(private http: HttpClient) { }

  /** Start a new game with given game settings */
  startGame(game?: GameStartDto): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      observe: "response" as 'body',
    };
    return this.http.post(`${this.gameUrl}/start`, game, httpOptions);
  }

  /** Get game by id */
  getGame(id: number): Observable<Game> {
    return this.http.get<Game>(`${this.gameUrl}/${id}`);
  }

  /** Make move for given game */
  makeMove(id: number, handSign: string): Observable<Game> {
    return this.http.post<Game>(`${this.gameUrl}/${id}/move`, JSON.stringify(handSign), this.httpOptions);
  }

  /** Abort game */
  abortGame(id: number): Observable<any> {
    return this.http.post(`${this.gameUrl}/${id}/abort`, this.httpOptions);
  }
}
