import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-reclamations',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './reclamations.component.html',
  styleUrls: ['./reclamations.component.scss'],
})
export class ReclamationsComponent {
  reclamations = [
    { id: 1, title: 'Late Delivery', status: 'Pending' },
    { id: 2, title: 'Wrong Item', status: 'Resolved' },
  ];

  constructor() {}
}
