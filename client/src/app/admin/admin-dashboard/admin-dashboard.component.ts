import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NzButtonModule, NzButtonSize } from 'ng-zorro-antd/button';
import { NzModalModule } from 'ng-zorro-antd/modal';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { ReactiveFormsModule } from '@angular/forms';
import { AppService } from '../../service/app.service';
import { log } from 'console';
@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [NzButtonModule,NzModalModule, ReactiveFormsModule, CommonModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {
  size: NzButtonSize = 'large';
 
  isVisible = false;
  isConfigured = false;
  configForm: FormGroup;
  logs: { timestamp: string; message: string }[] = [];
  isSystemStarted: boolean = false; 
  currentConfig: any = null;
 

  constructor(private fb: FormBuilder, private notification: NzNotificationService,private appService: AppService,) {
    this.configForm = this.fb.group({
      totalTickets: [null, [Validators.required, Validators.min(1)]],
      ticketReleaseRate: [null, [Validators.required, Validators.min(1)]],
      customerRetrievalRate: [null, [Validators.required, Validators.min(1)]],
      maxTicketCapacity: [null, [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {
    this.appService.getSystemStatus().subscribe({
      next: (status) => (this.isSystemStarted = status),
      error: (err) => console.error('Error fetching system status', err)
    });
    this.appService.getSystemConfiguration().subscribe({
      next: (config) => {
         this.currentConfig = config;
      },
      error: (err) => {
        console.error('Failed to fetch system configuration:', err);
      },
    });
  }
  
  showModal(): void {
    this.isVisible = true;
  }

  handleOk(): void {
    if (this.configForm.valid) {
      
      const configValues = this.configForm.value;
      this.saveConfiguration(configValues);
      
    } else {
      Object.values(this.configForm.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity();
        }
      });
      this.notification.error('Configuration Error', 'Please fill in all fields with valid values.');
    }
  }

  handleCancel(): void {
    this.isVisible = false;
  }

  toggleSystem(action: 'start' | 'stop'): void {
    
    if (action === 'start') {
      this.startSystem();
      
    } else {
      this.stopSystem();
      
    }
  }
  stopSystem(): void {
    this.appService.stopSystem().subscribe({
      next: (res: { message: string }) => {
        console.log(res.message, 'stop');
  
        this.logs.push({ timestamp: new Date().toLocaleString(), message: 'System stopped.' });
        this.notification.warning('System Stopped', res.message);
        this.isSystemStarted = false; 
      },
      error: (err) => {
        this.notification.warning('Failed to stop system:', err);
        console.error('Failed to stop system:', err);
      },
    });
  }
  startSystem(): void {
    this.appService.startSystem().subscribe({
      next: (res: { message: string }) => {
        console.log(res.message, 'start');
  
        this.logs.push({ timestamp: new Date().toLocaleString(), message: 'System started.' });
        this.notification.success('System Started', res.message);
        this.isSystemStarted = true; 
      },
      error: (err) => {
        console.error('Failed to start system:', err);
        this.notification.error('System Start Failed', err.error?.error || 'An unexpected error occurred.');
      },
    });
  }
  
  saveConfiguration(formValues: any): void {
    this.appService.saveSystemConfiguration(formValues).subscribe({
      next: (response) => {
        this.currentConfig = response; 
        this.logs.push({
          timestamp: new Date().toLocaleString(),
          message: `System configured with values: ${JSON.stringify(this.currentConfig)}`
        });
        this.notification.success('System Configured', 'The system has been configured successfully.');
        this.isVisible = false;
        this.isConfigured = true;
      },
      error: (err) => {
        console.error('Failed to save configuration:', err);
      },
    });
  }
    
  
}
