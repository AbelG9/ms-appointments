package com.codigo.msappointments.controller;

import com.codigo.msappointments.aggregates.request.RequestAppointment;
import com.codigo.msappointments.aggregates.response.ResponseBase;
import com.codigo.msappointments.service.AppointmentsService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("{id}")
    public ResponseBase findOneAppointmentById(@PathVariable int id) {
        return appointmentsService.findOneAppointmentById(id);
    }

    @GetMapping
    public ResponseBase findAllAppointments() {
        return appointmentsService.findAllAppointments();
    }

    @PatchMapping("{id}")
    public ResponseBase updateAppointment(@PathVariable int id, @RequestBody RequestAppointment requestAppointment) {
        return appointmentsService.updateAppointment(id, requestAppointment);
    }

    @DeleteMapping("{id}")
    public ResponseBase deleteAppointment(@PathVariable int id){
        return appointmentsService.deleteAppointment(id);
    }
}
