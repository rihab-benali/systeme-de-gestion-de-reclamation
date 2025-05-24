// src/app/dashboard/layout.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    MatIconModule,
    MatMenuModule,
    MatButtonModule,
  ],
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss'],
})
export class LayoutComponent implements OnInit {
  userType: string = ''; // Will be set from auth service
  navLinks: Array<{ icon: string; label: string; route: string }> = [];

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    // Get user type from auth service
    this.userType = this.authService.getUserRole()?.toLowerCase() || 'client';
    this.setNavLinks();
  }

  private setNavLinks(): void {
    switch (this.userType.toLowerCase()) {
      case 'admin':
        this.navLinks = [
          {
            icon: 'dashboard',
            label: 'Dashboard',
            route: '/dashboard/admin-home',
          },
          {
            icon: 'assignment',
            label: 'All Reclamations',
            route: '/dashboard/reclamations',
          },
          {
            icon: 'people',
            label: 'Users',
            route: '/dashboard/users',
          },
          {
            icon: 'support_agent',
            label: 'Agents',
            route: '/dashboard/agents',
          },
          {
            icon: 'assessment',
            label: 'Reports',
            route: '/dashboard/reports',
          },
          {
            icon: 'settings',
            label: 'Settings',
            route: '/dashboard/settings',
          },
        ];
        break;

      case 'agent_sav':
        this.navLinks = [
          {
            icon: 'assignment_ind',
            label: 'Assigned Reclamations',
            route: '/dashboard/agent-reclamations',
          },
          {
            icon: 'list_alt',
            label: 'Suivie Reclamations ',
            route: '/dashboard/logs',
          },
        ];
        break;

      case 'client':
        this.navLinks = [
          {
            icon: 'assignment',
            label: 'My Reclamations',
            route: '/dashboard/reclamations',
          },
        ];
        break;

      default:
        this.navLinks = [
          {
            icon: 'assignment',
            label: 'Reclamations',
            route: '/dashboard/reclamations',
          },
        ];
        break;
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }
}
