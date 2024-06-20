package dh.backend.clinicaodontologica.service.impl;

import dh.backend.clinicaodontologica.entity.Odontologo;
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

    public List<Odontologo> buscarTodosOdontologos() throws ResourceNotFoundException {
        LOGGER.info("Buscando todos los odontólogos");
        List<Odontologo> odontologos = odontologoRepository.findAll();
        if (odontologos.isEmpty()) {
            LOGGER.info("No se encontraron odontólogos");
            throw new ResourceNotFoundException("{\"message\": \"No se encontraron odontólogos\"}");
        }
        LOGGER.info("Se encontraron {} odontólogos", odontologos.size());
        return odontologos;
    }

    public Optional<Odontologo> buscarUnOdontologo(Integer id) throws ResourceNotFoundException {
        LOGGER.info("Buscando odontólogo con ID: {}", id);
        Optional<Odontologo> odontologo = odontologoRepository.findById(id);
        if (odontologo.isEmpty()) {
            LOGGER.info("Odontologo no encontrado con ID: {}", id);
            throw new ResourceNotFoundException("{\"message\": \"Odontólogo con id " + id + " no encontrado\"}");
        }
        LOGGER.info("Odontólogo encontrado con ID: {}", id);
        return odontologo;
    }

    @Override
    public void modificarOdontologo(Odontologo odontologo) throws ResourceNotFoundException {
        LOGGER.info("Modificando odontólogo con ID: {}", odontologo.getId());
        Optional<Odontologo> odontologoAModificar = odontologoRepository.findById(odontologo.getId());
        if (odontologoAModificar.isPresent()) {
            odontologoRepository.save(odontologo);
            LOGGER.info("Odontologo actualizado con éxito");
        } else {
            LOGGER.info("Odontologo no encontrado para modificar");
            throw new ResourceNotFoundException("{\"message\": \"Odontólogo no encontrado para modificar\"}");
        }
    }

    @Override
    public void eliminarOdontologo(Integer id) throws ResourceNotFoundException {
        LOGGER.info("Eliminando odontólogo con ID: {}", id);
        Optional<Odontologo> odontologoOptional = buscarUnOdontologo(id);
        if (odontologoOptional.isPresent()) {
            odontologoRepository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("{\"message\": \"Odontologo no encontrado\"}");
        }
    }

    @Override
    public List<Odontologo> buscarPorApellido(String apellido) throws ResourceNotFoundException {
        LOGGER.info("Buscando odontólogos con apellido: {}", apellido);
        List<Odontologo> odontologosApellido = odontologoRepository.buscarPorApellido(apellido);
        if (odontologosApellido.isEmpty()) {
            LOGGER.info("No se encontraron odontólogos con apellido: {}", apellido);
            throw new ResourceNotFoundException("{\"message\": \"Odontólogo con apellido " + apellido + " no encontrado\"}");
        }
        LOGGER.info("Se encontraron {} odontólogos", odontologosApellido.size());
        return odontologosApellido;
    }

    @Override
    public List<Odontologo> buscarPorNombre(String nombre) throws ResourceNotFoundException {
        LOGGER.info("Buscando odontólogos con nombre: {}", nombre);
        List<Odontologo> odontologosNombre = odontologoRepository.findByNombreLike(nombre);
        if (odontologosNombre.isEmpty()) {
            LOGGER.info("No se encontraron odontólogos con nombre: {}", nombre);
            throw new ResourceNotFoundException("{\"message\": \"Odontólogo con apellido " + nombre + " no encontrado\"}");
        }
        LOGGER.info("Se encontraron {} odontólogos", odontologosNombre.size());
        return odontologosNombre;
    }
}