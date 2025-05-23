import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgentReclamationsComponent } from './agent-reclamations.component';

describe('AgentReclamationsComponent', () => {
  let component: AgentReclamationsComponent;
  let fixture: ComponentFixture<AgentReclamationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgentReclamationsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgentReclamationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
