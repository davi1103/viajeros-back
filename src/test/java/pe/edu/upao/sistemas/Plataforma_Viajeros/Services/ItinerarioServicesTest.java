package pe.edu.upao.sistemas.Plataforma_Viajeros.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.DTO.ItinerarioDTO;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Repository.ItinerarioRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.models.Usuario;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Actividad;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Itinerario;
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
public class ItinerarioServicesTest {

    @Mock
    private ItinerarioRepository itinerarioRepository;

    @InjectMocks
    private PublicacionServices publicacionServices;

    @BeforeEach
    void setUp(){

    }


    @Test
    void cuandoSeListanItinerariosExitosamente() {

        Itinerario itinerario1 = new Itinerario();
        itinerario1.setId(1L); // Suponiendo que los IDs son generados automáticamente, esto es solo para la prueba
        itinerario1.setTipo("itinerario");
        itinerario1.setTitulo("Mi viaje a Machu Picchu");
        itinerario1.setDescripcion("Fue una experiencia increíble al visitar una de las siete maravillas del mundo moderno.");
        itinerario1.setUsuario(new Usuario(/* datos del usuario */)); // Necesitas crear un Usuario mock o real
        itinerario1.setFecha(LocalDate.now());
        itinerario1.setHora(LocalTime.now());
        itinerario1.setLugar(new Lugar(/* datos del lugar */)); // Necesitas crear un Lugar mock o real

        itinerario1.setNumLikes(100);
        itinerario1.setActividades(List.of(new Actividad(/* datos de la imagen */))); // Necesitas crear una lista de Imagen

        Itinerario itinerario2 = new Itinerario();
        itinerario2.setId(1L); //
        itinerario2.setTipo("itinerario");
        itinerario2.setTitulo("Mi viaje a Machu Picchu");
        itinerario2.setDescripcion("prueba");
        itinerario2.setUsuario(new Usuario(/* datos del usuario */));
        itinerario2.setFecha(LocalDate.now());
        itinerario2.setHora(LocalTime.now());
        itinerario2.setLugar(new Lugar(/* datos del lugar */));
        itinerario2.setNumLikes(100);
        itinerario2.setActividades(List.of(new Actividad(/* */)));


        List<Itinerario> itinerariosMock = new ArrayList<>();
        itinerariosMock.add(itinerario1);
        itinerariosMock.add(itinerario2);


        // Configurar el mock del repositorio para devolver la lista de experiencias
        when(itinerarioRepository.findAll()).thenReturn(itinerariosMock);

        // Invocar el método a probar
        List<?> resultados = publicacionServices.obtenerPublicaciones("itinerarios");

        // Verificar el resultado
        assertNotNull(resultados);
        assertEquals(itinerariosMock.size(), resultados.size());
        assertTrue(resultados.stream().allMatch(result -> result instanceof ItinerarioDTO));

        // Verificar que se llamó al método findAll() del repositorio de experiencias
        verify(itinerarioRepository).findAll();
    }


    @Test
    void cuandoNoExistenItinerarios() {
        // Configurar el mock del repositorio para devolver una lista vacía
        when(itinerarioRepository.findAll()).thenReturn(Collections.emptyList());

        // Invocar el método a probar
        List<?> resultados = publicacionServices.obtenerPublicaciones("itinerarios");

        // Verificar el resultado
        assertNotNull(resultados);
        assertTrue(resultados.isEmpty());

        // Verificar que se llamó al método findAll() del repositorio de experiencias
        verify(itinerarioRepository).findAll();
    }
}
