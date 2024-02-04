package com.codigo.msappointments.controller;

import com.codigo.msappointments.aggregates.request.RequestAppointment;
import com.codigo.msappointments.aggregates.response.ResponseBase;
import com.codigo.msappointments.service.AppointmentsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/appointments")
public class AppointmentsController {
    private final AppointmentsService appointmentsService;

    public AppointmentsController(AppointmentsService appointmentsService) {
        this.appointmentsService = appointmentsService;
    }

    @PostMapping
    public ResponseBase createAppointment(@RequestBody RequestAppointment requestAppointment) {
        return appointmentsService.createAppointment(requestAppointment);
    }
}
