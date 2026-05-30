package com.example.hospital_management.repository;

import com.example.hospital_management.entity.Appointment;
import com.example.hospital_management.entity.Employee;
import com.example.hospital_management.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    long countByHospitalAndAppointmentDateAndSlotTimeAndStatus(
            Hospital hospital,
            LocalDate appointmentDate,
            LocalTime slotTime,
            String status
    );

    boolean existsByEmployeeAndAppointmentDateAndStatus(
            Employee employee,
            LocalDate appointmentDate,
            String status
    );
}