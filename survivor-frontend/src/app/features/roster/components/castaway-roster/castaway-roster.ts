import { Component, OnInit, inject, signal } from "@angular/core";
import { RosterService } from "../../services/roster.service";
import { SeasonContestant } from "../../models/season-contestant.model";
import { CastawayCard } from "../castaway-card/castaway-card";
import { CommonModule } from "@angular/common";
import { RouterLink } from "@angular/router";


@Component({
    selector: 'app-castaway-roster',
    standalone: true,
    imports: [CommonModule, RouterLink, CastawayCard],
    templateUrl: './castaway-roster.html',
    styleUrl: './castaway-roster.css'
})
export class CastawayRoster implements OnInit {
    private rosterService = inject(RosterService);
    
    public contestants = signal<SeasonContestant[]>([]);

    ngOnInit(): void {
        this.rosterService.getSeasonRoster('S45').subscribe({
            next: (data) => {
                this.contestants.set(data);
            },
            error: (err) => console.error('Network error flagged:', err)
        });
    }
}