import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TribeFlag } from './tribe-flag';

describe('TribeFlag', () => {
  let component: TribeFlag;
  let fixture: ComponentFixture<TribeFlag>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TribeFlag],
    }).compileComponents();

    fixture = TestBed.createComponent(TribeFlag);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
