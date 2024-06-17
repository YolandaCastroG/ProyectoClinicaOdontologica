package dh.backend.clinicaodontologica.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "odontologos")
public class Odontologo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String matricula;
    private String nombre;
    private String apellido;

    @OneToMany(mappedBy = "odontologo", cascade = CascadeType.ALL) //el odontologo de mappedby hace alusión al atributo de turno
    @JsonIgnore
    private Set<Turno> turnoSet = new HashSet<>();
}