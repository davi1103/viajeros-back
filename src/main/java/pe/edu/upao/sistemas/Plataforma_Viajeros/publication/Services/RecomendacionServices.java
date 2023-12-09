package pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.DTO.PublicacionDTO;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.models.Usuario;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.repository.UsuarioRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Lugar;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Pais;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Recomendacion;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Repository.LugarRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Repository.PaisRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Repository.RecomendacionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class RecomendacionServices {

    private final RecomendacionRepository recomendacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final LugarRepository lugarRepository;
    private final PaisRepository paisRepository;

    public RecomendacionServices(RecomendacionRepository recomendacionRepository, UsuarioRepository usuarioRepository, LugarRepository lugarRepository, PaisRepository paisRepository){
        this.recomendacionRepository = recomendacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.lugarRepository = lugarRepository;
        this.paisRepository = paisRepository;
    }

    @Transactional
    public Recomendacion crearDesdeDTO(PublicacionDTO publicacionDTO, Usuario usuario){
        Recomendacion recomendacion = new Recomendacion();
        recomendacion.setTitulo(publicacionDTO.getTitulo());
        recomendacion.setTipo(publicacionDTO.getTipo());
        recomendacion.setDescripcion(publicacionDTO.getDescripcion());
        recomendacion.setUsuario(usuario);
        Lugar lugar = lugarRepository.findByNombre(publicacionDTO.getLugar())
                .orElseGet(() -> {
                    Lugar nuevoLugar = new Lugar();
                    nuevoLugar.setNombre(publicacionDTO.getLugar());
                    return lugarRepository.save(nuevoLugar);
                });
        recomendacion.setLugar(lugar);
        recomendacion.setFecha(LocalDate.from(LocalDateTime.now()));
        recomendacion.setHora(LocalTime.from(LocalDateTime.now()));

        return recomendacionRepository.save(recomendacion);
    }
}
