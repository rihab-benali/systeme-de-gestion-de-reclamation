import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private userRole: string | null = null;
  private authenticated = false;

  constructor() {
    // Example: get user info from localStorage or cookie
    this.authenticated = true;
    this.userRole = 'client'; // This should be set based on actual authentication logic
  }

  isAuthenticated(): boolean {
    return this.authenticated;
  }

  getUserRole(): string | null {
    return this.userRole;
  }

  logout(): void {
    // Clear user session data
    localStorage.removeItem('token');
    localStorage.removeItem('userRole');
    this.authenticated = false;
    this.userRole = null;

    // Optionally redirect or do other cleanup here
  }

  // Optionally add login or other auth methods here
}
