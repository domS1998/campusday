import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
  HttpEvent
} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NoCacheInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (req.method === 'GET') {
      const cloned = req.clone({
        setParams: { t: Date.now().toString() },
        setHeaders: {
          'Cache-Control': 'no-cache',
          'Pragma': 'no-cache'
        }
      });
      return next.handle(cloned);
    }
    return next.handle(req);
  }
}
