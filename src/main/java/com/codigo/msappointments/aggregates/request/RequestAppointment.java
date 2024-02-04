package com.codigo.msappointments.aggregates.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
