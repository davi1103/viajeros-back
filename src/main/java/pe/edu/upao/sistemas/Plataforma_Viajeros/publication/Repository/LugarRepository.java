package pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Lugar;

import java.util.Optional;

@Repository
public interface LugarRepository extends JpaRepository<Lugar, Long> {
    Optional<Lugar> findByNombre(String nombre);
    // Otros métodos de consulta para Lugar
}