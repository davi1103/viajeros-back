package pe.edu.upao.sistemas.Plataforma_Viajeros.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "experiencia_id")
    private Experiencia experiencia; // Referencia a la experiencia asociada

    // Constructor por defecto necesario para JPA
    public Imagen() {
    }

    public Imagen(String url, Experiencia experiencia) {
        this.url = url;
        this.experiencia = experiencia;
    }
}