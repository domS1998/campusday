import { Injectable } from '@angular/core';



@Injectable({
  providedIn: 'root'
})
export class UsbService {
  private socket

  constructor() {
    this.socket = new WebSocket('ws://localhost:3001');

    this.socket.onopen = () => console.log('WebSocket connected');
    this.socket.onclose = () => console.log('WebSocket disconnected');
  }

  onMessage(callback: (msg: string) => void) {
    this.socket.onmessage = (event) => callback(event.data);
    console.log("usb-service: message received: " + JSON.stringify(callback));
  }
}






