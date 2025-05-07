import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAllDocumentsComponent } from './admin-all-documents.component';

describe('AdminAllDocumentsComponent', () => {
  let component: AdminAllDocumentsComponent;
  let fixture: ComponentFixture<AdminAllDocumentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminAllDocumentsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminAllDocumentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
