package pe.edu.upao.sistemas.Plataforma_Viajeros.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ExperienciaDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDate fecha;
    private LocalTime hora;
    private int numLikes;
    private String lugar;
    private String pais;
    private List<String> imagenes; // Suponiendo que solo quieres las URLs de las imágenes
}
