import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-system-control',
  standalone: true,
  imports: [],
  templateUrl: './system-control.component.html',
  styleUrl: './system-control.component.css'
})
export class SystemControlComponent {

  systemStatus: string = 'Loading...';

  constructor(private http: HttpClient) {
    this.getSystemStatus();
  }

  getSystemStatus() {
    this.http.get('/api/system/control').subscribe((res: any) => {
      this.systemStatus = res.status ? 'Running' : 'Stopped';
    });
  }

  toggleSystem(action: 'start' | 'stop') {
    this.http.post('/api/system/control', { action }).subscribe(() => {
      this.systemStatus = action === 'start' ? 'Running' : 'Stopped';
    });
  }
}
