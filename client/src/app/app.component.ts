import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import {DrawerComponent} from '../app/components/drawer/drawer.component'
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzDrawerModule } from 'ng-zorro-antd/drawer';
import {LoginComponent} from '../app/auth/login/login.component';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import {NavbarComponent} from '../app/components/navbar/navbar.component'

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,NzIconModule, NzMenuModule,CommonModule,DrawerComponent,NzButtonModule, NzDrawerModule,LoginComponent,NzLayoutModule ,NavbarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'client';
  isNavCollapsed = true; 
  visible = false;

  toggleNavbar() { 
    console.log('clic',this.isNavCollapsed);
    
    this.isNavCollapsed = !this.isNavCollapsed; 
  }
  
  open(): void {
    this.visible = true;
  }

  close(): void {
    this.visible = false;
  }
}
