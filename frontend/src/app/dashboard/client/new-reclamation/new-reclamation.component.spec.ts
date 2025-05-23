import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewReclamationComponent } from './new-reclamation.component';

describe('NewReclamationComponent', () => {
  let component: NewReclamationComponent;
  let fixture: ComponentFixture<NewReclamationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewReclamationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewReclamationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
