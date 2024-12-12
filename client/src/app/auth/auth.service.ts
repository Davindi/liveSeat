import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private roles = { admin: 'admin', vendor: 'vendor', customer: 'customer' };
  private currentUserRole: string | null = null;

  login(username: string, password: string): boolean {
    if (username === 'admin' && password === 'password') {
      this.currentUserRole = this.roles.admin;
    } else if (username === 'vendor' && password === 'password') {
      this.currentUserRole = this.roles.vendor;
    } else if (username === 'customer' && password === 'password') {
      this.currentUserRole = this.roles.customer;
    } else {
      return false;
    }
    return true;
  }

  getRole(): string | null {
    return this.currentUserRole;
  }

  logout() {
    this.currentUserRole = null;
  }
}
