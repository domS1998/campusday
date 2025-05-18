import { Injectable } from '@angular/core';
import {GlobalConfigService} from '../global-config/global-config.service';



@Injectable({
  providedIn: 'root'
})
export class UsbService {
  private socket

  constructor(
    private globalConfig: GlobalConfigService
  ) {
    console.log('ws://'+this.globalConfig.WEBSOCKET_HOST+':'+this.globalConfig.WEBSOCKET_PORT)
    this.socket = new WebSocket('ws://'+this.globalConfig.WEBSOCKET_HOST+':'+this.globalConfig.WEBSOCKET_PORT);

    this.socket.onopen = () => console.log('WebSocket connected');
    this.socket.onclose = () => console.log('WebSocket disconnected');
  }

  onMessage(callback: (msg: string) => void) {
    this.socket.onmessage = (event) => callback(event.data);
    console.log("usb-service: message received: " + JSON.stringify(callback));
  }
}






