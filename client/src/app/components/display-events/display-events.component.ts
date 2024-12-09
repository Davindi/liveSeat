import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzButtonModule, NzButtonSize } from 'ng-zorro-antd/button';
import { NzDrawerModule } from 'ng-zorro-antd/drawer';
import { NzModalService } from 'ng-zorro-antd/modal';
import { NzModalModule } from 'ng-zorro-antd/modal';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { AppService } from '../../service/app.service';
import { NzTableModule } from 'ng-zorro-antd/table';

@Component({
  selector: 'app-display-events',
  standalone: true,
  imports: [NzCardModule, NzGridModule,NzButtonModule, NzDrawerModule,CommonModule, NzModalModule,ReactiveFormsModule,NzTableModule],
  templateUrl: './display-events.component.html',
  styleUrl: './display-events.component.css'
})
export class DisplayEventsComponent {
  isDrawerVisible = false;
  selectedCard: any = null;
  events: any[] = [];
  isAddEventModalVisible = false;
  isSystemStarted = false;
  addEventForm: FormGroup;

  constructor(private router: Router,private modal: NzModalService,private fb: FormBuilder, private notification: NzNotificationService,private appService: AppService,) {
    this.addEventForm = this.fb.group({
      eventName: ['', Validators.required],
      totalTickets: ['', [Validators.required, Validators.min(1)]],
      ticketPrice: ['', [Validators.required, Validators.min(0.1)]],

    });
  }
  
  cards = [
    {
      title: 'Europe Street Beat',
      description: 'www.instagram.com',
      img: 'https://os.alipayobjects.com/rmsportal/QBnOOoLaAfKPirc.png'
    },
    {
      title: 'Asia Street Beat',
      description: 'www.twitter.com',
      img: 'https://os.alipayobjects.com/rmsportal/QBnOOoLaAfKPirc.png'
    }
    
  ];

  isUserLoggedIn(): boolean {
    const token = localStorage.getItem('authToken'); 

    return !!token; 
  }
  
  isVendorOrCustomer(): any {
    const token = localStorage.getItem('authToken');
    if (token) {
      const userDetails: {  sub: string;
        email: string;
        role: string; } = jwtDecode(token);
      return userDetails;
    }
    return ;
  }
  ngOnInit(): void {
    this.isVendorOrCustomer();
    this.appService.getSystemStatus().subscribe({
      next: (status) => (this.isSystemStarted = status,this.fetchVendorEvents()),
      error: (err) => console.error('Error fetching system status', err)
    });
    this.fetchVendorEvents();
    
  }
  getVendorId(): any | null {
    const token = localStorage.getItem('authToken');
    if (token) {
      const userDetails: { sub: string; email: string; role: string; userId?: any } = jwtDecode(token);
      return userDetails.userId || null;
    }
    return null;
  }
  

  fetchVendorEvents() {
    
    const vendorId = this.getVendorId(); 
    if (vendorId) {
      this.appService.getVendorEvents(vendorId).subscribe({
        next: (response) => {
          this.events = response;
        },
        error: (err) => console.error('Error fetching vendor events', err),
      });
    } else {
      console.error('Vendor ID is not available');
    }
    
   
  }

  // Update event status (Activate/Deactivate)
  updateEventStatus(eventId: number, status: string) {
    this.appService.updateEventStatus(eventId, status).subscribe({
      next: (response) => {
        this.fetchVendorEvents(); 
        console.log(`Event ${status}d successfully`, response);
        
      },
      error: (err) => {
        console.error('Error updating event status', err);
      }
    });
  }

  // Add more tickets to the event
  addMoreTickets(eventId: string) {
    const additionalTickets = prompt('Enter the number of tickets to add:'); // Prompt to get the number of tickets to add
    if (additionalTickets) {
      const ticketsToAdd = parseInt(additionalTickets, 10);
      if (isNaN(ticketsToAdd) || ticketsToAdd <= 0) {
        alert('Please enter a valid number of tickets');
        return;
      }
      this.appService.addTickets(eventId, ticketsToAdd).subscribe({
        next: (response) => {
          console.log('Tickets added successfully', response);
          this.fetchVendorEvents(); // Refresh the event list
        },
        error: (err) => {
          console.error('Error adding tickets', err);
        }
      });
    }
  }

  showAddEventModal() {
    this.isAddEventModalVisible = true;
  }

  handleAddEventOk() {
  if (!this.isSystemStarted) {
    alert('System is not started by the admin. Please contact admin.');
    return;
  }

  const vendorId = this.getVendorId(); // Assuming you have a way to retrieve the vendorId
  if (vendorId) {
    const eventData = {
      eventName: this.addEventForm.value.eventName,
      status: "string", // You can replace with the actual status or a form field value
      ticketPrice: this.addEventForm.value.ticketPrice,
      totalTickets: this.addEventForm.value.totalTickets,
      ticketsSold: 0, // Assuming tickets sold is initially 0
    };

    this.appService.addEvent(vendorId, eventData).subscribe({
      next: (response) => {
        console.log('Event added successfully', response);
        this.fetchVendorEvents(); // Refresh the event list
        this.isAddEventModalVisible = false; // Close the modal
      },
      error: (err) => {
        console.error('Error adding event', err);
      },
    });
  } else {
    console.error('Vendor ID is not available');
  }
}


  handleAddEventCancel() {
    this.isAddEventModalVisible = false;
  } 
  open(): void {
    if (this.isUserLoggedIn()) {
      this.modal.create({
        nzTitle: 'Buy Tickets',
        nzContent: 'Please confirm your ticket purchase.',
        nzFooter: [
          {
            label: 'Cancel',
            onClick: () => console.log('Purchase cancelled'),
          },
          {
            label: 'Confirm',
            type: 'primary',
            onClick: () => console.log('Ticket purchased'),
          },
        ],
      });
    } else {
      this.router.navigate(['/login']); // Redirect to the login page
    }
  }
  // open(card: any): void {
  //   console.log('Open drawer with card:', card);
    
  //   this.selectedCard = card; // Pass selected card details
  //   this.isDrawerVisible = true;
  //   console.log("this.isDrawerVisible",this.isDrawerVisible);
    
  // }
  // testClick(): void {
  //   console.log('Button click is working!');
  // }
  
  close(): void {
    console.log('Drawer closed');
    this.isDrawerVisible = false;
  }

  
}
