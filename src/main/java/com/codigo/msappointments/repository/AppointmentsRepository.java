package com.codigo.msappointments.repository;

import com.codigo.msappointments.entity.AppointmentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentsRepository extends JpaRepository<AppointmentsEntity, Integer> {
}
