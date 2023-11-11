package pe.edu.upao.sistemas.Plataforma_Viajeros.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.Models.Itinerario;

@Repository
public interface ItinerarioRepository extends JpaRepository<Itinerario, Long> {
    // Métodos de consulta específicos para Itinerario
}