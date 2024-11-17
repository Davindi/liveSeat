import { Component } from '@angular/core';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzCheckboxModule } from 'ng-zorro-antd/checkbox';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { AuthService } from '../auth.service';
import { Router, RouterLink  } from '@angular/router';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AppService } from '../../service/app.service';
import { NzNotificationService } from 'ng-zorro-antd/notification';
@Component({
  selector: 'app-sign-up',
  standalone: true,
  imports: [NzFormModule, NzInputModule, NzSelectModule,NzButtonModule,NzCheckboxModule,ReactiveFormsModule, RouterLink ],
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.css'
})
export class SignUpComponent {
  username: string = '';
  password: string = '';

  constructor(private authService: AuthService, private router: Router,private fb: NonNullableFormBuilder,private appService: AppService,private notification: NzNotificationService,) {}

  registerForm = this.fb.group({
    firstname: this.fb.control('', [Validators.required]),
    email: this.fb.control('', [Validators.required]),
    role: this.fb.control('', [Validators.required]),
    username: this.fb.control('', [Validators.required]),
    password: this.fb.control('', [Validators.required]),
   
  });

  submitForm(): void {
    if (this.registerForm.valid) {
      this.appService.signUp(this.registerForm.value ).subscribe({
        next: (res) => {
          this.notification.create(
            'success',
            'Registered Successfully!',
            'welcome to LiveSeat'
            
          );
        },
        error: (error) => {
          this.notification.create(
            'error',
            'System Error!',
            'Not registerd'
          );
        },
      });
      console.log('submit', this.registerForm.value);
    } else {
      Object.values(this.registerForm.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
    }
  }

}
