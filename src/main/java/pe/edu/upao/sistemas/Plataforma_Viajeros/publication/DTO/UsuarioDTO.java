package pe.edu.upao.sistemas.Plataforma_Viajeros.publication.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
public class UsuarioDTO {

    private long id;
    private String nombre;
    private String apellidos;
    private String sexo;
    private LocalDate fechaNacimiento;
    private String descripcion;
    private String correo;
    private String paisOrigen;
    private String paisVive;
    private String tipoViajero;
    private String fotoPerfilUrl;
    private String urlFacebook;
    private String urlTwitter;
    private String urlInstagram;

    // Getters y setters
    // ...
}