package dh.backend.clinicaodontologica.service;

import dh.backend.clinicaodontologica.dto.request.TurnoModificacionDto;
import dh.backend.clinicaodontologica.dto.request.TurnoRequestDto;
import dh.backend.clinicaodontologica.dto.response.TurnoResponseDto;
import dh.backend.clinicaodontologica.exception.BadRequestException;
import dh.backend.clinicaodontologica.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface ITurnoService {
    TurnoResponseDto registrar (TurnoRequestDto turnoRequestDto) throws ResourceNotFoundException, BadRequestException;
    TurnoResponseDto buscarPorId(Integer id) throws ResourceNotFoundException;
    List<TurnoResponseDto> buscarTodos() throws ResourceNotFoundException;
    void actualizarTurno(TurnoModificacionDto turnoModificacionDto) throws ResourceNotFoundException;
    void eliminarTurno(Integer id) throws ResourceNotFoundException;

    //Metodos con HQL
    List<TurnoResponseDto> buscarTurnoEntreFechas(LocalDate startDate, LocalDate endDate) throws ResourceNotFoundException;
    List<TurnoResponseDto> listarTurnosPosterioresAFechaActual() throws ResourceNotFoundException;
}