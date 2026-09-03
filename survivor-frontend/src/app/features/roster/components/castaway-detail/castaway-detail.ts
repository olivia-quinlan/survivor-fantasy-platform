import { Component, OnInit, inject, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RosterService } from '../../services/roster.service';
import { SeasonContestant } from '../../models/season-contestant.model';
import { TribeFlag } from '../tribe-flag/tribe-flag';
import { Season } from '../../models/season.model';
import { StatusBadge } from '../status-badge/status-badge';
import { GlobalContestantHistory } from '../../models/global-contestant-history.model';
import { BioSegment, parseBio } from '../../utils/bio-parser.util';
import { buildTribeGradient } from '../../utils/tribe-gradient.util';

@Component({
  selector: 'app-castaway-detail',
  imports: [CommonModule, RouterLink, TribeFlag, StatusBadge],
  templateUrl: './castaway-detail.html',
  styleUrl: './castaway-detail.css',
  standalone: true,
})
export class CastawayDetail implements OnInit {
  private route = inject(ActivatedRoute);
  private rosterService = inject(RosterService);
  

  public contestant = signal<SeasonContestant | null>(null);
  public season = signal<Season | null>(null);
  public appearances = signal<GlobalContestantHistory[]|null>(null);

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const idParam = Number(params.get('id'));
      this.rosterService.getContestantAppearanceDetail(idParam).subscribe({
        next: (data) => {
          this.contestant.set(data);

          this.rosterService.getSeasonById(data.seasonId).subscribe({
            next: (seasonData) => {
              this.season.set(seasonData);
            },
            error: (err) => console.error('Error fetching season detail:', err)  
            })
        
          this.rosterService.getGlobalContestantHistory(data.contestantId).subscribe({
            next: (contestantData) => {
              this.appearances.set(contestantData);
            },
            error: (err) => console.error('Error fetching global contestant history detail:', err)
          })
        },
        error: (err) => console.error('Error fetching contestant detail:', err)
      });
    })


  }

  bioSegments(bio: string): BioSegment[] {
    return parseBio(bio);
  }

  isWinner(contestant: SeasonContestant): boolean {
    return contestant.status === 'WINNER';
  }

  isLit(contestant: SeasonContestant): boolean {
    return contestant.status === 'WINNER' || contestant.status === null;
  }

  finalNodeLabel(contestant: SeasonContestant): string {
    switch (contestant.status) {
      case 'WINNER':
        return 'Sole Survivor';
      case 'RUNNER_UP':
        return '2nd Place';
      case 'THIRD_PLACE':
        return '3rd Place';
      case null:
        return 'Active';
      default:
        return 'Voted Out';
    }
  }

  finalNodeDay(contestant: SeasonContestant, season: Season): number | null {
    if (contestant.status === null) {
      return null;
    }
    return contestant.eliminationDay ?? season.numberOfDays;
  }

  contestantTribeColors(contestant: SeasonContestant): string[] {
    const colors = contestant.timeline.map(step => step.tribeColorHex);
    return [...new Set(colors)];
  }

  tribeGradient(contestant: SeasonContestant): string {
    return buildTribeGradient(this.contestantTribeColors(contestant));
  }
}
