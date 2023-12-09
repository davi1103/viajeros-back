package pe.edu.upao.sistemas.Plataforma_Viajeros.publication.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicacionDTO {

    private String tipo; // 'experiencia', 'itinerario', 'recomendacion'

    private String titulo;

    private String descripcion;

    private String lugar;

    private MultipartFile imagen;



}