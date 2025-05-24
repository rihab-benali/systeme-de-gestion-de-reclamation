import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly TOKEN_KEY = 'authToken';
  private readonly ROLE_KEY = 'userRole';

  constructor() {}

  isAuthenticated(): boolean {
    return !!this.getToken() && !this.isTokenExpired();
  }

  getUserRole(): string | null {
    return localStorage.getItem(this.ROLE_KEY);
  }

  login(token: string, clientId : string, agentId: string): void {
    const payload = this.decodeToken(token);
    console.log(payload)
    const role = payload?.role || null;
    
    localStorage.setItem(this.TOKEN_KEY, token);
    if (role) {
      localStorage.setItem(this.ROLE_KEY, role);
    } 
    if(clientId) localStorage.setItem('clientID', clientId)
    if (agentId) localStorage.setItem('agentID', agentId)
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.ROLE_KEY);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  public decodeToken(token: string): any {
    try {
      const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      return JSON.parse(atob(base64));
    } catch (e) {
      console.error('Error decoding token', e);
      return null;
    }
  }

  private isTokenExpired(): boolean {
    const token = this.getToken();
    if (!token) return true;

    const payload = this.decodeToken(token);
    if (!payload || !payload.exp) return true;

    const expirationDate = new Date(0);
    expirationDate.setUTCSeconds(payload.exp);
    return expirationDate.valueOf() < new Date().valueOf();
  }
}