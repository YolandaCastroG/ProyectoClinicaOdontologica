package dh.backend.clinicaodontologica.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TurnoModificacionDto {
    private Integer id;
    private Integer paciente_id;
    private Integer odontologo_id;
    private String fecha;
}
