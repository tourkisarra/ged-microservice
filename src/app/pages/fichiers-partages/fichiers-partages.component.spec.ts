import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FichiersPartagesComponent } from './fichiers-partages.component';

describe('FichiersPartagesComponent', () => {
  let component: FichiersPartagesComponent;
  let fixture: ComponentFixture<FichiersPartagesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FichiersPartagesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FichiersPartagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
