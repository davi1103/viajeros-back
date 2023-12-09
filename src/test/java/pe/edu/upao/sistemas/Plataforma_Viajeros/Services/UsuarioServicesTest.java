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
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.DTO.UsuarioDTO;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Exception.ContrasenaInconrrectaException;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Exception.EntidadNoEncontradaException;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Exception.UsuarioYaExisteException;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Pais;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.models.Usuario;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Repository.PaisRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.repository.UsuarioRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.services.UsuarioServices;

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

    private UsuarioDTO usuarioDTO;
    private Usuario usuario;
    private Pais paisOrigen;
    private Pais paisVive;

    @BeforeEach
    void setUp() {
        // Inicializar datos de prueba
        usuarioDTO = new UsuarioDTO(
                "Juan", "Perez", "MASCULINO", LocalDate.of(1990, 1, 1),
                "Peru", "Peru", "Desc", "juan@example.com", "pass123", "NOVATO",
                "http://link-to-profile", "http://facebook.com/juan",
                "http://twitter.com/juan", "http://instagram.com/juan"
        );

        usuario = new Usuario();
        paisOrigen = new Pais("PE", "Peru");
        paisVive = new Pais("PE", "Peru");
        usuario.setId(1L); // Suponiendo que el ID es un Long
        usuario.setEmail("usuario@test.com");
        usuario.setPassword("contrasenaCorrecta");

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
        Usuario result = usuarioService.registrarUsuario(usuarioDTO);

        // Assert
        assertNotNull(result);
        verify(usuarioRepository).save(any(Usuario.class));

    }

    @Test
    void cuandoElCorreoYaExisteDeberiaLanzarExcepcion() {
        // Configurar el comportamiento simulado para cuando el correo ya existe
        when(usuarioRepository.findByEmail(usuarioDTO.getEmail())).thenReturn(Optional.of(new Usuario()));

        Exception exception = assertThrows(UsuarioYaExisteException.class, () -> {
            usuarioService.registrarUsuario(usuarioDTO);
        });

        String expectedMessage = "El correo indicado ya está registrado";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    ///////****INICIAR SESIÓN**////////

    @Test
    void inicioDeSesionExitoso() {

        when(usuarioRepository.findByEmail("usuario@test.com")).thenReturn(Optional.of(usuario));

        Optional<String> resultado = usuarioService.validarUsuario("usuario@test.com", "contrasenaCorrecta");

        assertFalse(resultado.isPresent());

        verify(usuarioRepository).findByEmail("usuario@test.com");
    }


    @Test
    void cuandoCorreoNoExiste() {
        // Dado (Given)
        String correoInexistente = "correo@inexistente.com";
        when(usuarioRepository.findByEmail(correoInexistente)).thenReturn(Optional.empty());

        EntidadNoEncontradaException thrown = assertThrows(
                EntidadNoEncontradaException.class,
                () -> usuarioService.validarUsuario(correoInexistente, "contrasenaCualquiera"),
                "Se esperaba que validarUsuario lanzara una EntidadNoEncontradaException"
        );

        assertEquals("Correo no encontrado.", thrown.getMessage());
    }


    @Test
    void cuandoContrasenaEsIncorrecta() {

        String correoExistente = "usuario@ejemplo.com";
        String contrasenaIncorrecta = "contrasenaErronea";
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getPassword()).thenReturn("contrasenaCorrecta");
        when(usuarioRepository.findByEmail(correoExistente)).thenReturn(Optional.of(usuarioMock));


        ContrasenaInconrrectaException thrown = assertThrows(
                ContrasenaInconrrectaException.class,
                () -> usuarioService.validarUsuario(correoExistente, contrasenaIncorrecta),
                "Se esperaba que validarUsuario lanzara una ContrasenaIncorrectaException"
        );


        assertEquals("Contraseña incorrecta.", thrown.getMessage());
    }

}