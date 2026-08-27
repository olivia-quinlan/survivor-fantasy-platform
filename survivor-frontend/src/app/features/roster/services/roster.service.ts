import { inject, Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { SeasonContestant } from "../models/season-contestant.model";

@Injectable({ providedIn: 'root'})
export class RosterService {
    private http : HttpClient = inject(HttpClient);
    private baseUrl : string = `http://localhost:8080/api/v1/public/seasons`;

    getSeasonRoster(seasonId: string) : Observable<SeasonContestant[]> {
        return this.http.get<SeasonContestant[]>(`${this.baseUrl}/${seasonId}/contestants`);
    }

    getContestantAppearanceDetail(appearanceId: number) : Observable<SeasonContestant> {
        return this.http.get<SeasonContestant>(`${this.baseUrl.replace('/seasons', '/contestants')}/${appearanceId}`)
    }
}