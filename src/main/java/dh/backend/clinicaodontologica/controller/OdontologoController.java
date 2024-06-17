package dh.backend.clinicaodontologica.controller;

import dh.backend.clinicaodontologica.entity.Odontologo;
import dh.backend.clinicaodontologica.exception.BadRequestException;
import dh.backend.clinicaodontologica.exception.ResourceNotFoundException;
import dh.backend.clinicaodontologica.service.IOdontologoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {

    private IOdontologoService odontologoService;

    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo odontologo) throws BadRequestException {
        Odontologo odontologoARetornar = odontologoService.registarOdontologo(odontologo);
        return ResponseEntity.status(HttpStatus.CREATED).body(odontologoARetornar);
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodos(){
        return ResponseEntity.ok(odontologoService.buscarTodosOdontologos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologoPorId(@PathVariable Integer id){
        Optional<Odontologo> odontologo = odontologoService.buscarUnOdontologo(id);
        if(odontologo.isPresent()){
            Odontologo odontologoARetornar = odontologo.get();
            return ResponseEntity.ok(odontologoARetornar);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping
    public ResponseEntity<String> modificarOdontologo(@RequestBody Odontologo odontologo){
        Optional<Odontologo> odontologoOptional = odontologoService.buscarUnOdontologo(odontologo.getId());
        if(odontologoOptional.isPresent()){
            odontologoService.modificarOdontologo(odontologo);
            return ResponseEntity.ok("{\"message\": \"Odontologo modificado\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarOdontologo(@PathVariable Integer id) throws ResourceNotFoundException {
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok("{\"message\": \"Odontologo eliminado\"}");
    }

    @GetMapping("/apellido/{apellido}")
    public ResponseEntity<List<Odontologo>> buscarPorApellido(@PathVariable String apellido){
        List<Odontologo> ListaOdontologos = odontologoService.buscarPorApellido(apellido);
        if (ListaOdontologos.size() > 0){
            return ResponseEntity.ok(ListaOdontologos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Odontologo>> buscarPorNombre(@PathVariable String nombre){
        return ResponseEntity.ok(odontologoService.buscarPorNombre(nombre));
    }
}