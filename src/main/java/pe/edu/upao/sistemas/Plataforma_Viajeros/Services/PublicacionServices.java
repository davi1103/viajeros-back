package pe.edu.upao.sistemas.Plataforma_Viajeros.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upao.sistemas.Plataforma_Viajeros.DTO.ExperienciaDTO;
import pe.edu.upao.sistemas.Plataforma_Viajeros.DTO.ItinerarioDTO;
import pe.edu.upao.sistemas.Plataforma_Viajeros.DTO.RecomendacionDTO;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Exception.EntidadNoEncontradaException;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Models.Experiencia;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Models.Itinerario;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Models.Recomendacion;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Repository.ExperienciaRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Repository.ItinerarioRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Repository.RecomendacionRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Models.Imagen;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Models.Actividad;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacionServices {

    @Autowired
    private ExperienciaRepository experienciaRepository;
    @Autowired
    private RecomendacionRepository recomendacionRepository;
    @Autowired
    private ItinerarioRepository itinerarioRepository;

    public List<Object> obtenerPublicaciones(String tipo) {
        switch (tipo) {
            case "todas":
                List<Object> todas = new ArrayList<>();
                todas.addAll(experienciaRepository.findAll().stream().map(this::convertirAExperienciaDTO).collect(Collectors.toList()));
                todas.addAll(recomendacionRepository.findAll().stream().map(this::convertirARecomendacionDTO).collect(Collectors.toList()));
                todas.addAll(itinerarioRepository.findAll().stream().map(this::convertirAItinerarioDTO).collect(Collectors.toList()));
                return todas;
            case "experiencias":
                return experienciaRepository.findAll().stream().map(this::convertirAExperienciaDTO).collect(Collectors.toList());
            case "recomendaciones":
                return recomendacionRepository.findAll().stream().map(this::convertirARecomendacionDTO).collect(Collectors.toList());
            case "itinerarios":
                return itinerarioRepository.findAll().stream().map(this::convertirAItinerarioDTO).collect(Collectors.toList());
            default:
                throw new EntidadNoEncontradaException("Tipo de publicación desconocido");
        }
    }

    private ExperienciaDTO convertirAExperienciaDTO(Experiencia experiencia) {
        ExperienciaDTO dto = new ExperienciaDTO();
        dto.setId(experiencia.getId());
        dto.setTitulo(experiencia.getTitulo());
        dto.setDescripcion(experiencia.getDescripcion());
        dto.setFecha(experiencia.getFecha());
        dto.setHora(experiencia.getHora());
        dto.setNumLikes(experiencia.getNumLikes());
        // Suponiendo que Lugar y Pais son entidades relacionadas y quieres exponer sus nombres
        dto.setLugar(experiencia.getLugar().getNombre());
        dto.setPais(experiencia.getPais().getNombre());
        // Suponiendo que imagenes es una colección de entidades Imagen y quieres exponer solo las URLs
        dto.setImagenes(experiencia.getImagenes().stream().map(Imagen::getUrl).collect(Collectors.toList()));
        return dto;
    }

    private RecomendacionDTO convertirARecomendacionDTO(Recomendacion recomendacion) {
        RecomendacionDTO dto = new RecomendacionDTO();
        dto.setId(recomendacion.getId());
        dto.setTitulo(recomendacion.getTitulo());
        dto.setDescripcion(recomendacion.getDescripcion());
        dto.setFecha(recomendacion.getFecha());
        dto.setHora(recomendacion.getHora());
        dto.setNumLikes(recomendacion.getNumLikes());
        dto.setLugar(recomendacion.getLugar().getNombre());
        dto.setPais(recomendacion.getPais().getNombre());
        return dto;
    }

    private ItinerarioDTO convertirAItinerarioDTO(Itinerario itinerario) {
        ItinerarioDTO dto = new ItinerarioDTO();
        dto.setId(itinerario.getId());
        dto.setTitulo(itinerario.getTitulo());
        dto.setDescripcion(itinerario.getDescripcion());
        dto.setFechaInicio(itinerario.getFechainicio());
        dto.setFechaFin(itinerario.getFechaFin());
        dto.setDuracion(itinerario.getDuracion());
        dto.setLugar(itinerario.getLugar().getNombre());
        dto.setPais(itinerario.getPais().getNombre());
        // Suponiendo que actividades es una colección de entidades Actividad y quieres exponer solo las descripciones
        dto.setActividades(itinerario.getActividades().stream().map(Actividad::getDescripcion).collect(Collectors.toList()));
        return dto;
    }
}
