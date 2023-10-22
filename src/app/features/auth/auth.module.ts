import { ModuleWithProviders, NgModule } from "@angular/core";
import { AuthRoutingModule } from "./auth-routing.module";
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';

@NgModule({
    declarations:[
  
    LoginComponent,
       RegisterComponent,
       ForgotPasswordComponent
  ],
    imports: [
        AuthRoutingModule,
    ],
    providers: []
})

export class AuthModule {
    public static forRoot(): ModuleWithProviders<AuthModule> {
        return {
            ngModule: AuthModule,
            providers: []
        };
    }
}