import { WalkPageComponent } from './components/walk-page/walk-page.component';
import { AboutComponent } from './components/about/about.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { AdsComponent } from './components/ads/ads.component';
import { FriendsComponent } from './components/friends/friends.component';
import { MessagesComponent } from './components/messages/messages.component';
import { ProfileComponent } from './components/profile/profile.component';
import { SettingsComponent } from './components/settings/settings.component';
import { HomeComponent } from './components/home/home.component';
import { NgModule, Component } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'home' },
  {path: "home", component: HomeComponent},
  {path: "settings/:id", component: SettingsComponent},
  {path: "profile", component: ProfileComponent},
  {path: "profile/:id", component: ProfileComponent},
  {path: "messages", component: MessagesComponent},
  {path: "messages/**", component: MessagesComponent},
  {path: "friends", component: FriendsComponent},
  {path: "ads", component: AdsComponent},
  {path: "about", component: AboutComponent},
  { path: "walkPage/:id", component: WalkPageComponent},
  { path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
