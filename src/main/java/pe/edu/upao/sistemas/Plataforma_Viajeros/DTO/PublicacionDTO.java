package pe.edu.upao.sistemas.Plataforma_Viajeros.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;



@Getter
@Data
public class PublicacionDTO {

    @NotBlank(message = "El tipo no debe estar vacio")
    private String tipo; // 'experiencia', 'itinerario', 'recomendacion'

    @NotBlank(message = "El titulo no debe estar vacio")
    private String titulo;

    @NotBlank(message = "La descripcion no debe estar vacia")
    private String descripcion;

    @NotBlank(message = "El Id no debe estar vacio")
    private Long usuarioId;

    @NotBlank(message = "El lugar no debe estar vacio")
    private String lugar;

    @NotBlank(message = "El pais no debe estar vacio")
    private String nombrePais;

    private List<String> urlsImagenes;

    private int duracion;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private List<String> actividades;

}