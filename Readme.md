# Hospital Management System

A Spring Boot based Hospital Management System that allows employees to book appointments in hospitals while enforcing business rules and slot restrictions.

## Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- Postman

## Features

### Employee Management
- Active/Inactive employees
- Employee belongs to a corporation

### Hospital Management
- Active/Inactive hospitals
- Hospital opening and closing timings

### Appointment Booking

Business Rules Implemented:

1. Employee must be active
2. Hospital must be active
3. No appointments in the past
4. Only hourly slots allowed
5. Maximum 3 bookings per slot
6. Cancelled appointments ignored during slot counting
7. One appointment per employee per day
8. Appointment must be within hospital timings

## API Endpoint

### Book Appointment

POST

```http
http://localhost:8080/appointments/book
```

Sample Request:

```json
{
  "employeeId": 1,
  "hospitalId": 1,
  "appointmentDate": "2026-06-21",
  "slotTime": "10:00:00"
}
```

Sample Response:

```json
{
  "appointmentId": 5,
  "appointmentDate": "2026-06-21",
  "slotTime": "10:00:00",
  "status": "BOOKED"
}
```

## Database Tables

- employees
- hospitals
- corporations
- appointments

## Run Project

1. Clone repository
2. Configure PostgreSQL database
3. Update application.properties
4. Run:

```bash
mvn spring-boot:run
```

5. Test APIs using Postman

## Author

Prasoon Kumar