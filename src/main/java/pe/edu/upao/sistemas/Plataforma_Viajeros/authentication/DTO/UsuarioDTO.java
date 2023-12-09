package pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Email;


import java.time.LocalDate;


@Data
@AllArgsConstructor

public class UsuarioDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "Los apellidos no pueden estar vacíos")
    private String apellidos;

    @NotBlank(message = "El sexo no puede estar vacío")
    private String Sexo;

    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El país de origen no puede estar vacío")
    private String paisOrigen;

    @NotBlank(message = "El país de residencia no puede estar vacío")
    private String paisVive;

    private String descripcion;

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "Formato de correo electrónico inválido")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @NotBlank(message = "El tipo de viajero no puede estar vacío")
    private String tipoViajero;

    @NotBlank(message = "Si o si tienes que subir una foto de perfil")
    private String fotoPerfilUrl;

    // Asumiendo que quieres permitir que los usuarios puedan o no agregar sus contactos durante el registro
    private String urlFacebook;
    private String urlTwiter;
    private String urlInstagram;
}