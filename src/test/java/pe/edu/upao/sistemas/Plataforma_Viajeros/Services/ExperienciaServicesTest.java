package pe.edu.upao.sistemas.Plataforma_Viajeros.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.DTO.ExperienciaDTO;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Repository.ExperienciaRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.models.Usuario;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Experiencia;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Imagen;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Lugar;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Pais;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Services.PublicacionServices;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class ExperienciaServicesTest {
    @Mock
    private ExperienciaRepository experienciaRepository;

    @InjectMocks
    private PublicacionServices publicacionServices;

    @BeforeEach
    void setUp(){

    }


    @Test
    void cuandoSeListanExperienciasExitosamente() {

        Experiencia experiencia1 = new Experiencia();
        experiencia1.setId(1L); // Suponiendo que los IDs son generados automáticamente, esto es solo para la prueba
        experiencia1.setTipo("experiencia");
        experiencia1.setTitulo("Mi viaje a Machu Picchu");
        experiencia1.setDescripcion("Fue una experiencia increíble al visitar una de las siete maravillas del mundo moderno.");
        experiencia1.setUsuario(new Usuario(/* datos del usuario */)); // Necesitas crear un Usuario mock o real
        experiencia1.setFecha(LocalDate.now());
        experiencia1.setHora(LocalTime.now());
        experiencia1.setLugar(new Lugar(/* datos del lugar */)); // Necesitas crear un Lugar mock o rea
        experiencia1.setNumLikes(100);
 // Necesitas crear una lista de Imagen

        Experiencia experiencia2 = new Experiencia();
        experiencia2.setId(2L);
        experiencia2.setTipo("experiencia");
        experiencia2.setTitulo("Explorando la selva amazónica");
        experiencia2.setDescripcion("Una aventura selvática, rodeada de naturaleza y vida silvestre.");
        experiencia2.setUsuario(new Usuario(/* datos del usuario */));
        experiencia2.setFecha(LocalDate.now());
        experiencia2.setHora(LocalTime.now());
        experiencia2.setLugar(new Lugar(/* datos del lugar */));

        experiencia2.setNumLikes(150);


        List<Experiencia> experienciasMock = new ArrayList<>();
        experienciasMock.add(experiencia1);
        experienciasMock.add(experiencia2);


        // Configurar el mock del repositorio para devolver la lista de experiencias
        when(experienciaRepository.findAll()).thenReturn(experienciasMock);

        // Invocar el método a probar
        List<?> resultados = publicacionServices.obtenerPublicaciones("experiencias");

        // Verificar el resultado
        assertNotNull(resultados);
        assertEquals(experienciasMock.size(), resultados.size());
        assertTrue(resultados.stream().allMatch(result -> result instanceof ExperienciaDTO));

        // Verificar que se llamó al método findAll() del repositorio de experiencias
        verify(experienciaRepository).findAll();
    }


    @Test
    void cuandoNoExistenExperiencias() {
        // Configurar el mock del repositorio para devolver una lista vacía
        when(experienciaRepository.findAll()).thenReturn(Collections.emptyList());

        // Invocar el método a probar
        List<?> resultados = publicacionServices.obtenerPublicaciones("experiencias");

        // Verificar el resultado
        assertNotNull(resultados);
        assertTrue(resultados.isEmpty());

        // Verificar que se llamó al método findAll() del repositorio de experiencias
        verify(experienciaRepository).findAll();
    }
}
