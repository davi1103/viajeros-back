package pe.edu.upao.sistemas.Plataforma_Viajeros.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.DTO.RecomendacionDTO;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Repository.RecomendacionRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.models.Usuario;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Lugar;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Pais;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Recomendacion;
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
public class RecomendacionServices {

    @Mock
    private RecomendacionRepository recomendacionRepository;

    @InjectMocks
    private PublicacionServices publicacionServices;

    @BeforeEach
    void setUp(){

    }


    @Test
    void cuandoSeListanRecomendaionesExitosamente() {

        Recomendacion recomendacion1 = new Recomendacion();
        recomendacion1.setId(1L); // Suponiendo que los IDs son generados automáticamente, esto es solo para la prueba
        recomendacion1.setTipo("recomendacion");
        recomendacion1.setTitulo("Recomendacion paraa Machu Picchu");
        recomendacion1.setDescripcion("Recomendacionexample");
        recomendacion1.setUsuario(new Usuario());
        recomendacion1.setFecha(LocalDate.now());
        recomendacion1.setHora(LocalTime.now());
        recomendacion1.setLugar(new Lugar());
        recomendacion1.setNumLikes(100);


        Recomendacion recomendacion2 = new Recomendacion();
        recomendacion2.setId(2L);
        recomendacion2.setTipo("recomendacion");
        recomendacion2.setTitulo("Explorando la selva amazónica");
        recomendacion2.setDescripcion("recomendacionexample2");
        recomendacion2.setUsuario(new Usuario());
        recomendacion2.setFecha(LocalDate.now());
        recomendacion2.setHora(LocalTime.now());
        recomendacion2.setLugar(new Lugar());
        recomendacion2.setNumLikes(150);


        List<Recomendacion> recomendacionesMock = new ArrayList<>();
        recomendacionesMock.add(recomendacion1);
        recomendacionesMock.add(recomendacion2);


        // Configurar el mock del repositorio para devolver la lista de experiencias
        when(recomendacionRepository.findAll()).thenReturn(recomendacionesMock);

        // Invocar el método a probar
        List<?> resultados = publicacionServices.obtenerPublicaciones("recomendaciones");

        // Verificar el resultado
        assertNotNull(resultados);
        assertEquals(recomendacionesMock.size(), resultados.size());
        assertTrue(resultados.stream().allMatch(result -> result instanceof RecomendacionDTO));

        // Verificar que se llamó al método findAll() del repositorio de experiencias
        verify(recomendacionRepository).findAll();
    }


    @Test
    void cuandoNoExistenRecomendaciones() {
        // Configurar el mock del repositorio para devolver una lista vacía
        when(recomendacionRepository.findAll()).thenReturn(Collections.emptyList());

        // Invocar el método a probar
        List<?> resultados = publicacionServices.obtenerPublicaciones("recomendaciones");

        // Verificar el resultado
        assertNotNull(resultados);
        assertTrue(resultados.isEmpty());

        // Verificar que se llamó al método findAll() del repositorio de experiencias
        verify(recomendacionRepository).findAll();
    }
}
