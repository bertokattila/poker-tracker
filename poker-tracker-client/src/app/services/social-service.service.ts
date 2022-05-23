import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AddFriendDto } from '../model/addFriendDto';
import { Session } from '../model/session';
import { SessionWithOwnerDTO } from '../model/sessionWithOwnerDTO';
import { serviceUrls } from './serviceUrls';

@Injectable({
  providedIn: 'root',
})
export class SocialService {
  constructor(private http: HttpClient) {}
  addFriend(email: string) {
    const dto = new AddFriendDto(email);

    return this.http.post<AddFriendDto>(
      serviceUrls.socialServiceUrl + '/social/addfriend',
      dto
    );
  }
  getFriends() {
    return this.http.get(serviceUrls.socialServiceUrl + '/social/friends');
  }
  getFriendRequests() {
    return this.http.get(
      serviceUrls.socialServiceUrl + '/social/friendrequests'
    );
  }
  getAddedFriends() {
    return this.http.get(serviceUrls.socialServiceUrl + '/social/addedfriends');
  }
  acceptFriendRequest(id: number) {
    return this.http.post(
      serviceUrls.socialServiceUrl + '/social/acceptfriend/' + id,
      null
    );
  }
  getNotifications(
    limit: number,
    offset: number
  ): Observable<SessionWithOwnerDTO[]> {
    return this.http.get<SessionWithOwnerDTO[]>(
      serviceUrls.socialServiceUrl +
        '/social/notifications?limit=' +
        limit +
        '&offset=' +
        offset
    );
  }
}
