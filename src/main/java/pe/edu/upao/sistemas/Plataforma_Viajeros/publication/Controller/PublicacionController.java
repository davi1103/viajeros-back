package pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.models.Usuario;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.DTO.PublicacionDTO;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Experiencia;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Itinerario;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.repository.UsuarioRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Recomendacion;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Services.ExperienciaServices;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Services.ItinerarioServices;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Services.PublicacionServices;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Services.RecomendacionServices;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/publicaciones")

public class PublicacionController {

    private final ExperienciaServices experienciaService;
    private final ItinerarioServices itinerarioService;
    private final RecomendacionServices recomendacionService;
    private final PublicacionServices publicacionServices;

    @Autowired
    private UsuarioRepository usuarioRepository;



    @Autowired
    public PublicacionController(ExperienciaServices experienciaService,
                                 ItinerarioServices itinerarioService,
                                 RecomendacionServices recomendacionService,
                                 PublicacionServices publicacionServices) {
        this.experienciaService = experienciaService;
        this.itinerarioService = itinerarioService;
        this.recomendacionService = recomendacionService;
        this.publicacionServices = publicacionServices;
    }

    @PostMapping(value = "/crearPublicacion", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> crearPublicacion(@ModelAttribute PublicacionDTO publicacionDTO, Principal principal) {
        System.out.println(principal.getName());
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        switch (publicacionDTO.getTipo().toLowerCase()) {
            case "experiencia":
                System.out.println("Experiencia");
                Experiencia experiencia = experienciaService.crearDesdeDTO(publicacionDTO, usuario);
                return ResponseEntity.ok("Publicacion creada");
            case "itinerario":
                Itinerario itinerario = itinerarioService.crearDesdeDTO(publicacionDTO, usuario);
                return ResponseEntity.ok("Publicacion creada");
            case "recomendacion":
                Recomendacion recomendacion = recomendacionService.crearDesdeDTO(publicacionDTO, usuario);
                return ResponseEntity.ok("Publicacion creada");
            default:
                return new ResponseEntity<>("Tipo de publicación desconocido", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<?>> obtenerPublicaciones(@RequestParam(name = "tipo", defaultValue = "todas") String tipo) {
        List<?> publicaciones = publicacionServices.obtenerPublicaciones(tipo);
        return ResponseEntity.ok(publicaciones);
    }


}