import { HttpClient } from '@angular/common/http';
import { WalkCategory } from './../models/walk-category';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable, catchError, throwError } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class WalkCategoryService {

  private url = environment.baseUrl;

  constructor(private http: HttpClient, private auth: AuthService) { }

  getHttpOptions() {
    let options = {
      headers: {
        Authorization: 'Basic ' + this.auth.getCredentials(),
        'X-Requested-With': 'XMLHttpRequest',
      },
    };
    return options;
  }

  index(): Observable<WalkCategory[]> {
    console.log('In walklocationService index');

    // Create GET request to authenticate credentials
    return this.http.get<WalkCategory[]>(this.url + 'api/walkCategory', this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error('WalkLocationService.index(): error updating address.')
        );
      })
    );
  }







}
