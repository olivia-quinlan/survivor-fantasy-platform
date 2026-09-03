import { Component, OnInit, inject, signal } from "@angular/core";
import { RosterService } from "../../services/roster.service";
import { SeasonContestant } from "../../models/season-contestant.model";
import { CastawayCard } from "../castaway-card/castaway-card";
import { CommonModule } from "@angular/common";
import { ActivatedRoute, RouterLink } from "@angular/router";
import { Season } from "../../models/season.model";
import { buildTribeGradient } from "../../utils/tribe-gradient.util";


@Component({
    selector: 'app-castaway-roster',
    standalone: true,
    imports: [CommonModule, RouterLink, CastawayCard],
    templateUrl: './castaway-roster.html',
    styleUrl: './castaway-roster.css'
})
export class CastawayRoster implements OnInit {
    private rosterService = inject(RosterService);
    private route = inject(ActivatedRoute);
    
    public contestants = signal<SeasonContestant[]>([]);
    public season = signal<Season | null>(null);

    ngOnInit(): void {
        const seasonIdParam = this.route.snapshot.paramMap.get('seasonId') ?? 'S45';
        this.rosterService.getSeasonRoster(seasonIdParam).subscribe({
            next: (data) => {
                this.contestants.set(data);
            },
            error: (err) => console.error('Network error flagged:', err)
        });
        this.rosterService.getSeasonById(seasonIdParam).subscribe({
            next: (data) => {
                this.season.set(data);
            },
            error: (err) => console.error('Network error flagged:', err)
        })
    }

    tribeGradient(season: Season): string {
        return buildTribeGradient(season.tribeColors);
    }
}