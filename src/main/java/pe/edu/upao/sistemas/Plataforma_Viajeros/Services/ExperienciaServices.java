package pe.edu.upao.sistemas.Plataforma_Viajeros.Services;

import org.springframework.stereotype.Service;
import pe.edu.upao.sistemas.Plataforma_Viajeros.DTO.PublicacionDTO;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Models.*;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Repository.ExperienciaRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Repository.LugarRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Repository.PaisRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Repository.UsuarioRepository;

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

    public Experiencia crearDesdeDTO(PublicacionDTO publicacionDTO){
        Experiencia experiencia = new Experiencia();
        experiencia.setTitulo(publicacionDTO.getTitulo());
        experiencia.setTipo(publicacionDTO.getTipo());
        experiencia.setDescripcion(publicacionDTO.getDescripcion());

        Usuario usuario = usuarioRepository.findById(publicacionDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        experiencia.setUsuario(usuario);

        Lugar lugar = lugarRepository.findByNombre(publicacionDTO.getLugar())
                .orElseGet(() -> {
                    Lugar nuevoLugar = new Lugar();
                    nuevoLugar.setNombre(publicacionDTO.getLugar());
                    return lugarRepository.save(nuevoLugar);
                });
        experiencia.setLugar(lugar);

        Pais pais = paisRepository.findByNombreIgnoreCase(publicacionDTO.getNombrePais())
                .orElseThrow(() -> new RuntimeException("Pais no encontrado"));
        experiencia.setPais(pais);

        List<Imagen> imagenes = publicacionDTO.getUrlsImagenes().stream()
                .map(url -> new Imagen(url, experiencia))
                .collect(Collectors.toList());
        experiencia.setImagenes(imagenes);
        experiencia.setFecha(LocalDate.from(LocalDateTime.now()));
        experiencia.setHora(LocalTime.from(LocalDateTime.now()));


        return experienciaRepository.save(experiencia);
    }
}
