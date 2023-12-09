package pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.DTO.LoginRequest;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.DTO.LoginResponse;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.DTO.UsuarioDTO;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.models.Usuario;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.util.JwtTokenUtil;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Exception.EntidadNoEncontradaException;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Exception.UsuarioYaExisteException;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.repository.UsuarioRepository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.services.UsuarioServices;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UsuarioServices usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public AuthController(UsuarioServices usuarioService, UsuarioRepository usuarioRepository, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    LoginResponse login(@RequestBody LoginRequest loginRequest) throws Exception{
        Optional<Usuario> user = usuarioRepository.findByEmail(loginRequest.getEmail());
        if(user.isPresent()){
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
                return new LoginResponse(jwtTokenUtil.generateToken(user.get()));
            }catch (AuthenticationException e){
                //pass to the throw.
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Correo y/o contraseña incorrecta");
    }




    @PostMapping("/registrar")
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            usuarioService.registrarUsuario(usuarioDTO);
            return ResponseEntity.ok("Usuario registrado con éxito");
        } catch (UsuarioYaExisteException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntidadNoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}