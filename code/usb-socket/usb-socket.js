import { SerialPort } from 'serialport';
import { WebSocketServer } from 'ws';

const serial = new SerialPort({ path: '/dev/ttyUSB0', baudRate: 115200 }); // Adjust path

const wss = new WebSocketServer({ port: 8000 });
console.log('WebSocket server running on ws://localhost:8000');

wss.on('connection', (ws) => {
  console.log('Frontend connected via WebSocket');

  // String bereits eingelesen
  let cardNumber = ""
  const regex = /\{\s*"readerNumber"\s*:\s*"(\d+)"\s*,\s*"cardNumber"\s*:\s*"([\d\-]+)"\s*\}/;

  serial.on('data', (data) => {
    const message = data.toString()/*.trim()*/;
    console.log('Received from ESP32:', message);

    // const sanitized = message.trimEnd();

    cardNumber += message;
    const isValid = regex.test(cardNumber);

      if ( cardNumber.at(cardNumber.length-1) == '#') {
        console.log("'#' detected");

        cardNumber = cardNumber.slice(0, -1)
        if (isValid) {
          console.log('valid -> sending ', cardNumber + "'");
          ws.send(cardNumber);
          cardNumber = ""
        }
        else {
            console.log('discarding card number:', cardNumber);
            cardNumber = ""
        }
      }
      // else {
      //   console.log('discarding card number:', cardNumber);
      //   cardNumber = ""
      // }

  });

  ws.on('close', () => {
    console.log('Frontend disconnected');
  });
});




