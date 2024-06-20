package dh.backend.clinicaodontologica.controller;

import dh.backend.clinicaodontologica.dto.request.TurnoModificacionDto;
import dh.backend.clinicaodontologica.dto.request.TurnoRequestDto;
import dh.backend.clinicaodontologica.dto.response.TurnoResponseDto;
import dh.backend.clinicaodontologica.exception.BadRequestException;
import dh.backend.clinicaodontologica.exception.ResourceNotFoundException;
import dh.backend.clinicaodontologica.service.ITurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/turno")
public class TurnoController {
    private ITurnoService turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping
    public ResponseEntity<TurnoResponseDto> agregarTurno(@RequestBody TurnoRequestDto turno) throws BadRequestException, ResourceNotFoundException {
        TurnoResponseDto turnoADevolver = turnoService.registrar(turno);
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoADevolver);
    }

    @GetMapping
    public ResponseEntity<List<TurnoResponseDto>> buscarTodosTurnos() throws ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoResponseDto> buscarTurnoPorId(@PathVariable Integer id) throws ResourceNotFoundException {
        TurnoResponseDto turno = turnoService.buscarPorId(id);
        return ResponseEntity.ok(turno);
    }

    @PutMapping
    public ResponseEntity<String> actualizarTurno(@RequestBody TurnoModificacionDto turno) throws ResourceNotFoundException {
        turnoService.actualizarTurno(turno);
        return ResponseEntity.ok("{\"message\": \"Turno modificado\"}");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarTurno(@PathVariable Integer id) throws ResourceNotFoundException {
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("{\"message\": \"Turno eliminado\"}");
    }

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @GetMapping("/fechas")
    public ResponseEntity<List<TurnoResponseDto>> buscarEntreFechas(@RequestParam String inicio, @RequestParam String fin) throws ResourceNotFoundException {
        LocalDate fechaInicio = LocalDate.parse(inicio, formatter);
        LocalDate fechaFinal = LocalDate.parse(fin, formatter);

        return ResponseEntity.ok(turnoService.buscarTurnoEntreFechas(fechaInicio, fechaFinal));
    }

    @GetMapping("/posteriores")
    public ResponseEntity<List<TurnoResponseDto>> listarTurnosPosterioresAFechaActual() throws ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.listarTurnosPosterioresAFechaActual());
    }

}