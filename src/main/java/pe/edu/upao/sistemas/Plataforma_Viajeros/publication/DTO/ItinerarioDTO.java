package pe.edu.upao.sistemas.Plataforma_Viajeros.publication.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class ItinerarioDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int duracion;
    private String lugar;
    private String pais;
    private List<String> actividades; // Suponiendo que solo quieres las descripciones de las actividades
}
