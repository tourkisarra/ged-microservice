import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ValidationDocumentsComponent } from './validation-documents.component';

describe('ValidationDocumentsComponent', () => {
  let component: ValidationDocumentsComponent;
  let fixture: ComponentFixture<ValidationDocumentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ValidationDocumentsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ValidationDocumentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
