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
        LOGGER.info("Intentando registrar paciente: {}", paciente);
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

    public Optional<Paciente> buscarPorId(Integer id) {
        LOGGER.info("Buscando paciente con ID: {}", id);
        return pacienteRepository.findById(id);
    }

    public List<Paciente> buscarTodos() {
        LOGGER.info("Buscando todos los pacientes");
        return pacienteRepository.findAll();
    }

    @Override
    public void actualizarPaciente(Paciente paciente) {
        LOGGER.info("Actualizando paciente con ID: {}", paciente.getId());
        pacienteRepository.save(paciente);
        LOGGER.info("Paciente actualizado con éxito");
    }


  @Override
    public void eliminarPaciente(Integer id) throws ResourceNotFoundException {
      LOGGER.info("Intentando eliminar paciente con ID: {}", id);
      Optional<Paciente> pacienteOptional = buscarPorId(id);
        if(pacienteOptional.isPresent()){
            pacienteRepository.deleteById(id);
            LOGGER.info("Paciente con ID: {} eliminado con éxito", id);
        } else {
            throw new ResourceNotFoundException("{\"message\": \"Paciente no encontrado\"}");
        }
    }

   @Override
    public List<Paciente> buscarPorDni(String dni) {
       LOGGER.info("Buscando pacientes con DNI: {}", dni);
       return pacienteRepository.findByDni(dni);
   }

    @Override
    public List<Paciente> buscarPorProvincia(String provincia) {
        LOGGER.info("Buscando pacientes en la provincia: {}", provincia);
        return pacienteRepository.findByProvincia(provincia);
    }
}
