package pe.edu.upao.sistemas.Plataforma_Viajeros.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Table(name = "itinerario")
@Entity
@Data
public class Itinerario extends Publicacion{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int duracion;

    @Column(nullable = false)
    private LocalDate fechainicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    @OneToMany(mappedBy = "itinerario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Actividad> actividades = new ArrayList<>();

    public Itinerario() {
        super();
        this.setTipo("itinerario");
    }

}