package pe.edu.upao.sistemas.Plataforma_Viajeros.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Models.Pais;
import java.util.Optional;

@Repository
public interface PaisRepository extends JpaRepository<Pais, String> {
    Optional<Pais> findBycodigo(String codigo);
    Optional<Pais> findByNombreIgnoreCase(String nombre);

}