package com.codigo.msappointments.repository;


import com.codigo.msappointments.entity.SpecialistsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialistsRepository extends JpaRepository<SpecialistsEntity,Integer> {
    boolean existsSpecialistsByCmpNumber(String cmpNumber);
}
