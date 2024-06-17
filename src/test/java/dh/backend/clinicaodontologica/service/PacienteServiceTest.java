package dh.backend.clinicaodontologica.service;

import dh.backend.clinicaodontologica.entity.Domicilio;
import dh.backend.clinicaodontologica.entity.Paciente;
import dh.backend.clinicaodontologica.exception.BadRequestException;
import dh.backend.clinicaodontologica.service.impl.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PacienteServiceTest {
    private static Logger LOGGER = LoggerFactory.getLogger(PacienteServiceTest.class);
    @Autowired
    private PacienteService pacienteService;
    private Paciente paciente;

    @BeforeEach
    void setUp() {
        paciente = new Paciente();
        paciente.setApellido("Castro");
        paciente.setNombre("Yolanda");
        paciente.setDni("12345");
        paciente.setFechaIngreso(LocalDate.of(2024, 6, 16));

        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Flores");
        domicilio.setNumero(1237);
        domicilio.setLocalidad("Suba");
        domicilio.setProvincia("Bogotá");

        paciente.setDomicilio(domicilio);
    }

    @Test
    @DisplayName("Testear que un paciente fue guardado")
    void testPacienteGuardado() throws BadRequestException {
        Paciente pacienteDesdeLaBD = pacienteService.registrarPaciente(paciente);

        assertNotNull(pacienteDesdeLaBD.getId());
        LOGGER.info("Paciente guardado exitosamente en la base de datos: {}" + pacienteDesdeLaBD.getId());
    }

    @Test
    @DisplayName("Testear busqueda paciente por id")
    void testPacientePorId(){
        Integer id = 1;
        Optional<Paciente> pacienteEncontrado = pacienteService.buscarPorId(id);
        Paciente paciente1 = pacienteEncontrado.get();

        assertEquals(id, paciente1.getId());
    }

    @Test
    @DisplayName("Testear busqueda todos los pacientes")
    void testBusquedaTodos() {

        List<Paciente> pacientes = pacienteService.buscarTodos();

        assertTrue(pacientes.size()!=0);
    }
}