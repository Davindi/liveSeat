import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { DisplayEventsComponent } from './components/display-events/display-events.component';
import { DrawerComponent } from './components/drawer/drawer.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    {path:'display-events',component:DisplayEventsComponent},
    {path:'drawer',component:DrawerComponent},
    { path: '**', redirectTo: 'display-events' }
];
