import { Component } from '@angular/core';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzDrawerModule } from 'ng-zorro-antd/drawer';
import {LoginComponent} from '../../auth/login/login.component'
@Component({
  selector: 'app-drawer',
  standalone: true,
  imports: [NzButtonModule, NzDrawerModule,LoginComponent],
  templateUrl: './drawer.component.html',
  styleUrl: './drawer.component.css'
})
export class DrawerComponent {
  visible = false;

  open(): void {
    this.visible = true;
  }

  close(): void {
    this.visible = false;
  }
}
