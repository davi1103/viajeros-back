package pe.edu.upao.sistemas.Plataforma_Viajeros.Services;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;

import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Controller.PublicacionController;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.DTO.PublicacionDTO;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Experiencia;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Services.ExperienciaServices;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Services.ItinerarioServices;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Services.PublicacionServices;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Services.RecomendacionServices;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class PublicacionServicesTest {

    @Mock
    private ExperienciaServices experienciaService;

    @Mock
    private ItinerarioServices itinerarioService;

    @Mock
    private RecomendacionServices recomendacionService;

    @Mock
    private PublicacionServices publicacionServices;

    @InjectMocks
    private PublicacionController publicacionController;

    private PublicacionDTO publicacionDTO;

    @BeforeEach
    void setUp() {
        publicacionDTO = new PublicacionDTO();
        publicacionDTO.setTipo("experiencia");
        publicacionDTO.setTitulo("Titulo de prueba");
        publicacionDTO.setDescripcion("Descripcion de prueba");


    }



}