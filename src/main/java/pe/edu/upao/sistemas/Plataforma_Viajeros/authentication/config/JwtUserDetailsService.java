package pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.models.Usuario;
import pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.repository.UsuarioRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class JwtUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);
    private final UsuarioRepository userRepository;

    public JwtUserDetailsService(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.debug("---loadUserByUsername called.---");
        Optional<Usuario> user = userRepository.findByEmail(email);
        if(user.isPresent()) {

            return user.get();
            //  return new User(user.get().getEmail(), user.get().getPassword(),true,true,true,true, authorities);
        } else {
            throw new UsernameNotFoundException("User "+email+" not found.");
        }
    }
}