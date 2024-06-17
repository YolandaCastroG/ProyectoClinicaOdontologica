package dh.backend.clinicaodontologica.service.impl;

import dh.backend.clinicaodontologica.dto.request.TurnoModificacionDto;
import dh.backend.clinicaodontologica.dto.request.TurnoRequestDto;
import dh.backend.clinicaodontologica.dto.response.OdontologoResponseDto;
import dh.backend.clinicaodontologica.dto.response.PacienteResponseDto;
import dh.backend.clinicaodontologica.dto.response.TurnoResponseDto;
import dh.backend.clinicaodontologica.entity.Odontologo;
import dh.backend.clinicaodontologica.entity.Paciente;
import dh.backend.clinicaodontologica.entity.Turno;
import dh.backend.clinicaodontologica.exception.BadRequestException;
import dh.backend.clinicaodontologica.exception.ResourceNotFoundException;
import dh.backend.clinicaodontologica.repository.IOdontologoRepository;
import dh.backend.clinicaodontologica.repository.IPacienteRepository;
import dh.backend.clinicaodontologica.repository.ITurnoRepository;
import dh.backend.clinicaodontologica.service.IPacienteService;
import dh.backend.clinicaodontologica.service.ITurnoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {
    private static Logger LOGGER = LoggerFactory.getLogger(IPacienteService.class);
    private IOdontologoRepository odontologoRepository;
    private IPacienteRepository pacienteRepository;
    private ITurnoRepository turnoRepository;
    private ModelMapper modelMapper;

    public TurnoService(IOdontologoRepository odontologoRepository, IPacienteRepository pacienteRepository, ITurnoRepository turnoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.pacienteRepository = pacienteRepository;
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TurnoResponseDto registrar(TurnoRequestDto turnoRequestDto) throws BadRequestException {
        LOGGER.info("Intentando registrar turno con datos: {}", turnoRequestDto);

        Optional<Paciente> paciente = pacienteRepository.findById(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoRepository.findById(turnoRequestDto.getOdontologo_id());
        Turno turnoARegistrar = new Turno();
        Turno turnoGuardado = null;
        TurnoResponseDto turnoADevolver = null;
        if (paciente.isEmpty() || odontologo.isEmpty()) {
            throw new BadRequestException("{\"message\": \"Paciente u odontologo no encontrados\"}");
        } else if (paciente.isEmpty()){
            throw new BadRequestException("{\"message\": \"Paciente no existe\"}");
        } else if (paciente.isEmpty()) {
            throw new BadRequestException("{\"message\": \"Paciente no existe\"}");
        }else {
            turnoARegistrar.setOdontologo(odontologo.get());
            turnoARegistrar.setPaciente(paciente.get());
            turnoARegistrar.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            turnoGuardado = turnoRepository.save(turnoARegistrar);
            turnoADevolver = mapToResponseDto(turnoGuardado);
            LOGGER.info("Turno registrado con éxito: {}", turnoADevolver);
            return turnoADevolver;
        }
    }

    @Override
    public TurnoResponseDto buscarPorId(Integer id) {
        LOGGER.info("Buscando turno con ID: {}", id);
        Optional<Turno> turnoOptional = turnoRepository.findById(id);
        if (turnoOptional.isPresent()) {
            Turno turnoEncontrado = turnoOptional.get();
            TurnoResponseDto turnoADevolver = mapToResponseDto(turnoEncontrado);
            LOGGER.info("Turno encontrado: {}", turnoADevolver);
            return turnoADevolver;
        }
        LOGGER.warn("Turno con ID: {} no encontrado", id);
        return null;
    }

    @Override
    public List<TurnoResponseDto> buscarTodos() {
        LOGGER.info("Buscando todos los turnos");
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoResponseDto> turnosADevolver = new ArrayList<>();
        TurnoResponseDto turnoAuxiliar = null;
        for (Turno turno : turnos) {
            turnoAuxiliar = mapToResponseDto(turno);
            turnosADevolver.add(turnoAuxiliar);
        }
        LOGGER.info("Se encontraron {} turnos", turnosADevolver.size());
        return turnosADevolver;
    }

    @Override
    public void actualizarTurno(TurnoModificacionDto modificacionDto) throws ResourceNotFoundException {
        LOGGER.info("Intentando actualizar turno con ID: {}", modificacionDto.getId());
        Optional<Paciente> paciente = pacienteRepository.findById(modificacionDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoRepository.findById(modificacionDto.getOdontologo_id());
        Optional<Turno> turno = turnoRepository.findById(modificacionDto.getId());
        Turno turnoAModificar = new Turno();
        if (paciente.isPresent() && odontologo.isPresent() && turno.isPresent()) {
            turnoAModificar.setId(modificacionDto.getId());
            turnoAModificar.setOdontologo(odontologo.get());
            turnoAModificar.setPaciente(paciente.get());
            turnoAModificar.setFecha(LocalDate.parse(modificacionDto.getFecha()));
            turnoRepository.save(turnoAModificar);
            LOGGER.info("Turno actualizado con éxito");
        }
        else {
            throw new ResourceNotFoundException("{\"message\": \"Datos incorrectos o turno no encontrado\"}");
        }
}

    @Override
    public void eliminarTurno(Integer id) throws ResourceNotFoundException {
        LOGGER.info("Intentando eliminar turno con ID: {}", id);
        TurnoResponseDto turnoResponseDto = buscarPorId(id);
        if (turnoResponseDto != null) {
            turnoRepository.deleteById(id);
            LOGGER.info("Turno con ID: {} eliminado con éxito", id);
        }else {
            throw new ResourceNotFoundException("{\"message\": \"Turno no encontrado\"}");
        }
    }

    @Override
    public List<TurnoResponseDto> buscarTurnoEntreFechas(LocalDate startDate, LocalDate endDate) {
        LOGGER.info("Buscando turnos entre fechas: {} y {}", startDate, endDate);
        List<Turno> listadoTurnos = turnoRepository.buscarTurnoEntreFechas(startDate, endDate);
        List<TurnoResponseDto> listadoARetornar = new ArrayList<>();
        TurnoResponseDto turnoAuxiliar = null;
        for (Turno turno: listadoTurnos){
            turnoAuxiliar = mapToResponseDto(turno);
            listadoARetornar.add(turnoAuxiliar);
        }
        LOGGER.info("Se encontraron {} turnos entre las fechas: {} y {}", listadoARetornar.size(), startDate, endDate);
        return listadoARetornar;
    }

    @Override
    public List<TurnoResponseDto> listarTurnosPosterioresAFechaActual() {
        LOGGER.info("Listando turnos posteriores a la fecha actual");
        List<Turno> listadoTurnos = turnoRepository.listarTurnosPosterioresAFechaActual();
        List<TurnoResponseDto> listadoARetornar = new ArrayList<>();
        for (Turno turno : listadoTurnos) {
            TurnoResponseDto turnoAuxiliar = mapToResponseDto(turno);
            listadoARetornar.add(turnoAuxiliar);
        }
        LOGGER.info("Se encontraron {} turnos posteriores a la fecha actual", listadoARetornar.size());
        return listadoARetornar;
    }

    //Método que mapea turno en TurnoResponseDto
    private TurnoResponseDto mapToResponseDto(Turno turno){
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setOdontologo(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        turnoResponseDto.setPaciente(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        return turnoResponseDto;
    }

}