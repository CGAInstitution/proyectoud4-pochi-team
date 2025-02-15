package madstodolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import madstodolist.model.Enfermedad;
import madstodolist.model.Tarjeta;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDTO {
    private Long id;
    private String nss;
    private Integer edad;
    private String nombre;
    private Enfermedad enfermedad;
    private Long objetivo;
    private Tarjeta tarjeta;

}
