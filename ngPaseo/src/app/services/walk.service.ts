import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Walk } from '../models/walk';
import { AuthService } from './auth.service';
import { catchError, throwError, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
@Injectable()
export class WalkService {
  private url = environment.baseUrl;

  constructor(private http: HttpClient, private auth: AuthService) {}

  getHttpOptions() {
    let options = {
      headers: {
        Authorization: 'Basic ' + this.auth.getCredentials(),
        'X-Requested-With': 'XMLHttpRequest',
      },
    };
    return options;
  }

  index(): Observable<Walk[]> {
    //return this.http.get<Todo[]>(this.url + '?sorted=true').pipe(
    return this.http.get<Walk[]>(this.url + 'api/walks ', this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error('WalkService.index(): error retrieving walk: ' + err)
        );
      })
    );
  }

  create(walk: Walk): Observable<Walk> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
      },
    };

    return this.http
      .post<Walk>(this.url + 'api/walks', walk, this.getHttpOptions())
      .pipe(
        catchError((err: any) => {
          console.error(err);
          return throwError(
            () => new Error('WalkService.create(): error creating Walk: ' + err)
          );
        })
      );
  }

  update(walk: Walk): Observable<Walk> {
    // Create GET request to authenticate credentials
    return this.http
      .put<Walk>(this.url + 'api/walks/' + walk.id, walk, this.getHttpOptions())
      .pipe(
        catchError((err: any) => {
          console.log(err);
          return throwError(
            () => new Error('WalkService.update(): error updating walk.')
          );
        })
      );
  }

  search(walk : Walk): Observable<Walk[]> {

    return this.http.post<Walk[]>(this.url + 'api/walks/search ', walk,  this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error('WalkService.search(): error retrieving walks: ' + err)
        );
      })
    );
  }


  destroy(id: number): Observable<void>   {
    return this.http.delete<void>(this.url + 'api/walks/' + id, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error('WalkService.destroy(): error deleting walk: ' + err)
        );
      })
    );
    }

getById(id: number): Observable<Walk> {

  return this.http.get<Walk>(this.url + 'api/walks/' + id, this.getHttpOptions()).pipe(
    catchError((err: any) => {
      console.log(err);
      return throwError(
        () => new Error('WalkService.getById(): error retrieving walk: ' + err)
      );
    })
  );

  }

getUserWalks(userId: number): Observable<Walk[]> {
  return this.http.get<Walk[]>(this.url + 'api/walks/user/' + userId, this.getHttpOptions()).pipe(
    catchError((err: any) => {
      console.log(err);
      return throwError(
        () => new Error('WalkService.getById(): error retrieving walk for user: ' + err)
      );
    })
  );
}

getJoinedWalks(userId:number):Observable<Walk[]> {
  return this.http.get<Walk[]>(this.url + 'api/walks/joined/' + userId, this.getHttpOptions()).pipe(
    catchError((err: any) => {
      console.log(err);
      return throwError(
        () => new Error('WalkService.getJoinedWalks(): error retrieving joined walks for user: ' + err)
      );
    })
  );

}

  joinWalk(walkId:number):Observable<void> {
    return this.http.post<void>(this.url + 'api/walks/join/' + walkId,"", this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error('WalkService.joinWalk(): error joining walk: ' + err)
        );
      })
    );
  }

  leaveWalk(walkId:number):Observable<void> {
    return this.http.delete<void>(this.url + 'api/walks/leave/' + walkId, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error('WalkService.leaveWalk(): error leaving walk: ' + err)
        );
      })
    );
  }
}
