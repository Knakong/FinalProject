import { NavbarComponent } from './../navbar/navbar.component';
import { AuthService } from 'src/app/services/auth.service';
import { WalkService } from 'src/app/services/walk.service';
import { AddressService } from './../../services/address.service';
import { GenderService } from './../../services/gender.service';

import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Walk } from 'src/app/models/walk';
import { UserService } from 'src/app/services/user.service';
import { Address } from 'src/app/models/address';
import { Gender } from 'src/app/models/gender';
import { User } from 'src/app/models/user';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
[x: string]: any;
  walks: Walk[] = [];
  joinedWalks: Walk[] = [];
  loggedInUserWalks: Walk[] = [];

  user: User = new User();
  followedUsers: User[] = [];
  loggedInUser: User = new User();
  currentUserFollowedUsers: User[] = [];
  followingUsers: User[] = [];
  address: Address = new Address();
  userGender = new Gender();
  genders: Gender[] = [];

  displayPhotos = true;
  displayCreatedWalks = false;
  displayJoinedWalks = false;
  displayGroups = false;
  displayInterests = false;
  followed: boolean = false;

  closeResult = '';
  viewedWalk: Walk = new Walk();
  adminUser : User = new User();
  displayDeactivateProfileButton : boolean = false;
  currentUserId : number = 0;

  viewedWalkJoined: boolean = false;
  viewedWalkCreated: boolean = false;

  imageUrl: string = this.user.profileImageUrl;
  constructor(
    private auth: AuthService,
    private userService: UserService,
    private router: Router,
    private addressService: AddressService,
    private gender: GenderService,
    private walkService: WalkService,
    private route: ActivatedRoute,
    private modalService: NgbModal
  ) {}

  ngOnInit() {
    // this.checkCurrentUserForAdminStatus();
    this.getUser();
  }
  getUser() {
    //If no ID present
    this.auth.getLoggedInUser().subscribe({
      next: (data) => {
        this.loggedInUser = data;
        let id: string | null = this.route.snapshot.paramMap.get('id');

        if (id && !isNaN(+id)) {
          //If ID present
          this.userService.findUser(+id).subscribe({
            next: (data) => {
              console.log(data);
              this.user = data;
              this.getFollowed();
              this.getFollowers();
              this.getUserWalks();
              this.getUserJoinedWalks();
              this.getLoggedInUserJoinedWalks();
              this.getCurrentUserFollowed();
              this.viewedWalk = new Walk();
              if (!this.user.address) {
                this.user.address = new Address();
              } else {
                this.address = data.address!;
              }
              if (!this.user.gender) {
                this.user.gender = new Gender();
              } else {
                this.userGender = data.gender!;
              }

              this.viewedWalkCreated = false;
              this.viewedWalkJoined = false;
              this.imageUrl = Object.assign({}, this.user.profileImageUrl);
              console.log('Logged in');
            },
            error: (fail) => {
              this.router.navigateByUrl('/notFound');
              console.error(fail);
            },
          });
        } else {
          console.log(data);
          this.user = data;
          this.getFollowed();
          this.getFollowers();
          this.getUserWalks();
          this.getUserJoinedWalks();
          this.getLoggedInUserJoinedWalks();
          this.getCurrentUserFollowed();
          if (!this.user.address) {
            this.user.address = new Address();
          } else {
            this.address = data.address!;
          }
          if (!this.user.gender) {
            this.user.gender = new Gender();
          } else {
            this.userGender = data.gender!;
          }

          this.viewedWalkCreated = false;
          this.viewedWalkJoined = false;
          this.imageUrl = Object.assign({}, this.user.profileImageUrl);
          console.log('Logged in');
        }
      },
      error: (fail) => {
        this.router.navigateByUrl('/notFound');
        console.error(fail);
      },
    });
  }

  //Mapped user created walks
  getUserWalks() {
    this.walkService.getUserWalks(this.user.id).subscribe({
      next: (data) => {
        this.walks = data;
        console.log(this.walks);
      },
      error: (fail) => {
        this.router.navigateByUrl('/notFound');
        console.error(fail);
      },
    });
  }

  getUserJoinedWalks() {
    let id: number = 0;
    if (this.route.snapshot.paramMap.get('id')) {
      id = +this.route.snapshot.paramMap.get('id')!;
    } else {
      id = +localStorage.getItem('currentUserId')!;
    }
    this.walkService.getJoinedWalks(id).subscribe({
      next: (data) => {
        console.log(data);
        this.joinedWalks = data;
      },
      error: (err) => {
        console.error('Failed to get joined walks');
        console.error(err);
      },
    });
  }

  getLoggedInUserJoinedWalks() {
    if (this.user.id === +localStorage.getItem('currentUserId')!) {
      this.loggedInUserWalks = this.joinedWalks;
    } else {
      this.walkService
        .getJoinedWalks(+localStorage.getItem('currentUserId')!)
        .subscribe({
          next: (data) => {
            this.loggedInUserWalks = data;
          },
          error: (err) => {
            console.error('Failed to get joined walks');
            console.error(err);
          },
        });
    }
  }

  getFollowers() {
    this.userService.findFollowers(+this.user.id).subscribe({
      next: (data) => {
        this.followingUsers = data;
      },
      error: (err) => {
        console.error('Failed to get following users');
        console.error(err);
      },
    });
  }

  getFollowed() {
    this.userService.findFriends(+this.user.id).subscribe({
      next: (data) => {
        this.followedUsers = data;
      },
      error: (err) => {
        console.error('Failed to get followed users');
        console.error(err);
      },
    });
  }

  getCurrentUserFollowed() {
    let id: string = localStorage.getItem('currentUserId')!;
    this.userService.findFriends(+id).subscribe({
      next: (data) => {
        let id = localStorage.getItem('currentUserId');
        this.currentUserFollowedUsers = data;
        for (let u of this.currentUserFollowedUsers) {
          if (u.id === +this.user.id!) {
            this.followed = true;
            break;
          }
        }
      },
      error: (err) => {
        console.error('Failed to get followed users');
        console.error(err);
      },
    });
  }

  clickPhotos() {
    this.displayPhotos = true;
    this.displayCreatedWalks = false;
    this.displayJoinedWalks = false;
  }
  clickCreatedWalks() {
    this.displayPhotos = false;
    this.displayCreatedWalks = true;
    this.displayJoinedWalks = false;
  }
  clickJoinedWalks() {
    this.displayPhotos = false;
    this.displayCreatedWalks = false;
    this.displayJoinedWalks = true;
  }

  followUser() {
    let id: string = this.route.snapshot.paramMap.get('id')!;
    if (id === null) {
      id = localStorage.getItem('currentUserId')!;
    }
    this.userService.followUser(+id).subscribe({
      next: () => {
        this.getUser();
      },
      error: (err) => {
        console.error(err);
      },
    });
  }
    unfollowUser() {
      let id: string = this.route.snapshot.paramMap.get('id')!;
    if (id === null) {
      id = localStorage.getItem('currentUserId')!;
    }
    this.userService.unfollowUser(+id).subscribe({
      next: (data) => {
        this.followed = false;
        this.getUser();
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  //Open reply modal
  open(content: any, walk: Walk) {
    this.modalService
      .open(content, { ariaLabelledBy: 'modal-basic-title' })
      .result.then(
        (result) => {
          this.closeResult = `Closed with: ${result}`;
        },
        (reason) => {
          this.closeResult = `Dismissed with ${reason}`;
        }
      );
    this.viewedWalk = walk;
    for (let walkId of this.joinedWalks) {
      if (walkId.id === this.viewedWalk.id) {
        this.viewedWalkJoined = true;
        break;
      }
    }
    for (let walk of this.walks) {
      if (walk.id === this.viewedWalk.id) {
        this.viewedWalkCreated = true;
        break;
      }
    }
  }

  joinWalk() {
    this.walkService.joinWalk(this.viewedWalk.id).subscribe({
      next: (data) => {
        this.getUser();
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  leaveWalk() {
    this.walkService.leaveWalk(this.viewedWalk.id).subscribe({
      next: (data) => {
        this.getUser();
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  deleteWalk() {
    this.walkService.destroy(this.viewedWalk.id).subscribe({
      next: () => {
        this.getUser();
        this.modalService.dismissAll();
      },
      error: (err) => {
        console.error(err);
      }
    })
  }

  editWalk() {
    this.modalService.dismissAll();
    this.router.navigateByUrl('/walkPage/' + this.viewedWalk.id);
  }

  close() {
    this.modalService.dismissAll();
    this.viewedWalk = new Walk();
    this.viewedWalkCreated = false;
    this.viewedWalkJoined = false;
  }
}
