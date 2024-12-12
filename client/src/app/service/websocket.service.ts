import { Injectable, OnDestroy } from '@angular/core';
import { CompatClient, Stomp } from '@stomp/stompjs';
import { StompSubscription } from '@stomp/stompjs/src/stomp-subscription';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService implements OnDestroy {
  private connection: CompatClient | undefined = undefined;
  private subscription: StompSubscription | undefined;
  private activityLogs = new BehaviorSubject<string[]>([]); // Stores logs

  constructor() {
    this.connection = Stomp.client('ws://localhost:8080/ws'); // Your WebSocket endpoint
    this.connectToWebSocket();
  }

  // Connect to the WebSocket server
  private connectToWebSocket(): void {
    this.connection?.connect({}, () => {
      console.log('Connected to WebSocket');
      
      // Listen for messages on the /topic/activities topic
      this.subscription = this.connection?.subscribe('/topic/activities', message => {
        if (message.body) {
          this.addLog(message.body); // Add the log received to the logs
        }
      });
    });
  }

  // Send activity logs (if needed)
  public sendActivityLog(log: string): void {
    if (this.connection && this.connection.connected) {
      this.connection.send('/app/activity', {}, log);
    }
  }

  // Adds new log to the BehaviorSubject (which is observable)
  private addLog(log: string): void {
    this.activityLogs.next([...this.activityLogs.value, log]); // Append to the list of logs
  }

  // Get logs as an observable
  public getLogs() {
    return this.activityLogs.asObservable();
  }

  // Cleanup when the service is destroyed
  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
