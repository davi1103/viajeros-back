package pe.edu.upao.sistemas.Plataforma_Viajeros.Services;

import pe.edu.upao.sistemas.Plataforma_Viajeros.DTO.SignUpDTO;
import pe.edu.upao.sistemas.Plataforma_Viajeros.DTO.UsuarioDTO;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Exception.EntidadNoEncontradaException;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Exception.UsuarioYaExisteException;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Models.Pais;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Models.Usuario;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Repository.PaisRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Repository.UsuarioRepository;
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

    public Optional<String> validarUsuario(String correo, String contrasena) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByCorreo(correo);
        if (usuarioOptional.isEmpty()) {
            return Optional.of("Correo no encontrado.");
        }
        Usuario usuario = usuarioOptional.get();
        if (!usuario.getContrasena().equals(contrasena)) {
            return Optional.of("Contraseña incorrecta.");
        }
        return Optional.empty();
    }

    public Optional<Usuario> obtenerUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public Usuario registrarUsuario(SignUpDTO signUpDTO) {

        // Verificar si ya existe un usuario con el mismo correo
        if (usuarioRepository.findByCorreo(signUpDTO.getCorreo()).isPresent()) {
            throw new UsuarioYaExisteException("El correo indicado ya está registrado");
        }

        // Convertir SignUpDTO a entidad Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(signUpDTO.getNombre());
        usuario.setApellidos(signUpDTO.getApellidos());
        usuario.setSexo(Usuario.Sexo.valueOf(signUpDTO.getSexo()));
        usuario.setFechaNacimiento(signUpDTO.getFechaNacimiento()); // Asegúrate de que el formato de fecha sea el correcto
        usuario.setCorreo(signUpDTO.getCorreo());
        usuario.setContrasena(signUpDTO.getContrasena());

        Pais paisOrigen = paisRepository.findByNombreIgnoreCase((signUpDTO.getPaisOrigen()))
                .orElseThrow(() -> new EntidadNoEncontradaException("País de origen no encontrado"));

        Pais paisVive = paisRepository.findByNombreIgnoreCase((signUpDTO.getPaisVive()))
                .orElseThrow(() -> new EntidadNoEncontradaException("País de residencia no encontrado"));

        usuario.setPaisOrigen(paisOrigen);
        usuario.setPaisVive(paisVive);
        usuario.setDescripcion(signUpDTO.getDescripcion());

        usuario.setTipoViajero(Usuario.TipoViajero.valueOf(signUpDTO.getTipoViajero()));
        usuario.setFotoPerfilUrl(signUpDTO.getFotoPerfilUrl());
        usuario.setUrlFacebook(signUpDTO.getUrlFacebook());
        usuario.setUrlInstagram(signUpDTO.getUrlInstagram());
        usuario.setUrlTwiter(signUpDTO.getUrlTwiter());

        // Guardar el usuario en la base de datos
        return usuarioRepository.save(usuario);
    }

    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirAUsuarioDTO)
                .collect(Collectors.toList());
    }

    private UsuarioDTO convertirAUsuarioDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellidos(usuario.getApellidos());
        dto.setSexo(usuario.getSexo().toString());
        dto.setFechaNacimiento(usuario.getFechaNacimiento());
        dto.setDescripcion(usuario.getDescripcion());
        dto.setCorreo(usuario.getCorreo());
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