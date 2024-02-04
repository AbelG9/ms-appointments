package com.codigo.msappointments.aggregates.request;

import com.codigo.msappointments.entity.PersonsEntity;
import com.codigo.msappointments.entity.SpecialistsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class RequestAppointment {
    private String subject;
    private String details;
    private Integer officeNumber;
    private String appointmentDate;
    private String appointmentTime;
    private int personsId;
    private int specialistsId;
}
