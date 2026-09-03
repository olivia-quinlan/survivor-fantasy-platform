import { Component, inject, OnInit, signal } from '@angular/core';
import { RosterService } from '../../services/roster.service';
import { Season } from '../../models/season.model';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { buildTribeGradient } from '../../utils/tribe-gradient.util';

@Component({
  selector: 'app-season-picker',
  imports: [RouterLink, CommonModule],
  templateUrl: './season-picker.html',
  styleUrl: './season-picker.css',
})
export class SeasonPicker implements OnInit {
  private rosterService = inject(RosterService);

  public seasons = signal<Season[]>([]);
  
  ngOnInit(): void {
    this.rosterService.getAllSeasons().subscribe({
      next: (data) => {
        data.sort((a,b)=> b.premiereDate.localeCompare(a.premiereDate));
        this.seasons.set(data);
      },
      error: (err) => console.error('Network error flagged:', err)
    });
  }

  tribeGradient(season: Season): string {
    return buildTribeGradient(season.tribeColors);
  }

  seasonStatus(season: Season): 'upcoming' | 'airing' | 'complete' {
    if (season.finaleDate !== null) {
      return 'complete';
    }
    if (new Date(season.premiereDate) > new Date()) {
      return 'upcoming';
    }
    return 'airing';
  }
}
