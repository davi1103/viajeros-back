package pe.edu.upao.sistemas.Plataforma_Viajeros.publication.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienciaDTO {

    private String nombreUsuario;
    private String titulo;
    private String descripcion;
    private String lugar;
    private LocalDate fechaCreacion;
    private String imagen; // Suponiendo que solo quieres las URLs de las imágenes
}
