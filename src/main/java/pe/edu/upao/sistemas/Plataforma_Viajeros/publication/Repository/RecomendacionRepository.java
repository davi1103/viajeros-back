package pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Recomendacion;

@Repository
public interface RecomendacionRepository extends JpaRepository<Recomendacion, Long> {
    // Métodos de consulta específicos para Recomendacion
}