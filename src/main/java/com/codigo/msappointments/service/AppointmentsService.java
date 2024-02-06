package com.codigo.msappointments.service;

import com.codigo.appointmentslibrary.response.ResponseBase;
import com.codigo.msappointments.aggregates.request.RequestAppointment;

public interface AppointmentsService {
    ResponseBase createAppointment(RequestAppointment requestAppointment);

    ResponseBase findOneAppointmentById(int id);

    ResponseBase findAllAppointments();

    ResponseBase updateAppointment(int id, RequestAppointment requestAppointment);

    ResponseBase deleteAppointment(int id);
}
