import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { NewReclamationComponent } from '../new-reclamation/new-reclamation.component';
import { AuthService } from '../../../auth/auth.service';

@Component({
  selector: 'app-reclamations',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatCardModule,
    MatDialogModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    NewReclamationComponent
  ],
  templateUrl: './reclamations.component.html',
  styleUrls: ['./reclamations.component.scss']
})
export class ReclamationsComponent implements OnInit {
  reclamations: any[] = [];
  isLoading = false;

  constructor(
    private dialog: MatDialog,
    private snackBar: MatSnackBar,
    private authService: AuthService
  ) {}

  async ngOnInit() {
    await this.loadClientReclamations();
  }

  async loadClientReclamations() {
    this.isLoading = true;
    try {
      const token = this.authService.getToken();
      if (!token) {
        throw new Error('Not authenticated');
      }

      const response = await fetch('http://localhost:8080/reclamations', {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        },
        credentials: 'include'
      });

      if (!response.ok) {
        throw new Error('Failed to fetch your reclamations');
      }

      this.reclamations = await response.json();
    } catch (error) {
      console.error('Error loading reclamations:', error);
      this.snackBar.open(
        error instanceof Error ? error.message : 'Failed to load your reclamations',
        'Close',
        { duration: 5000 }
      );
    } finally {
      this.isLoading = false;
    }
  }

  openNewReclamationDialog(): void {
    const dialogRef = this.dialog.open(NewReclamationComponent, {
      width: '500px'
    });

    dialogRef.afterClosed().subscribe(async (result) => {
      if (result) {
        await this.createClientReclamation(result);
        await this.loadClientReclamations();
      }
    });
  }

  async createClientReclamation(data: { title: string, description: string }) {
    try {
      const token = this.authService.getToken();
      if (!token) {
        throw new Error('Not authenticated');
      }

      const response = await fetch('http://localhost:8080/reclamations', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        },
        credentials: 'include',
        body: JSON.stringify({
          title: data.title,
          description: data.description
        })
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Failed to create reclamation');
      }

      this.snackBar.open('Reclamation created successfully!', 'Close', {
        duration: 3000
      });
    } catch (error) {
      console.error('Error creating reclamation:', error);
      this.snackBar.open(
        error instanceof Error ? error.message : 'Failed to create reclamation',
        'Close',
        { duration: 5000 }
      );
    }
  }

  async deleteClientReclamation(id: number) {
    try {
      const token = this.authService.getToken();
      if (!token) {
        throw new Error('Not authenticated');
      }

      const response = await fetch(`http://localhost:8080/reclamations/my/${id}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`
        },
        credentials: 'include'
      });

      if (!response.ok) {
        throw new Error('Failed to delete reclamation');
      }

      this.snackBar.open('Reclamation deleted successfully!', 'Close', {
        duration: 3000
      });
      await this.loadClientReclamations();
    } catch (error) {
      console.error('Error deleting reclamation:', error);
      this.snackBar.open(
        error instanceof Error ? error.message : 'Failed to delete reclamation',
        'Close',
        { duration: 5000 }
      );
    }
  }
}