package dh.backend.clinicaodontologica.service;

import dh.backend.clinicaodontologica.entity.Odontologo;
import dh.backend.clinicaodontologica.exception.BadRequestException;
import dh.backend.clinicaodontologica.exception.ResourceNotFoundException;
import dh.backend.clinicaodontologica.service.impl.OdontologoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OdontologoServiceTest {
    private static Logger LOGGER = LoggerFactory.getLogger(OdontologoServiceTest.class);
    @Autowired
    private OdontologoService odontologoService;
    private Odontologo odontologo;

    @BeforeEach
    void setUp(){
        odontologo = new Odontologo();
        odontologo.setNombre("Pepito");
        odontologo.setApellido("Perez");
        odontologo.setMatricula("1234");
    }

    @Test
    @DisplayName("Testear que un odontologo fue guardado")
    void testOdontologoGuardado() throws BadRequestException {
        Odontologo odontologoDesdeLaBd = odontologoService.registarOdontologo(odontologo);

        assertNotNull(odontologoDesdeLaBd);
        LOGGER.info("Odontologo guardado exitosamente en la base de datos: " + odontologoDesdeLaBd.getId());
    }

    @Test
    @DisplayName("Testear busqueda odontologo por id")
    void testOdontologoPorId() throws ResourceNotFoundException {
        Integer id = 1;
        Optional<Odontologo> odontologoEncontrado = odontologoService.buscarUnOdontologo(id);
        Odontologo odontologo1 = odontologoEncontrado.get();

        assertEquals(id, odontologo1.getId());
    }

    @Test
    @DisplayName("Testear busqueda todos los odontologos")
    void testBusquedaTodos() throws ResourceNotFoundException {
        List<Odontologo> odontologos = odontologoService.buscarTodosOdontologos();

        assertNotNull(odontologos);
        assertTrue(odontologos.size() != 0);
    }
}