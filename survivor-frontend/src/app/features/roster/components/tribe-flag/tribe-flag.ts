import { Component, input } from '@angular/core';

@Component({
  selector: 'app-tribe-flag',
  imports: [],
  templateUrl: './tribe-flag.html',
  styleUrl: './tribe-flag.css',
})
export class TribeFlag {
  tribeName = input.required<string>();
  tribeColorHex = input.required<string>();
}
