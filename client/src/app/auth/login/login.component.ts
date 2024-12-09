import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router, RouterLink } from '@angular/router';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzCheckboxModule } from 'ng-zorro-antd/checkbox';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { AppService } from '../../service/app.service';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { jwtDecode } from 'jwt-decode';
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [NzFormModule, NzInputModule, NzSelectModule, NzButtonModule, NzCheckboxModule, RouterLink, ReactiveFormsModule,],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private authService: AuthService, private router: Router, private fb: NonNullableFormBuilder, private appService: AppService, private notification: NzNotificationService,) { }

  validateForm = this.fb.group({
    username: this.fb.control('', [Validators.required]),
    password: this.fb.control('', [Validators.required]),

  });

  submitForm(): void {
    if (this.validateForm.valid) {
      console.log('submit', this.validateForm.value);
      this.appService.signIn(this.validateForm.value).subscribe({
        next: (res) => {
          const token = res.token;
          console.log('res', res);

          localStorage.setItem('authToken', token);
          // Decode the token
          const userDetails: {
            sub: string;
            email: string;
            role: string;
          } = jwtDecode(token);
          console.log('User Details:', userDetails);
          
          switch (userDetails.role) {
            case 'admin':
              this.router.navigate(['/admin-dashboard']);
              break;
            case 'vendor':
            case 'customer':
              this.router.navigate(['/display-events']);
              break;
            default:
              this.notification.create('error', 'Login Failed', 'Unknown user role');
          }
          this.notification.create(
            'success',
            'Loged Successfully!',
            'welcome to LiveSeat'

          );
        },
        error: (error) => {
          this.notification.create(
            'error',
            'System Error!',
            'Invalid UserName or Password'
          );
        },
      });
    } else {
      Object.values(this.validateForm.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
    }
  }



}
