import { inject, Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { SeasonContestant } from "../models/season-contestant.model";
import { environment } from "../../../../environments/environment";
import { Season } from "../models/season.model";
import { GlobalContestantHistory } from "../models/global-contestant-history.model";

@Injectable({ providedIn: 'root'})
export class RosterService {
    private http : HttpClient = inject(HttpClient);
    private apiRoot : string = environment.apiUrl;

    getSeasonRoster(seasonId: string) : Observable<SeasonContestant[]> {
        return this.http.get<SeasonContestant[]>(`${this.apiRoot}/seasons/${seasonId}/contestants`);
    }

    getContestantAppearanceDetail(appearanceId: number) : Observable<SeasonContestant> {
        return this.http.get<SeasonContestant>(`${this.apiRoot}/contestants/${appearanceId}`);
    }

    getAllSeasons() : Observable<Season[]> {
        return this.http.get<Season[]>(`${this.apiRoot}/seasons`);
    }
    
    getSeasonById(seasonId: string) : Observable<Season> {
        return this.http.get<Season>(`${this.apiRoot}/seasons/${seasonId}`)
    }

    getGlobalContestantHistory(contestantId: number) : Observable<GlobalContestantHistory[]> {
        return this.http.get<GlobalContestantHistory[]>(`${this.apiRoot}/contestants/${contestantId}/appearanceHistory`)
    }
}