package dh.backend.clinicaodontologica.service.impl;

import dh.backend.clinicaodontologica.entity.Paciente;
import dh.backend.clinicaodontologica.exception.BadRequestException;
import dh.backend.clinicaodontologica.exception.ResourceNotFoundException;
import dh.backend.clinicaodontologica.repository.IPacienteRepository;
import dh.backend.clinicaodontologica.service.IPacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {
    private static Logger LOGGER = LoggerFactory.getLogger(IPacienteService.class);
    private IPacienteRepository pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente registrarPaciente(Paciente paciente) throws BadRequestException {
        LOGGER.info("Registrando paciente: {}", paciente);
        if (paciente.getNombre() == null) {
            throw new BadRequestException("{\"message\": \"El nombre del paciente es obligatorio\"}");
        }
        if (paciente.getApellido() == null) {
            throw new BadRequestException("{\"message\": \"El apellido del paciente es obligatorio\"}");
        }
        Paciente pacientePersistido = pacienteRepository.save(paciente);
        LOGGER.info("Paciente persistido con exito, id: {}", pacientePersistido.getId());
        return pacientePersistido;
    }

    public List<Paciente> buscarTodos() throws ResourceNotFoundException {
        LOGGER.info("Buscando todos los pacientes");
        List<Paciente> pacientes = pacienteRepository.findAll();
        if (pacientes.isEmpty()) {
            LOGGER.info("No se encontraron pacientes");
            throw new ResourceNotFoundException("{\"message\": \"No se encontraron pacientes\"}");
        }
        LOGGER.info("Se encontraron {} pacientes", pacientes.size());
        return pacientes;
    }

    public Optional<Paciente> buscarPorId(Integer id) throws ResourceNotFoundException {
        LOGGER.info("Buscando paciente con ID: {}", id);
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        if (paciente.isEmpty()){
            LOGGER.info("Paciente no encontrado con ID: {}", id);
            throw new ResourceNotFoundException("{\"message\": \"Paciente con id " + id + " no encontrado\"}");
        }
        LOGGER.info("Paciente encontrado con ID: {}", id);
        return paciente;
    }

    @Override
    public void actualizarPaciente(Paciente paciente) throws ResourceNotFoundException {
        LOGGER.info("Actualizando paciente con ID: {}", paciente.getId());
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(paciente.getId());
        if (pacienteOptional.isPresent()){
            pacienteRepository.save(paciente);
            LOGGER.info("Paciente actualizado con éxito");
        } else {
            LOGGER.info("Paciente no encontrado para modificar");
            throw new ResourceNotFoundException("{\"message\": \"Paciente no encontrado para modificar\"}");
        }
    }


  @Override
    public void eliminarPaciente(Integer id) throws ResourceNotFoundException {
      LOGGER.info("Eliminando paciente con ID: {}", id);
      Optional<Paciente> pacienteOptional = buscarPorId(id);
        if(pacienteOptional.isPresent()){
            pacienteRepository.deleteById(id);
            LOGGER.info("Paciente con ID: {} eliminado con éxito", id);
        } else {
            throw new ResourceNotFoundException("{\"message\": \"Paciente no encontrado\"}");
        }
    }

   @Override
    public List<Paciente> buscarPorDni(String dni) throws ResourceNotFoundException {
       LOGGER.info("Buscando pacientes con DNI: {}", dni);
       List<Paciente> pacientes = pacienteRepository.findByDni(dni);
       if (pacientes.isEmpty()) {
           LOGGER.info("No se encontraron pacientes con dni: {}", dni);
           throw new ResourceNotFoundException("{\"message\": \"Paciente con dni " + dni + " no encontrado\"}");
       }
       LOGGER.info("Se encontraron {} pacientes", pacientes.size());
       return pacientes;
   }

    @Override
    public List<Paciente> buscarPorProvincia(String provincia) throws ResourceNotFoundException {
        LOGGER.info("Buscando pacientes en la provincia: {}", provincia);
        List<Paciente> pacientesProvincia = pacienteRepository.findByProvincia(provincia);
        if (pacientesProvincia.isEmpty()) {
            LOGGER.info("No se encontraron pacientes de la provincia: {}", provincia);
            throw new ResourceNotFoundException("{\"message\": \"Pacientes de la provincia " + provincia + " no encontrados\"}");
        }
        LOGGER.info("Se encontraron {} pacientes", pacientesProvincia.size());
        return pacientesProvincia;
    }
}
