import { Component } from '@angular/core';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzButtonModule, NzButtonSize } from 'ng-zorro-antd/button';

@Component({
  selector: 'app-display-events',
  standalone: true,
  imports: [NzCardModule, NzGridModule,NzButtonModule,],
  templateUrl: './display-events.component.html',
  styleUrl: './display-events.component.css'
})
export class DisplayEventsComponent {

}
