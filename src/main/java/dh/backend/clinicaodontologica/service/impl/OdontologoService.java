package dh.backend.clinicaodontologica.service.impl;

import dh.backend.clinicaodontologica.entity.Odontologo;
import dh.backend.clinicaodontologica.entity.Paciente;
import dh.backend.clinicaodontologica.exception.BadRequestException;
import dh.backend.clinicaodontologica.exception.ResourceNotFoundException;
import dh.backend.clinicaodontologica.repository.IOdontologoRepository;
import dh.backend.clinicaodontologica.service.IOdontologoService;
import dh.backend.clinicaodontologica.service.IPacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {
    private static Logger LOGGER = LoggerFactory.getLogger(IPacienteService.class);
    private IOdontologoRepository odontologoRepository;

    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo registarOdontologo(Odontologo odontologo) throws BadRequestException {
        LOGGER.info("Registrando odontólogo: {}", odontologo);
        if (odontologo.getNombre() == null) {
            throw new BadRequestException("{\"message\": \"El nombre del odontólogo es obligatorio\"}");
        }
        if (odontologo.getApellido() == null) {
            throw new BadRequestException("{\"message\": \"El apellido del odontólogo es obligatorio\"}");
        }
        if (odontologo.getMatricula() == null) {
            throw new BadRequestException("{\"message\": \"La matricula del  odontólogo es obligatorio\"}");
        }
        Odontologo odontologoPersistido = odontologoRepository.save(odontologo);
        LOGGER.info("Odontólogo persistido con exito, id: {}", odontologoPersistido.getId());
        return odontologoPersistido;
    }

    public Optional<Odontologo> buscarUnOdontologo(Integer id){
        LOGGER.info("Buscando odontólogo con ID: {}", id);
        return odontologoRepository.findById(id);
    }

    public List<Odontologo> buscarTodosOdontologos(){
        LOGGER.info("Buscando todos los odontólogos");
        return odontologoRepository.findAll();
    }

    @Override
    public void modificarOdontologo(Odontologo odontologo){
        LOGGER.info("Modificando odontólogo con ID: {}", odontologo.getId());
        odontologoRepository.save(odontologo);
    }

    @Override
    public void eliminarOdontologo(Integer id) throws ResourceNotFoundException {
        LOGGER.info("Eliminando odontólogo con ID: {}", id);
        Optional<Odontologo> odontologoOptional = buscarUnOdontologo(id);
        if(odontologoOptional.isPresent())
            odontologoRepository.deleteById(id);
        else throw new ResourceNotFoundException("{\"message\": \"Odontologo no encontrado\"}");
    }

    @Override
    public List<Odontologo> buscarPorApellido(String apellido) {
        LOGGER.info("Buscando odontólogos con apellido: {}", apellido);
        return odontologoRepository.buscarPorApellido(apellido);
    }

    @Override
    public List<Odontologo> buscarPorNombre(String nombre) {
        LOGGER.info("Buscando odontólogos con nombre: {}", nombre);
        return odontologoRepository.findByNombreLike(nombre);
    }
}