package com.codigo.msappointments.service.impl;

import com.codigo.msappointments.aggregates.request.RequestAppointment;
import com.codigo.msappointments.aggregates.response.ResponseBase;
import com.codigo.msappointments.constants.Constants;
import com.codigo.msappointments.entity.AppointmentsEntity;
import com.codigo.msappointments.entity.PersonsEntity;
import com.codigo.msappointments.entity.SpecialistsEntity;
import com.codigo.msappointments.repository.AppointmentsRepository;
import com.codigo.msappointments.repository.PersonsRepository;
import com.codigo.msappointments.repository.SpecialistsRepository;
import com.codigo.msappointments.service.AppointmentsService;
import com.codigo.msappointments.util.AppointmentsValidations;
import com.codigo.msappointments.util.Util;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentsService {
    private final AppointmentsRepository appointmentsRepository;
    private final AppointmentsValidations appointmentsValidations;
    private final PersonsRepository personsRepository;
    private final SpecialistsRepository specialistsRepository;

    public AppointmentServiceImpl(AppointmentsRepository appointmentsRepository, AppointmentsValidations appointmentsValidations, PersonsRepository personsRepository, SpecialistsRepository specialistsRepository) {
        this.appointmentsRepository = appointmentsRepository;
        this.appointmentsValidations = appointmentsValidations;
        this.personsRepository = personsRepository;
        this.specialistsRepository = specialistsRepository;
    }

    @Override
    public ResponseBase createAppointment(RequestAppointment requestAppointment) {
        boolean validateAppointment = appointmentsValidations.validateInput(requestAppointment);
        if (validateAppointment) {
            AppointmentsEntity appointmentsEntity = getAppointmentsEntity(requestAppointment, false);
            appointmentsRepository.save(appointmentsEntity);
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESSAGE_SUCCESS, Optional.of(appointmentsEntity));
        } else {
            return new ResponseBase(Constants.CODE_ERROR_DATA_INPUT, Constants.MESSAGE_ERROR_DATA_NOT_VALID, Optional.empty());
        }
    }

    private AppointmentsEntity getAppointmentsEntity(RequestAppointment requestAppointment, boolean isUpdate) {
        AppointmentsEntity appointmentsEntity = new AppointmentsEntity();
        appointmentsEntity.setSubject(requestAppointment.getSubject());
        appointmentsEntity.setDetails(requestAppointment.getDetails());
        appointmentsEntity.setOfficeNumber(requestAppointment.getOfficeNumber());
        appointmentsEntity.setStatus(Constants.STATUS_ACTIVE);

        String appointmentTimestamp = requestAppointment.getAppointmentDate()+" "+requestAppointment.getAppointmentTime()+".000";

        appointmentsEntity.setAppointmentDate(Timestamp.valueOf(appointmentTimestamp));
        appointmentsEntity.setPersonsEntity(getPersonsEntity(requestAppointment));
        appointmentsEntity.setSpecialistsEntity(getSpecialistEntity(requestAppointment));
        if (!isUpdate) {
            appointmentsEntity.setDateCreated(Util.getActualTimestamp());
            appointmentsEntity.setUserCreated(Constants.AUDIT_ADMIN);
        } else {
            appointmentsEntity.setDateModified(Util.getActualTimestamp());
            appointmentsEntity.setUserModified(Constants.AUDIT_ADMIN);
        }
        return appointmentsEntity;
    }

    private PersonsEntity getPersonsEntity(RequestAppointment requestAppointment) {
        return personsRepository.findById(requestAppointment.getPersonsId()).orElse(null);
    }

    private SpecialistsEntity getSpecialistEntity(RequestAppointment requestAppointment) {
        return specialistsRepository.findById(requestAppointment.getSpecialistsId()).orElse(null);
    }
}
