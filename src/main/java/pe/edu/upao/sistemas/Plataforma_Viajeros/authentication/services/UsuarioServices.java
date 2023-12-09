package pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.services;

import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.DTO.UsuarioDTO;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Exception.EntidadNoEncontradaException;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Exception.UsuarioYaExisteException;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Pais;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.models.Usuario;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Repository.PaisRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServices {

    private final UsuarioRepository usuarioRepository;
    private final PaisRepository paisRepository;

    @Autowired
    public UsuarioServices(UsuarioRepository usuarioRepository, PaisRepository paisRepository) {
        this.usuarioRepository = usuarioRepository;
        this.paisRepository = paisRepository;
    }

    public Optional<String> validarUsuario(String email, String password) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
        if (usuarioOptional.isEmpty()) {
            return Optional.of("Correo no encontrado.");
        }
        Usuario usuario = usuarioOptional.get();
        if (!usuario.getPassword().equals(password)) {
            return Optional.of("Contraseña incorrecta.");
        }
        return Optional.empty();
    }

    public Optional<Usuario> obtenerUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByEmail(correo);
    }

    public Usuario registrarUsuario(UsuarioDTO usuarioDTO) {

        // Verificar si ya existe un usuario con el mismo correo
        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new UsuarioYaExisteException("El correo indicado ya está registrado");
        }

        // Convertir SignUpDTO a entidad Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellidos(usuarioDTO.getApellidos());
        usuario.setSexo(Usuario.Sexo.valueOf(usuarioDTO.getSexo()));
        usuario.setFechaNacimiento(usuarioDTO.getFechaNacimiento()); // Asegúrate de que el formato de fecha sea el correcto
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(usuarioDTO.getPassword());

        Pais paisOrigen = paisRepository.findByNombreIgnoreCase((usuarioDTO.getPaisOrigen()))
                .orElseThrow(() -> new EntidadNoEncontradaException("País de origen no encontrado"));

        Pais paisVive = paisRepository.findByNombreIgnoreCase((usuarioDTO.getPaisVive()))
                .orElseThrow(() -> new EntidadNoEncontradaException("País de residencia no encontrado"));

        usuario.setPaisOrigen(paisOrigen);
        usuario.setPaisVive(paisVive);
        usuario.setDescripcion(usuarioDTO.getDescripcion());

        usuario.setTipoViajero(Usuario.TipoViajero.valueOf(usuarioDTO.getTipoViajero()));
        usuario.setFotoPerfilUrl(usuarioDTO.getFotoPerfilUrl());
        usuario.setUrlFacebook(usuarioDTO.getUrlFacebook());
        usuario.setUrlInstagram(usuarioDTO.getUrlInstagram());
        usuario.setUrlTwiter(usuarioDTO.getUrlTwiter());

        // Guardar el usuario en la base de datos
        return usuarioRepository.save(usuario);
    }

    public List<pe.edu.upao.sistemas.Plataforma_Viajeros.publication.DTO.UsuarioDTO> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirAUsuarioDTO)
                .collect(Collectors.toList());
    }

    private pe.edu.upao.sistemas.Plataforma_Viajeros.publication.DTO.UsuarioDTO convertirAUsuarioDTO(Usuario usuario) {
        pe.edu.upao.sistemas.Plataforma_Viajeros.publication.DTO.UsuarioDTO dto = new pe.edu.upao.sistemas.Plataforma_Viajeros.publication.DTO.UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellidos(usuario.getApellidos());
        dto.setSexo(usuario.getSexo().toString());
        dto.setFechaNacimiento(usuario.getFechaNacimiento());
        dto.setDescripcion(usuario.getDescripcion());
        dto.setCorreo(usuario.getEmail());
        dto.setPaisOrigen(usuario.getPaisOrigen().getNombre()); // Asumiendo que paisOrigen es una entidad con un campo 'nombre'
        dto.setPaisVive(usuario.getPaisVive().getNombre()); // Similar para paisVive
        dto.setTipoViajero(usuario.getTipoViajero().toString());
        dto.setFotoPerfilUrl(usuario.getFotoPerfilUrl());
        dto.setUrlFacebook(usuario.getUrlFacebook());
        dto.setUrlTwitter(usuario.getUrlTwiter());
        dto.setUrlInstagram(usuario.getUrlInstagram());
        // Asegúrate de que todos los getters y setters correspondientes están definidos en UsuarioDTO
        return dto;
    }


}