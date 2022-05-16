import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AddFriendDto } from '../model/addFriendDto';
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
}
