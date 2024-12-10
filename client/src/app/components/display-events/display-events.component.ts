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
import {NavbarComponent} from '../navbar/navbar.component'
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-display-events',
  standalone: true,
  imports: [NzCardModule, NzGridModule,NzButtonModule, NzDrawerModule,CommonModule, NzModalModule,ReactiveFormsModule,NzTableModule,NavbarComponent, FormsModule,],
  templateUrl: './display-events.component.html',
  styleUrl: './display-events.component.css'
})
export class DisplayEventsComponent {
  isBuyTicketsModalVisible = false;
  isPurchasedTicketsDrawerVisible = false;
  selectedEvent: any = null;
  ticketCount: number = 1;
  totalPrice: number = 0;
  purchasedTickets: any[] = [];
  events: any[] = [];
  allEvents: any[] = [];
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
   ngOnInit(): void {
    this.isVendorOrCustomer();
    this.appService.getSystemStatus().subscribe({
      next: (status) => (this.isSystemStarted = status,this.fetchVendorEvents()),
      error: (err) => console.error('Error fetching system status', err)
    });
    this.fetchVendorEvents();
    this.fetchAllEvents();
    
  }
  
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
      return userDetails.role;
    }
    return ;
  }
 //--------------customer-----------------


//  open(): void {
//   if (this.isUserLoggedIn()) {
//     this.modal.create({
//       nzTitle: 'Buy Tickets',
//       nzContent: 'Please confirm your ticket purchase.',
//       nzFooter: [
//         {
//           label: 'Cancel',
//           onClick: () => console.log('Purchase cancelled'),
//         },
//         {
//           label: 'Confirm',
//           type: 'primary',
//           onClick: () => console.log('Ticket purchased'),
//         },
//       ],
//     });
//   } else {
//     this.router.navigate(['/login']); // Redirect to the login page
//   }
// }
// Utility to get Customer ID
getCustomerId(): number {
  const token = localStorage.getItem('authToken');
  if (token) {
    const userDetails: { sub: string; email: string; role: string; userId?: any } = jwtDecode(token);
    return userDetails.userId || null;
  }
  return 0;
}
// Open Buy Tickets Modal
openBuyTicketsModal(event: any): void {
  this.selectedEvent = event;
  this.ticketCount = 1;
  this.totalPrice = event.ticketPrice;
  this.isBuyTicketsModalVisible = true;
}

// Calculate Total Price
calculateTotalPrice(): void {
  console.log("this.ticketCount",this.ticketCount);
  
  this.totalPrice = (this.selectedEvent?.ticketPrice || 0) * this.ticketCount;
}

// Handle Buy Tickets Submit
handleBuyTicketsSubmit(): void {
  const customerId = this.getCustomerId(); // Implement this to get customer ID
  if (customerId && this.selectedEvent) {
    this.appService
      .purchaseTickets(customerId, this.selectedEvent.id, this.ticketCount)
      .subscribe({
        next: () => {
          this.isBuyTicketsModalVisible = false;
          this.fetchPurchasedTickets();
        },
        error: (err) => console.error('Error purchasing tickets', err),
      });
  }
}

// Handle Modal Cancel
handleBuyTicketsCancel(): void {
  this.isBuyTicketsModalVisible = false;
}

// Fetch Purchased Tickets
fetchPurchasedTickets(): void {
  const customerId = this.getCustomerId();
  console.log("customerId",customerId);
  
  if (customerId) {
    this.appService.getPurchasedTickets(customerId).subscribe({
      next: (tickets) => (this.purchasedTickets = tickets,console.log("ticket",this.purchasedTickets)
      ),
      error: (err) => console.error('Error fetching purchased tickets', err),
    });
  }
}

// Open Purchased Tickets Drawer
openPurchasedTicketsDrawer(): void {
  this.isPurchasedTicketsDrawerVisible = true;
  this.fetchPurchasedTickets();
}

// Handle Drawer Close
handlePurchasedTicketsDrawerClose(): void {
  this.isPurchasedTicketsDrawerVisible = false;
}





 //---------------Vendor--------------

  isVendor(): boolean {
    const token = localStorage.getItem('authToken');
    if (token) {
      const userDetails: { role: string } = jwtDecode(token);
      return userDetails.role === 'vendor';
    }
    return false;
  }
  
 
  getVendorId(): any | null {
    const token = localStorage.getItem('authToken');
    if (token) {
      const userDetails: { sub: string; email: string; role: string; userId?: any } = jwtDecode(token);
      return userDetails.userId || null;
    }
    return null;
  }
  
  fetchAllEvents() {
    
    this.appService.getAllEvents().subscribe((response) => {
      this.allEvents = response;
    });
  
 
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
  
  // open(card: any): void {
  //   console.log('Open drawer with card:', card);
    
  //   this.selectedCard = card; // Pass selected card details
  //   this.isDrawerVisible = true;
  //   console.log("this.isDrawerVisible",this.isDrawerVisible);
    
  // }
  // testClick(): void {
  //   console.log('Button click is working!');
  // }
  
  // close(): void {
  //   console.log('Drawer closed');
  //   this.isDrawerVisible = false;
  // }

  
}
