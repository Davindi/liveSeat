import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable()
export class AdminDashboardService {
  private logs: BehaviorSubject<string[]> = new BehaviorSubject<string[]>([]);

  addLog(message: string): void {
    const currentLogs = this.logs.value;
    this.logs.next([message, ...currentLogs]);
  }

  getLogs() {
    return this.logs.asObservable();
  }
}
