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

import java.sql.Timestamp;
import java.util.List;
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
            AppointmentsEntity appointmentsEntity = getAppointmentsEntity(requestAppointment);
            appointmentsRepository.save(appointmentsEntity);
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESSAGE_SUCCESS, Optional.of(appointmentsEntity));
        } else {
            return new ResponseBase(Constants.CODE_ERROR_DATA_INPUT, Constants.MESSAGE_ERROR_DATA_NOT_VALID, Optional.empty());
        }
    }

    @Override
    public ResponseBase findOneAppointmentById(int id) {
        Optional<AppointmentsEntity> appointment = appointmentsRepository.findById(id);
        if (appointment.isPresent()) {
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESSAGE_SUCCESS, appointment);
        } else {
            return new ResponseBase(Constants.CODE_ERROR_DATA_NOT, Constants.MESSAGE_ZERO_ROWS, Optional.empty());
        }
    }

    @Override
    public ResponseBase findAllAppointments() {
        List<AppointmentsEntity> appointments = appointmentsRepository.findAll();
        if (!appointments.isEmpty()) {
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESSAGE_SUCCESS, Optional.of(appointments));
        } else {
            return new ResponseBase(Constants.CODE_ERROR_DATA_NOT, Constants.MESSAGE_ZERO_ROWS, Optional.empty());
        }
    }

    @Override
    public ResponseBase updateAppointment(int id, RequestAppointment requestAppointment) {
        boolean existsAppointment = appointmentsRepository.existsById(id);
        if (existsAppointment) {
            Optional<AppointmentsEntity> appointment = appointmentsRepository.findById(id);
            boolean validationEntity = appointmentsValidations.validateInput(requestAppointment);
            if (validationEntity) {
                AppointmentsEntity appointmentUpdate = getAppointmentsEntityUpdate(requestAppointment, appointment.get());
                appointmentsRepository.save(appointmentUpdate);
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESSAGE_SUCCESS, Optional.of(appointmentUpdate));
            } else {
                return new ResponseBase(Constants.CODE_ERROR_DATA_INPUT, Constants.MESSAGE_ERROR_DATA_NOT_VALID, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR_DATA_NOT, Constants.MESSAGE_ERROR_NOT_UPDATE_PERSONS, Optional.empty());
        }
    }

    @Override
    public ResponseBase deleteAppointment(int id) {
        boolean existsAppointment = appointmentsRepository.existsById(id);
        if (existsAppointment) {
            Optional<AppointmentsEntity> appointment = appointmentsRepository.findById(id);
            AppointmentsEntity appointmentDelete = getPersonsEntityDeleted(appointment.get());
            appointmentsRepository.save(appointmentDelete);
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESSAGE_SUCCESS, Optional.of(appointmentDelete));
        } else {
            return new ResponseBase(Constants.CODE_ERROR_EXIST, Constants.MESSAGE_ERROR_NOT_DELETE_PERSONS, Optional.empty());
        }
    }

    private AppointmentsEntity getPersonsEntityDeleted(AppointmentsEntity appointmentsEntity) {
        appointmentsEntity.setStatus(Constants.STATUS_INACTIVE);
        appointmentsEntity.setDateDeleted(Util.getActualTimestamp());
        appointmentsEntity.setUserDeleted(Constants.AUDIT_ADMIN);
        return appointmentsEntity;
    }

    private AppointmentsEntity getAppointmentsEntity(RequestAppointment requestAppointment) {
        AppointmentsEntity appointmentsEntity = new AppointmentsEntity();
        return getAppointments(requestAppointment, appointmentsEntity, false);
    }

    private AppointmentsEntity getAppointmentsEntityUpdate(RequestAppointment requestAppointment, AppointmentsEntity appointmentsEntity) {
        return getAppointments(requestAppointment, appointmentsEntity, true);
    }

    private AppointmentsEntity getAppointments(RequestAppointment requestAppointment, AppointmentsEntity appointmentsEntity, boolean isUpdate) {
        appointmentsEntity.setSubject(requestAppointment.getSubject());
        appointmentsEntity.setDetails(requestAppointment.getDetails());
        appointmentsEntity.setOfficeNumber(requestAppointment.getOfficeNumber());
        appointmentsEntity.setStatus(Constants.STATUS_ACTIVE);
        appointmentsEntity.setAppointmentDate(generateTimestamp(requestAppointment));
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

    private Timestamp generateTimestamp(RequestAppointment requestAppointment){
        return Timestamp.valueOf(requestAppointment.getAppointmentDate()+" "+requestAppointment.getAppointmentTime()+".000");
    }
}
