package madstodolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import madstodolist.model.Enfermedad;
import madstodolist.model.Tarjeta;
import madstodolist.model.Usuario;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDTO {
    private Long id;
    @Pattern(regexp = "\\d{11}", message = "El NSS debe tener 11 dígitos")
    private String nss;
    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 130, message = "La edad no puede ser mayor a 130")
    private Integer edad;
    private String nombre;
    @NotNull(message = "La enfermedad es obligatoria")
    private Enfermedad enfermedad;
    private Long objetivo;
    private Tarjeta tarjeta;
    private Long usuarioId;
}