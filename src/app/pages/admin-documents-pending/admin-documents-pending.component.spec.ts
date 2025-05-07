import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDocumentsPendingComponent } from './admin-documents-pending.component';

describe('AdminDocumentsPendingComponent', () => {
  let component: AdminDocumentsPendingComponent;
  let fixture: ComponentFixture<AdminDocumentsPendingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminDocumentsPendingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminDocumentsPendingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
