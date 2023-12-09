package pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Experiencia;
@Repository
public interface ExperienciaRepository extends JpaRepository<Experiencia, Long> {
    // Métodos de consulta específicos para Experiencia
}