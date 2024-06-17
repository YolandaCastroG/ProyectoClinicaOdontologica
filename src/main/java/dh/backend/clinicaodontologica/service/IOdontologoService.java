package dh.backend.clinicaodontologica.service;

import dh.backend.clinicaodontologica.entity.Odontologo;
import dh.backend.clinicaodontologica.exception.BadRequestException;
import dh.backend.clinicaodontologica.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IOdontologoService {
    Odontologo registarOdontologo(Odontologo odontologo) throws BadRequestException;
    Optional<Odontologo> buscarUnOdontologo(Integer id);
    List<Odontologo> buscarTodosOdontologos();
    void modificarOdontologo(Odontologo odontologo);
    void eliminarOdontologo(Integer id) throws ResourceNotFoundException;

    //Metodos con HQL
    List<Odontologo> buscarPorApellido(String apellido);
    List<Odontologo> buscarPorNombre(String nombre);
}