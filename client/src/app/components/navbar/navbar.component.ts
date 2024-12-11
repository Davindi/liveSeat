import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzDrawerModule } from 'ng-zorro-antd/drawer';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { log } from 'node:console';
@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [NzDrawerModule,NzButtonModule,NzFormModule,NzInputModule,CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  constructor(private router: Router) {}
  // isLoginDrawerVisible = false;
  // username = '';
  // password = '';
  ngOnInit(): void {
    this.isVendorOrCustomer();
    console.log("this.isVendorOrCustomer()",this.isVendorOrCustomer());
    
  }
  openLogin(): void {
    this.router.navigate(['/login']);
  }
  isVendorOrCustomer(): boolean {
    const token = localStorage.getItem('authToken');
    if (token) {
      const userDetails: {  
        sub: string;
        email: string;
        role: string; } = jwtDecode(token);
      return userDetails.role === 'vendor' || userDetails.role === 'customer';
    }
    return false;
  }
  // closeLoginDrawer(): void {
  //   this.isLoginDrawerVisible = false;
  // }

  // onLoginSubmit(): void {
  //   console.log('Username:', this.username);
  //   console.log('Password:', this.password);
  //   // Add login logic here
  //   this.closeLoginDrawer();
  // }
}
