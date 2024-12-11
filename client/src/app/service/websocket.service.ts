import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private socket: WebSocket | null = null;
  private messages: Subject<string> = new Subject<string>();

  connect(url: string): void {
    try {
      this.socket = new WebSocket(url);

      this.socket.onopen = () => {
        console.log('WebSocket connected');
      };

      this.socket.onmessage = (event) => {
        console.log('Message from server:', event.data);
        this.messages.next(event.data); // Emit received messages
      };

      this.socket.onerror = (error) => {
        console.error('WebSocket error:', error);
      };

      this.socket.onclose = (event) => {
        console.warn('WebSocket closed:', event);
        this.reconnect(url); // Attempt reconnection
      };
    } catch (error) {
      console.error('Failed to connect to WebSocket:', error);
    }
  }

  reconnect(url: string, retries: number = 3, delay: number = 2000): void {
    let attempts = 0;
    const tryReconnect = () => {
      if (attempts < retries) {
        attempts++;
        console.log(`Reconnect attempt #${attempts}`);
        setTimeout(() => {
          this.connect(url);
        }, delay);
      } else {
        console.error('Max reconnect attempts reached');
      }
    };
    tryReconnect();
  }

  onMessage(): Observable<string> {
    return this.messages.asObservable(); // Return observable for messages
  }

  close(): void {
    if (this.socket) {
      this.socket.close();
      this.socket = null;
    }
  }
}
