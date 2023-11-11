package pe.edu.upao.sistemas.Plataforma_Viajeros.Repository;

import org.springframework.stereotype.Repository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findById(Long id);
}
