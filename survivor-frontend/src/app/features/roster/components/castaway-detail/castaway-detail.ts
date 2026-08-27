import { Component, OnInit, inject, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RosterService } from '../../services/roster.service';
import { SeasonContestant } from '../../models/season-contestant.model';
import { TribeFlag } from '../tribe-flag/tribe-flag';

@Component({
  selector: 'app-castaway-detail',
  imports: [CommonModule, RouterLink, TribeFlag],
  templateUrl: './castaway-detail.html',
  styleUrl: './castaway-detail.css',
  standalone: true,
})
export class CastawayDetail implements OnInit {
  private route = inject(ActivatedRoute);
  private rosterService = inject(RosterService);
  

  public contestant = signal<SeasonContestant | null>(null);

  ngOnInit(): void {
    const idParam = Number(this.route.snapshot.paramMap.get('id'));
    this.rosterService.getContestantAppearanceDetail(idParam).subscribe({
      next: (data) => {
        this.contestant.set(data);
       
      },
      error: (err) => console.error('Error fetching contestant detail:', err)
    });
  }
}
