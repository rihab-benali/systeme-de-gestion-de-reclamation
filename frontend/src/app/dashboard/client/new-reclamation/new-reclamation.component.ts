// src/app/dashboard/client/new-reclamation/new-reclamation.component.ts
import { Component, inject } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-new-reclamation',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatSnackBarModule,
    RouterModule,
  ],
  templateUrl: './new-reclamation.component.html',
  styleUrls: ['./new-reclamation.component.scss'],
})
export class NewReclamationComponent {
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private snackBar = inject(MatSnackBar);
  private dialogRef = inject(MatDialogRef<NewReclamationComponent>);

  isLoading = false;

  reclamationForm = this.fb.group({
    title: ['', [Validators.required, Validators.minLength(5)]],
    description: ['', [Validators.required, Validators.minLength(20)]],
    category: ['technical', Validators.required],
    priority: ['medium', Validators.required],
  });

  categories = [
    { value: 'technical', label: 'Technical Issue' },
    { value: 'billing', label: 'Billing Problem' },
    { value: 'delivery', label: 'Delivery Concern' },
    { value: 'other', label: 'Other' },
  ];

  priorities = [
    { value: 'low', label: 'Low' },
    { value: 'medium', label: 'Medium' },
    { value: 'high', label: 'High' },
    { value: 'critical', label: 'Critical' },
  ];

  onSubmit(): void {
    if (this.reclamationForm.invalid || this.isLoading) return;

    this.isLoading = true;

    // Mock implementation
    setTimeout(() => {
      this.isLoading = false;
      this.snackBar.open('Reclamation created successfully!', 'Close', {
        duration: 3000,
      });
      this.dialogRef.close(this.reclamationForm.value);
    }, 1500);
  }

  onCancel(): void {
    if (this.reclamationForm.dirty) {
      if (confirm('Are you sure you want to discard this reclamation?')) {
        this.dialogRef.close();
      }
    } else {
      this.dialogRef.close();
    }
  }
}
