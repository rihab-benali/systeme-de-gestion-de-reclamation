// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { LayoutComponent } from './dashboard/layout.component';
import { authGuard } from './auth/auth.guard';
import { roleGuard } from './auth/role.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'auth/login', pathMatch: 'full' },

  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.routes').then((m) => m.AUTH_ROUTES),
  },

  {
    path: 'dashboard',
    component: LayoutComponent,
    canActivate: [authGuard],
    children: [
      // Admin routes
      {
        path: 'admin-home',
        canActivate: [roleGuard],
        data: { roles: ['admin'] },
        loadComponent: () =>
          import('./dashboard/admin/admin-home/admin-home.component').then(
            (m) => m.AdminHomeComponent
          ),
      },
      {
        path: 'users',
        canActivate: [roleGuard],
        data: { roles: ['client'] },
        loadComponent: () =>
          import('./dashboard/admin/users/users.component').then(
            (m) => m.UsersComponent
          ),
      },
      {
        path: 'agents',
        canActivate: [roleGuard],
        data: { roles: ['agent'] },
        loadComponent: () =>
          import('./dashboard/admin/agents/agents.component').then(
            (m) => m.AgentsComponent
          ),
      },
      {
        path: 'reports',
        canActivate: [roleGuard],
        data: { roles: ['admin'] },
        loadComponent: () =>
          import('./dashboard/admin/reports/reports.component').then(
            (m) => m.ReportsComponent
          ),
      },

      // Agent routes
      {
        path: 'agent-reclamations',
        canActivate: [roleGuard],
        data: { roles: ['agent'] },
        loadComponent: () =>
          import(
            './dashboard/agent/agent-reclamations/agent-reclamations.component'
          ).then((m) => m.AgentReclamationsComponent),
      },
      {
        path: 'suivi',
        canActivate: [roleGuard],
        data: { roles: ['agent'] },
        loadComponent: () =>
          import('./dashboard/agent/suivi/suivi.component').then(
            (m) => m.SuiviComponent
          ),
      },
      {
        path: 'stats',
        canActivate: [roleGuard],
        data: { roles: ['agent'] },
        loadComponent: () =>
          import('./dashboard/agent/stats/stats.component').then(
            (m) => m.StatsComponent
          ),
      },

      // Client routes
      {
        path: 'reclamations',
        loadComponent: () =>
          import('./dashboard/client/reclamations/reclamations.component').then(
            (m) => m.ReclamationsComponent
          ),
      },
      {
        path: 'new-reclamation',
        canActivate: [roleGuard],
        data: { roles: ['client'] },
        loadComponent: () =>
          import(
            './dashboard/client/new-reclamation/new-reclamation.component'
          ).then((m) => m.NewReclamationComponent),
      },
      {
        path: 'profile',
        loadComponent: () =>
          import('./dashboard/shared/profile/profile.component').then(
            (m) => m.ProfileComponent
          ),
      },

      // Shared routes
      {
        path: 'settings',
        canActivate: [roleGuard],
        data: { roles: ['admin'] },
        loadComponent: () =>
          import('./dashboard/admin/settings/settings.component').then(
            (m) => m.SettingsComponent
          ),
      },

      // Default redirect
      {
        path: '',
        redirectTo: 'reclamations',
        pathMatch: 'full',
      },
    ],
  },
];
