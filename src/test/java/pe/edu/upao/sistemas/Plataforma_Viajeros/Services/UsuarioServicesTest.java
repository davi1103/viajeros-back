package pe.edu.upao.sistemas.Plataforma_Viajeros.Services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pe.edu.upao.sistemas.Plataforma_Viajeros.DTO.SignUpDTO;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Exception.ContrasenaInconrrectaException;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Exception.EntidadNoEncontradaException;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Exception.UsuarioYaExisteException;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Models.Pais;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Models.Usuario;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Repository.PaisRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Repository.UsuarioRepository;

import java.time.LocalDate;
import java.util.Optional;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class UsuarioServicesTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PaisRepository paisRepository;

    @InjectMocks
    private UsuarioServices usuarioService;

    private SignUpDTO signUpDTO;
    private Usuario usuario;
    private Pais paisOrigen;
    private Pais paisVive;

    @BeforeEach
    void setUp() {
        // Inicializar datos de prueba
        signUpDTO = new SignUpDTO(
                "Juan", "Perez", "MASCULINO", LocalDate.of(1990, 1, 1),
                "Peru", "Peru", "Desc", "juan@example.com", "pass123", "NOVATO",
                "http://link-to-profile", "http://facebook.com/juan",
                "http://twitter.com/juan", "http://instagram.com/juan"
        );

        usuario = new Usuario();
        paisOrigen = new Pais("PE", "Peru");
        paisVive = new Pais("PE", "Peru");
        usuario.setId(1L); // Suponiendo que el ID es un Long
        usuario.setCorreo("usuario@test.com");
        usuario.setContrasena("contrasenaCorrecta");

        // Configurar el comportamiento simulado (mock)
        when(paisRepository.findByNombreIgnoreCase("Peru")).thenReturn(Optional.of(paisOrigen));
        when(paisRepository.findByNombreIgnoreCase("Peru")).thenReturn(Optional.of(paisVive));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
    }


    ///////****REGISTRAR USUARIO**////////

    @Test
    void cuandoSeRegistraNuevoUsuarioExitosamente() {
        // Configurar más comportamiento simulado si es necesario

        // Act
        Usuario result = usuarioService.registrarUsuario(signUpDTO);

        // Assert
        assertNotNull(result);
        verify(usuarioRepository).save(any(Usuario.class));

    }

    @Test
    void cuandoElCorreoYaExisteDeberiaLanzarExcepcion() {
        // Configurar el comportamiento simulado para cuando el correo ya existe
        when(usuarioRepository.findByCorreo(signUpDTO.getCorreo())).thenReturn(Optional.of(new Usuario()));

        // Ejecutar la acción y verificar que se lanza la excepción
        Exception exception = assertThrows(UsuarioYaExisteException.class, () -> {
            usuarioService.registrarUsuario(signUpDTO);
        });

        // Verificar el mensaje de la excepción
        String expectedMessage = "El correo indicado ya está registrado";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    ///////****INICIAR SESIÓN**////////

    @Test
    void inicioDeSesionExitoso() {
        // Configurar comportamiento simulado para encontrar al usuario por correo
        when(usuarioRepository.findByCorreo("usuario@test.com")).thenReturn(Optional.of(usuario));

        // Actuar: Intentar iniciar sesión con credenciales correctas
        Optional<String> resultado = usuarioService.validarUsuario("usuario@test.com", "contrasenaCorrecta");

        // Afirmar: Verificar que el resultado está vacío, lo que indica un inicio de sesión exitoso
        assertFalse(resultado.isPresent());

        // Verificar que se llamó al método findByCorreo con el correo correcto
        verify(usuarioRepository).findByCorreo("usuario@test.com");
    }


    @Test
    void cuandoCorreoNoExiste() {
        // Dado (Given)
        String correoInexistente = "correo@inexistente.com";
        when(usuarioRepository.findByCorreo(correoInexistente)).thenReturn(Optional.empty());

        EntidadNoEncontradaException thrown = assertThrows(
                EntidadNoEncontradaException.class,
                () -> usuarioService.validarUsuario(correoInexistente, "contrasenaCualquiera"),
                "Se esperaba que validarUsuario lanzara una EntidadNoEncontradaException"
        );

        assertEquals("Correo no encontrado.", thrown.getMessage());
    }


    @Test
    void cuandoContrasenaEsIncorrecta() {
        // Dado (Given)
        String correoExistente = "usuario@ejemplo.com";
        String contrasenaIncorrecta = "contrasenaErronea";
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getContrasena()).thenReturn("contrasenaCorrecta");
        when(usuarioRepository.findByCorreo(correoExistente)).thenReturn(Optional.of(usuarioMock));

        // Cuando (When) y Entonces (Then)
        ContrasenaInconrrectaException thrown = assertThrows(
                ContrasenaInconrrectaException.class,
                () -> usuarioService.validarUsuario(correoExistente, contrasenaIncorrecta),
                "Se esperaba que validarUsuario lanzara una ContrasenaIncorrectaException"
        );

        // Afirmaciones adicionales (Additional Assertions)
        assertEquals("Contraseña incorrecta.", thrown.getMessage());
    }

}