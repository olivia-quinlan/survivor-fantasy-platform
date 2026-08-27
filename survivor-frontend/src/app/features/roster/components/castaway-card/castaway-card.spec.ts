import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CastawayCard } from './castaway-card';

describe('CastawayCard', () => {
  let component: CastawayCard;
  let fixture: ComponentFixture<CastawayCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CastawayCard],
    }).compileComponents();

    fixture = TestBed.createComponent(CastawayCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
