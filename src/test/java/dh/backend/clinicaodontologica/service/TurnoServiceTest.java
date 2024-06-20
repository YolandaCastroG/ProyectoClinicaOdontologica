package dh.backend.clinicaodontologica.service;

import dh.backend.clinicaodontologica.dto.request.TurnoRequestDto;
import dh.backend.clinicaodontologica.dto.response.TurnoResponseDto;
import dh.backend.clinicaodontologica.entity.Domicilio;
import dh.backend.clinicaodontologica.entity.Odontologo;
import dh.backend.clinicaodontologica.entity.Paciente;
import dh.backend.clinicaodontologica.exception.BadRequestException;
import dh.backend.clinicaodontologica.exception.ResourceNotFoundException;
import dh.backend.clinicaodontologica.service.impl.OdontologoService;
import dh.backend.clinicaodontologica.service.impl.PacienteService;
import dh.backend.clinicaodontologica.service.impl.TurnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TurnoServiceTest {
    private static Logger LOGGER = LoggerFactory.getLogger(TurnoServiceTest.class);
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private PacienteService pacienteService;
    private Odontologo odontologo;
    private Paciente paciente;

    @BeforeEach
    void setUp() throws BadRequestException {
        // Crear y guardar un odontólogo
        odontologo = new Odontologo();
        odontologo.setNombre("Pepito");
        odontologo.setApellido("Perez");
        odontologo.setMatricula("1234");
        odontologoService.registarOdontologo(odontologo);

        // Crear y guardar un paciente
        paciente = new Paciente();
        paciente.setApellido("Simpson");
        paciente.setNombre("Homero");
        paciente.setDni("12345");
        paciente.setFechaIngreso(LocalDate.of(2024, 6, 18));
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle Falsa");
        domicilio.setNumero(865);
        domicilio.setLocalidad("Springfield");
        domicilio.setProvincia("Estados Unidos");
        paciente.setDomicilio(domicilio);
        pacienteService.registrarPaciente(paciente);
    }

    @Test
    @DisplayName("Testear que un turno fue guardado")
    void testTurnoGuardado() throws BadRequestException {
        TurnoRequestDto turnoRequestDto = new TurnoRequestDto();
        turnoRequestDto.setPaciente_id(paciente.getId());
        turnoRequestDto.setOdontologo_id(odontologo.getId());
        turnoRequestDto.setFecha("2024-06-20");

        TurnoResponseDto turnoDesdeLaBd = turnoService.registrar(turnoRequestDto);

        assertNotNull(turnoDesdeLaBd);
        assertEquals(turnoRequestDto.getFecha(), turnoDesdeLaBd.getFecha().toString());
        LOGGER.info("Turno guardado exitosamente en la base de datos: {}", turnoDesdeLaBd.getId());
    }

    @Test
    @DisplayName("Testear busqueda de turno por id")
    void testTurnoPorId() throws BadRequestException, ResourceNotFoundException {
        TurnoRequestDto turnoRequestDto = new TurnoRequestDto();
        turnoRequestDto.setPaciente_id(paciente.getId());
        turnoRequestDto.setOdontologo_id(odontologo.getId());
        turnoRequestDto.setFecha("2024-06-20");

        TurnoResponseDto turnoGuardado = turnoService.registrar(turnoRequestDto);
        Integer id = turnoGuardado.getId();

        TurnoResponseDto turnoEncontrado = turnoService.buscarPorId(id);

        assertNotNull(turnoEncontrado);
        assertEquals(id, turnoEncontrado.getId());
    }

    @Test
    @DisplayName("Testear busqueda de todos los turnos")
    void testBusquedaTodos() throws ResourceNotFoundException {
        List<TurnoResponseDto> turnos = turnoService.buscarTodos();

        assertNotNull(turnos);
        assertTrue(turnos.size() != 0);
    }
}