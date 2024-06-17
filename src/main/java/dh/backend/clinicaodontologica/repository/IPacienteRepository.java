package dh.backend.clinicaodontologica.repository;

import dh.backend.clinicaodontologica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPacienteRepository extends JpaRepository<Paciente, Integer> {
    //Buscar un paciente por dni
    List<Paciente> findByDni(String dni);

    // Listar todos los pacientes cuyo domicilio se encuentre en una provincia en particular
    @Query("SELECT p FROM Paciente p WHERE LOWER(p.domicilio.provincia) = LOWER(:provincia)")
    List<Paciente> findByProvincia(@Param("provincia") String provincia);
}