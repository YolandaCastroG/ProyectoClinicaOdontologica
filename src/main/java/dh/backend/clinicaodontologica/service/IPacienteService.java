package dh.backend.clinicaodontologica.service;

import dh.backend.clinicaodontologica.entity.Paciente;
import dh.backend.clinicaodontologica.exception.BadRequestException;
import dh.backend.clinicaodontologica.exception.ResourceNotFoundException;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {
    Paciente registrarPaciente(Paciente paciente) throws BadRequestException;
    Optional<Paciente> buscarPorId(Integer id) throws ResourceNotFoundException;
    List<Paciente> buscarTodos() throws ResourceNotFoundException;
    void actualizarPaciente(Paciente paciente) throws ResourceNotFoundException;
    void eliminarPaciente(Integer id) throws ResourceNotFoundException;

    //Metodos con HQL
    List<Paciente> buscarPorDni(String dni) throws ResourceNotFoundException;
    List<Paciente> buscarPorProvincia(@Param("provincia") String provincia) throws ResourceNotFoundException;
}