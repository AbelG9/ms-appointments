package com.codigo.msappointments.entity;

import com.codigo.appointmentslibrary.model.Audit;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

;

@Entity
@Getter
@Setter
@Table(name = "appointments")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentsEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_appointments")
    private int idAppointments;
    @Column(name = "subject", length = 100, nullable = false)
    private String subject;
    @Column(name = "details", length = 200, nullable = true)
    private String details;
    @Column(name = "office_number", nullable = false)
    private Integer officeNumber;
    @Column(name = "status", nullable = false)
    private int status;
    @Column(name = "appointment_date", nullable = false)
    private Timestamp appointmentDate;
    @ManyToOne
    @JoinColumn(name = "persons_id_persons", nullable = false)
    private PersonsEntity personsEntity;
    @ManyToOne
    @JoinColumn(name = "specialists_id_specialists", nullable = false)
    private SpecialistsEntity specialistsEntity;
}
