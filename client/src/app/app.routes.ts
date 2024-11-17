import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { DisplayEventsComponent } from './components/display-events/display-events.component';
import { DrawerComponent } from './components/drawer/drawer.component';
import { SignUpComponent } from './auth/sign-up/sign-up.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    {path:'sign-up',component:SignUpComponent},
    {path:'display-events',component:DisplayEventsComponent},
    {path:'drawer',component:DrawerComponent},
    
    { path: '**', redirectTo: 'display-events' }
];
