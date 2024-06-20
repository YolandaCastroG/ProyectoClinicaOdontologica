package dh.backend.clinicaodontologica.controller;

import dh.backend.clinicaodontologica.entity.Paciente;
import dh.backend.clinicaodontologica.exception.BadRequestException;
import dh.backend.clinicaodontologica.exception.ResourceNotFoundException;
import dh.backend.clinicaodontologica.service.IPacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    private static Logger LOGGER = LoggerFactory.getLogger(PacienteController.class);
    private IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente paciente) throws BadRequestException {
        Paciente pacienteARetornar = pacienteService.registrarPaciente(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteARetornar);
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodos() throws ResourceNotFoundException {
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Integer id) throws ResourceNotFoundException {
        Optional<Paciente> paciente = pacienteService.buscarPorId(id);
        return ResponseEntity.ok(paciente.get());
    }

    @PutMapping
    public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente) throws ResourceNotFoundException {
        pacienteService.actualizarPaciente(paciente);
        return ResponseEntity.ok("{\"message\": \"Paciente modificado\"}");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarPaciente(@PathVariable Integer id) throws ResourceNotFoundException {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.ok("{\"message\": \"Paciente eliminado\"}");
    }

     @GetMapping("/dni/{dni}")
    public ResponseEntity<List<Paciente>> buscarPacientePorDni(@PathVariable String dni) throws ResourceNotFoundException {
        List<Paciente> listaPacientes = pacienteService.buscarPorDni(dni);
        return ResponseEntity.ok(listaPacientes);
    }

    @GetMapping("/provincia/{provincia}")
    public ResponseEntity<List<Paciente>> buscarPacientePorProvincia(@PathVariable String provincia) throws ResourceNotFoundException {
        List<Paciente> listadoPacientes = pacienteService.buscarPorProvincia(provincia);
        return ResponseEntity.ok(listadoPacientes);
    }
}