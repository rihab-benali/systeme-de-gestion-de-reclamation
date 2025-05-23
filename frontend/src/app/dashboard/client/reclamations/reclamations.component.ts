// src/app/dashboard/reclamations/reclamations.component.ts
import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { NewReclamationComponent } from '../new-reclamation/new-reclamation.component';

@Component({
  selector: 'app-reclamations',
  templateUrl: './reclamations.component.html',
  styleUrls: ['./reclamations.component.scss'],
})
export class ReclamationsComponent {
  reclamations: any[] = [
    { id: 1, title: 'Broken Product', status: 'Open' },
    { id: 2, title: 'Late Delivery', status: 'In Progress' },
  ];

  constructor(private dialog: MatDialog) {}

  openNewReclamationDialog(): void {
    const dialogRef = this.dialog.open(NewReclamationComponent, {
      width: '500px',
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.addNewReclamation(result);
      }
    });
  }

  addNewReclamation(data: any): void {
    const newRec = {
      id: this.reclamations.length + 1,
      title: data.title,
      description: data.description,
      status: 'New',
    };
    this.reclamations = [...this.reclamations, newRec];
  }
}
