package pe.edu.upao.sistemas.Plataforma_Viajeros.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "publicacion")
@Inheritance(strategy = InheritanceType.JOINED) // Utiliza la estrategia JOINED para la herencia
public abstract class Publicacion {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    private String titulo;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; // Referencia a la entidad Usuario

    @Temporal(TemporalType.DATE)
    private LocalDate fecha;

    @Temporal(TemporalType.TIME)
    private LocalTime hora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lugar_id")
    private Lugar lugar; // Referencia a la entidad Lugar

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pais_codigo")
    private Pais pais;

    private int numLikes = 0; // Inicializa numLikes a 0

}