import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { LoginComponent } from './components/login/login.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { RegisterComponent } from './components/register/register.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { DialogComponent } from './components/dialog/dialog.component';
import { MatDialogModule } from '@angular/material/dialog';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { AuthInterceptor } from './utils/AuthInterceptor';
import { HeaderComponent } from './components/header/header.component';
import { AddSessionComponent } from './components/add-session/add-session.component';
import { HistoryComponent } from './components/history/history.component';
import { StatisticsComponent } from './components/statistics/statistics.component';
import { SocialComponent } from './components/social/social.component';
import { MainPageComponent } from './components/main-page/main-page.component';
import { MatTabsModule } from '@angular/material/tabs';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { OnlyNumber } from './utils/OnlyNumber';
import { MatExpansionModule } from '@angular/material/expansion';
import { DatePipe } from '@angular/common';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { AddFriendComponent } from './components/social/add-friend/add-friend.component';
import { NotificationsComponent } from './components/social/notifications/notifications.component';
import { AddFriendDialogComponent } from './components/social/add-friend-dialog/add-friend-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DialogComponent,
    WelcomeComponent,
    HeaderComponent,
    AddSessionComponent,
    HistoryComponent,
    StatisticsComponent,
    SocialComponent,
    MainPageComponent,
    OnlyNumber,
    AddFriendComponent,
    NotificationsComponent,
    AddFriendDialogComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    FlexLayoutModule,
    MatIconModule,
    MatSelectModule,
    FormsModule,
    HttpClientModule,
    MatDialogModule,
    MatTabsModule,
    MatButtonToggleModule,
    MatExpansionModule,
    NgxChartsModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    DatePipe,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
