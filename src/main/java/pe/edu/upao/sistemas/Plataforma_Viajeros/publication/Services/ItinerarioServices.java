package pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Services;

import org.springframework.stereotype.Service;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.DTO.PublicacionDTO;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Repository.ItinerarioRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Repository.LugarRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Repository.PaisRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.repository.UsuarioRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.models.Usuario;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Actividad;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Itinerario;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Lugar;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Pais;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItinerarioServices {

    private final ItinerarioRepository itinerarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final LugarRepository lugarRepository;

    private final PaisRepository paisRepository;

    public ItinerarioServices(ItinerarioRepository itinerarioRepository, UsuarioRepository usuarioRepository, LugarRepository lugarRepository, PaisRepository paisRepository){
        this.itinerarioRepository = itinerarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.lugarRepository = lugarRepository;
        this.paisRepository = paisRepository;
    }

    public Itinerario crearDesdeDTO(PublicacionDTO publicacionDTO, Usuario usuario){
        Itinerario itinerario = new Itinerario();
        itinerario.setTitulo(publicacionDTO.getTitulo());
        itinerario.setDescripcion(publicacionDTO.getDescripcion());
        itinerario.setTipo(publicacionDTO.getTipo());

        itinerario.setUsuario(usuario);

        Lugar lugar = lugarRepository.findByNombre(publicacionDTO.getLugar())
                .orElseGet(() -> {
                    Lugar nuevoLugar = new Lugar();
                    nuevoLugar.setNombre(publicacionDTO.getLugar());
                    return lugarRepository.save(nuevoLugar);
                });
        itinerario.setLugar(lugar);
        itinerario.setFecha(LocalDate.from(LocalDateTime.now()));
        itinerario.setHora(LocalTime.from(LocalDateTime.now()));

        return itinerarioRepository.save(itinerario);
    }
}
