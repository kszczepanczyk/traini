import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthModule } from './features/auth/auth.module';
import { HomeModule } from './features/home/home.module';
import { ProfileModule } from './features/profile/profile.module';
import { UserModule } from './features/users/users.module';
import { TrainingModule } from './features/training/training.module';
import {
  TranslateLoader,
  TranslateModule,
  TranslateService,
} from '@ngx-translate/core';
import { MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core';
import { HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { AuthService } from './features/auth/auth.service';
import { RequestInterceptor } from './shared/helpers/http.interceptor';
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

export function initializeApp(yourService: AuthService) {
  return () => yourService.initialize();
}

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
    MatNativeDateModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient],
      },
    }),
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: RequestInterceptor,
      multi: true,
    },
    { provide: MAT_DATE_LOCALE, useValue: 'pl' },
    AuthService,
    {
      provide: APP_INITIALIZER,
      useFactory: initializeApp,
      multi: true,
      deps: [AuthService],
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
  constructor(private translate: TranslateService) {
    translate.addLangs(['en', 'pl']);
    translate.setDefaultLang('en');
    const browserLang = translate.getBrowserLang() ?? 'en';
    translate.use(browserLang.match(/en|pl/) ? browserLang : 'en');
  }
}
