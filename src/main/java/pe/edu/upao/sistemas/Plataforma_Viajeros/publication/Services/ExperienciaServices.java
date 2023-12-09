package pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Services;

import org.springframework.stereotype.Service;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.DTO.PublicacionDTO;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Repository.ExperienciaRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Repository.LugarRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Repository.PaisRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.repository.UsuarioRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.models.Usuario;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Experiencia;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Imagen;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Lugar;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Pais;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ExperienciaServices {

    private final ExperienciaRepository experienciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LugarRepository lugarRepository;

    private final PaisRepository paisRepository;

    public ExperienciaServices(ExperienciaRepository experienciaRepository, UsuarioRepository usuarioRepository, LugarRepository lugarRepository, PaisRepository paisRepository){
        this.experienciaRepository = experienciaRepository;
        this.usuarioRepository = usuarioRepository;
        this.lugarRepository = lugarRepository;
        this.paisRepository = paisRepository;
    }

    public Experiencia crearDesdeDTO(PublicacionDTO publicacionDTO, Usuario usuario){
        Experiencia experiencia = new Experiencia();
        experiencia.setTitulo(publicacionDTO.getTitulo());
        experiencia.setTipo(publicacionDTO.getTipo());
        experiencia.setDescripcion(publicacionDTO.getDescripcion());
        experiencia.setUsuario(usuario);

        Lugar lugar = lugarRepository.findByNombre(publicacionDTO.getLugar())
                .orElseGet(() -> {
                    Lugar nuevoLugar = new Lugar();
                    nuevoLugar.setNombre(publicacionDTO.getLugar());
                    return lugarRepository.save(nuevoLugar);
                });
        experiencia.setLugar(lugar);
        try {
            experiencia.setImagen(publicacionDTO.getImagen().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        experiencia.setFecha(LocalDate.from(LocalDateTime.now()));
        experiencia.setHora(LocalTime.from(LocalDateTime.now()));


        return experienciaRepository.save(experiencia);
    }
}
