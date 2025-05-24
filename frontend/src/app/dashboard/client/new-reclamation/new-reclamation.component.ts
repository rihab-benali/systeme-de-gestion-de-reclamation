import { Component, inject } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../auth/auth.service';

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
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatIconModule
  ],
  templateUrl: './new-reclamation.component.html',
  styleUrls: ['./new-reclamation.component.scss'],
})
export class NewReclamationComponent {
  private fb = inject(FormBuilder);
  private snackBar = inject(MatSnackBar);
  private dialogRef = inject(MatDialogRef<NewReclamationComponent>);
  private authService = inject(AuthService);

  isLoading = false;

  reclamationForm = this.fb.group({
    description: ['', [Validators.required, Validators.minLength(20)]],
    produit: ['', Validators.required],
    note: ['', Validators.required] // Added note field as text input
  });

  async onSubmit(): Promise<void> {
    if (this.reclamationForm.invalid || this.isLoading) return;

    this.isLoading = true;

    try {
      const token = this.authService.getToken();
      if (!token) {
        throw new Error('Authentication required');
      }

      // Get user ID from token
      const payload = this.authService.decodeToken(token);
      const clientId = localStorage.getItem('clientID');

      const reclamationData = {
        ...this.reclamationForm.value,
        date: new Date().toISOString().split('T')[0], // Current date
        statut: 'pending', // Default status
        client_id : clientId
      };

    const response = await fetch('http://localhost:8080/reclamations', {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      credentials: 'include', 
      body: JSON.stringify(reclamationData)
    });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Failed to create reclamation');
      }

      const createdReclamation = await response.json();
      
      this.snackBar.open('Reclamation created successfully!', 'Close', {
        duration: 3000,
      });
      this.dialogRef.close(createdReclamation);
    } catch (error) {
      console.error('Error creating reclamation:', error);
      this.snackBar.open(
        error instanceof Error ? error.message : 'Failed to create reclamation',
        'Close',
        { duration: 5000 }
      );
    } finally {
      this.isLoading = false;
    }
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