import { Component, input, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { SeasonContestant } from '../../models/season-contestant.model';
import { TribeFlag } from '../tribe-flag/tribe-flag';
import { StatusBadge } from '../status-badge/status-badge';

@Component({
  selector: 'app-castaway-card',
  standalone: true,
  imports: [CommonModule, RouterLink, TribeFlag, StatusBadge],
  templateUrl: './castaway-card.html',
  styleUrl: './castaway-card.css'
})
export class CastawayCard {
  imageFailed = signal(false)
  player = input.required<SeasonContestant>();
}

