# LiveSeat - Real-Time Event Ticketing System

*LiveSeat* is an advanced event ticketing system that combines a Spring Boot backend with an Angular frontend to enable real-time ticket management. This system offers seamless user interaction, live updates, and robust backend services to provide an exceptional event management experience.
---
## Features
### Backend (Spring Boot) Features:
- **User Authentication**: Secure user login and sign-up using JWT-based authentication.
- **Event Management**: Create, update, and manage events.
- **Ticket Management**: Handle ticket creation, updates, and deletions.
- **WebSocket Integration**: Real-time updates for ticket bookings and activities.
- **CLI Integration**: A Java-based Command Line Interface (CLI) for system initialization and configuration.

### Frontend (Angular) Features:
- **Real-Time Ticket Updates**: WebSocket integration for live updates.
- **Event Dashboard**: View and manage events and tickets.
- **User-Friendly Interface**: Responsive and mobile-friendly design with Angular and Tailwind CSS.
- **Authentication**: Secure JWT-based login and session management.
---

## Tech Stack
### Backend:
- **Framework**: Spring Boot (Java)
- **Authentication**: JWT (JSON Web Tokens)
- **Database**: MySQL
- **WebSocket**: For real-time communication
- **Logging**: Integrated system activity logs
- **Build Tool**: Maven
- **CLI**: Core Java CLI for system configuration

### Frontend:
- **Framework**: Angular (v17)
- **State Management**: RxJS and Angular Services
- **Real-Time Updates**: WebSocket integration
- **UI Styling**: Tailwind CSS
- **Routing**: Angular Router
- **Form Handling**: Angular Reactive Forms
- **HTTP Requests**: HttpClient module

---

## Setup Instructions

### Prerequisites

#### For Backend (Spring Boot):
- Java 17 or higher
- Maven
- MySQL (with a database instance created for the application)

#### For Frontend (Angular):
- Node.js (v18 or higher)
- npm (v9 or higher)
- Angular CLI(v17)

### Installation

#### Backend Setup:
1. Clone the repository:
   ```bash
   git clone https://github.com/Davindi/liveSeat.git
   cd LiveSeat/server
   ```
2. Configure the database:
   - Update the `application.properties` file with your MySQL credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/liveseat
     spring.datasource.username=your-username
     spring.datasource.password=your-password
     ```

3. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the Swagger API documentation:
   - Open your browser and navigate to ` http://localhost:8080/swagger-ui/index.html#`.

#### Frontend Setup:

1. Navigate to the frontend directory:
   ```bash
   cd ../frontend
   ```
2. Run the Angular application:
   ```bash
   ng serve
   ```
4. Open the application in your browser at `http://localhost:4200`.
--
## Usage Instructions

### Backend Configuration:
- Ensure the backend server is running and connected to the database.
- Use the provided Swagger UI for API testing.

### CLI Usage:
1. Navigate to the CLI directory:
   ```bash
   cd ../cli
   ```
2. Compile and run the CLI:
   ```bash
   javac Main.java
   java Main
   ```
3. Use the CLI commands to initialize and configure system settings.

### Frontend Usage:
1. Login to the system using valid credentials.
2. Navigate through the dashboard to:
   - View real-time event and ticket updates.
   - Manage event details and status.

### WebSocket Integration:
- Ensure the WebSocket server is running at `ws://localhost:8080/ws`.
- Real-time updates can be viewed on the dashboard as events and tickets are managed.

---

## Troubleshooting

- **Database Connection Issues**:
  - Verify the MySQL server is running and the credentials in `application.properties` are correct.

- **WebSocket Connection Issues**:
  - Ensure the WebSocket endpoint matches the backend configuration.
  - Check for firewall or network restrictions.

- **Frontend Not Loading**:
  - Ensure the Angular development server is running and accessible at `http://localhost:4200`.
  - Clear browser cache and try again.

Developed by: W M S H Davindi (20223136/w1986623)  
For inquiries, contact: sihara.20223136@iit.ac.lk

