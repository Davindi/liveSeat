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

}