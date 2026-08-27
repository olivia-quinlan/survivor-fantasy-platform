import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CastawayDetail } from './castaway-detail';

describe('CastawayDetail', () => {
  let component: CastawayDetail;
  let fixture: ComponentFixture<CastawayDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CastawayDetail],
    }).compileComponents();

    fixture = TestBed.createComponent(CastawayDetail);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
