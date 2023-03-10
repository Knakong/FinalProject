import { WalkPageComponent } from './components/walk-page/walk-page.component';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NavbarComponent } from './components/navbar/navbar.component';
import { SettingsComponent } from './components/settings/settings.component';
import { ProfileComponent } from './components/profile/profile.component';
import { MessagesComponent } from './components/messages/messages.component';
import { FriendsComponent } from './components/friends/friends.component';
import { AdsComponent } from './components/ads/ads.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { AboutComponent } from './components/about/about.component';
import { RegisterComponent } from './components/register/register.component';
import { WalkComponent } from './components/walk/walk.component';
import { CreateMessageComponent } from './components/create-message/create-message.component';
import { FooterComponent } from './components/footer/footer.component';
import { WalkCategoryPipe } from './pipes/walk-category.pipe';
import { WalkTypePipe } from './pipes/walk-type.pipe';
import { WalkLocationPipe } from './pipes/walk-location.pipe';
import { WalkGenderPipe } from './pipes/walk-gender.pipe';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NavbarComponent,
    SettingsComponent,
    ProfileComponent,
    MessagesComponent,
    FriendsComponent,
    AdsComponent,
    NotFoundComponent,
    AboutComponent,
    RegisterComponent,
    WalkPageComponent,
    WalkComponent,
    CreateMessageComponent,
    FooterComponent,
    WalkCategoryPipe,
    WalkTypePipe,
    WalkLocationPipe,
    WalkGenderPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
