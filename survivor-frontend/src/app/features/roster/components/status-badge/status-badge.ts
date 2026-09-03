import { Component, input } from '@angular/core';

@Component({
  selector: 'app-status-badge',
  imports: [],
  templateUrl: './status-badge.html',
  styleUrl: './status-badge.css',
  host: {
    '[class.floating]': 'floating()'
  }
})
export class StatusBadge {
  status = input.required<string | null>()
  placementSummary = input.required<string>()
  floating = input<boolean>(true);
}
