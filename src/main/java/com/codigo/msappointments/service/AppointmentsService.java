package com.codigo.msappointments.service;

import com.codigo.msappointments.aggregates.request.RequestAppointment;
import com.codigo.msappointments.aggregates.response.ResponseBase;

public interface AppointmentsService {
    ResponseBase createAppointment(RequestAppointment requestAppointment);

    ResponseBase findOneAppointmentById(int id);

    ResponseBase findAllAppointments();

    ResponseBase updateAppointment(int id, RequestAppointment requestAppointment);

    ResponseBase deleteAppointment(int id);
}
