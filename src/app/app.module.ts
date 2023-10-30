import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthModule } from './features/auth/auth.module';
import { HomeModule } from './features/home/home.module';
import { ProfileModule } from './features/profile/profile.module';
import { UserModule } from './features/users/users.module';
import { TrainingModule } from './features/training/training.module';
@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AuthModule,
    HomeModule,
    ProfileModule,
    UserModule,
    TrainingModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
