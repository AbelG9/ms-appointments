package com.codigo.msappointments.util;

import com.codigo.appointmentslibrary.util.Util;
import com.codigo.msappointments.aggregates.request.RequestAppointment;
import com.codigo.msappointments.repository.AppointmentsRepository;
import org.springframework.stereotype.Component;

@Component
public class AppointmentsValidations {
    private final AppointmentsRepository appointmentsRepository;

    public AppointmentsValidations(AppointmentsRepository appointmentsRepository) {
        this.appointmentsRepository = appointmentsRepository;
    }

    public boolean validateInput(RequestAppointment requestAppointment) {
        if (requestAppointment == null) {
            return false;
        }
        if (Util.isNullOrEmpty(requestAppointment.getSubject()) || requestAppointment.getOfficeNumber() == null) {
            return false;
        }
        if (Util.isNullOrEmpty(requestAppointment.getAppointmentDate())) {
            return false;
        }
        return true;
    }

    public boolean validateExistsAppointment(RequestAppointment requestAppointment) {
        return appointmentsRepository.existsBySpecialistAndPerson(requestAppointment.getPersonsId(), requestAppointment.getSpecialistsId());
    }
}
