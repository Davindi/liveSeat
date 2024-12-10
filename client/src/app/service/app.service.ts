import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../src/environments/environments';

@Injectable({
  providedIn: 'root'
})
export class AppService {
    private BASE = environment.apiHost + environment.apiBasePath;

  constructor(
    private http: HttpClient,
  ) { }
  signUp(data:any): Observable<any> {

    return this.http.post(this.BASE + '/auth/signup', data);
  }
  signIn(data:any): Observable<any> {

    return this.http.post(this.BASE + '/auth/signin', data);
  }
  // Get current system configuration
  getSystemConfiguration(): Observable<any> {
    return this.http.get(`${this.BASE}/configuration`);
  }

  // Create or update system configuration
  saveSystemConfiguration(data: any): Observable<any> {
    return this.http.post(`${this.BASE}/configuration`, data);
  }

  // Start the system
  startSystem(): Observable<any> {
    return this.http.post(`${this.BASE}/configuration/start`, {});
  }

  // Stop the system
  stopSystem(): Observable<any> {
    return this.http.post(`${this.BASE}/configuration/stop`, {});
  }

  getSystemStatus(): Observable<boolean> {
    return this.http.get<boolean>(this.BASE + '/configuration/status');
  }
  

  getVendorEvents(vendorId: any): Observable<any[]> {
    return this.http.get<any[]>(`${this.BASE}/vendor/${vendorId}/events`);
  }
  

  addEvent(vendorId: string, eventData: any): Observable<any> {
    return this.http.post<any>(`${this.BASE}/vendor/${vendorId}/add-event`, eventData);
  }

  // Update event status (Activate/Deactivate)
updateEventStatus(eventId: number, status: string): Observable<any> {
  return this.http.put<any>(`${this.BASE}/vendor/events/${eventId}/status?status=${status}`, {});
}


  // Add more tickets to the event
  addTickets(eventId: string, tickets: number): Observable<any> {
    return this.http.put<any>(`${this.BASE}/vendor/events/${eventId}/addTickets`, { tickets });
  }
//------------------------------customer--------------
  getAllEvents(): Observable<any[]> {
    return this.http.get<any>(this.BASE + '/customers/events');
  }
  
  purchaseTickets(customerId: number, eventId: number, ticketCount: number) {
    return this.http.post(`${this.BASE}/customers/purchase/${customerId}/${eventId}/${ticketCount}`, {});
  }

  getPurchasedTickets(customerId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.BASE}/customers/purchased-tickets/${customerId}`);
  }
  
}