import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzDrawerModule } from 'ng-zorro-antd/drawer';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [NzDrawerModule,NzButtonModule,NzFormModule,NzInputModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  constructor(private router: Router) {}
  // isLoginDrawerVisible = false;
  // username = '';
  // password = '';

  openLogin(): void {
    this.router.navigate(['/login']);
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
