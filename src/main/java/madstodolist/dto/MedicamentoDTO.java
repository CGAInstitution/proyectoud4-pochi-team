package madstodolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import madstodolist.model.Enfermedad;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private int precio;
    private boolean receta;
    private Set<Enfermedad> enfermedades;

}
