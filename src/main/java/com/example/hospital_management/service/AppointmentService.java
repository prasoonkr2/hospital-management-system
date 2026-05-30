package com.example.hospital_management.service;

import com.example.hospital_management.dto.AppointmentRequest;
import com.example.hospital_management.entity.Appointment;
import com.example.hospital_management.entity.Employee;
import com.example.hospital_management.entity.Hospital;
import com.example.hospital_management.repository.AppointmentRepository;
import com.example.hospital_management.repository.EmployeeRepository;
import com.example.hospital_management.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    public Appointment bookAppointment(AppointmentRequest request) {

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Hospital hospital = hospitalRepository.findById(request.getHospitalId())
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

        LocalDate appointmentDate = request.getAppointmentDate();
        LocalTime slotTime = request.getSlotTime();

        // Rule 2: Appointment only for active employees
        if (!employee.isActive()) {
            throw new RuntimeException("Appointment cannot be booked for inactive employee");
        }

        // Rule 3: Appointment only in active hospitals
        if (!hospital.isActive()) {
            throw new RuntimeException("Appointment cannot be booked in inactive hospital");
        }

        // Rule 8: Cannot book appointment in the past
        if (appointmentDate.isBefore(LocalDate.now())) {
            throw new RuntimeException("Appointment cannot be booked in the past");
        }

        // Rule 4: Slot must be hourly, like 09:00, 10:00, 11:00
        if (slotTime.getMinute() != 0 || slotTime.getSecond() != 0) {
            throw new RuntimeException("Only hourly slots are allowed");
        }

        // Rule 9: Slot must be inside hospital working hours
        if (slotTime.isBefore(hospital.getOpeningTime()) ||
                !slotTime.isBefore(hospital.getClosingTime())) {
            throw new RuntimeException("Appointment slot is outside hospital working hours");
        }

        // Rule 7: One appointment per employee per day
        boolean alreadyBooked = appointmentRepository.existsByEmployeeAndAppointmentDateAndStatus(
                employee,
                appointmentDate,
                "BOOKED"
        );

        if (alreadyBooked) {
            throw new RuntimeException("Employee already has an appointment on this date");
        }

        // Rule 5 and 6: Max 3 patients per slot, cancelled appointments ignored
        long bookedCount = appointmentRepository.countByHospitalAndAppointmentDateAndSlotTimeAndStatus(
                hospital,
                appointmentDate,
                slotTime,
                "BOOKED"
        );

        if (bookedCount >= 3) {
            throw new RuntimeException("Slot is full. Maximum 3 appointments allowed");
        }

        Appointment appointment = new Appointment();
        appointment.setEmployee(employee);
        appointment.setHospital(hospital);
        appointment.setAppointmentDate(appointmentDate);
        appointment.setSlotTime(slotTime);
        appointment.setStatus("BOOKED");

        return appointmentRepository.save(appointment);
    }
}