package pe.edu.upao.sistemas.Plataforma_Viajeros.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Entity
@Data
@AllArgsConstructor

public class Actividad {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "itinerario_id")
    private Itinerario itinerario;


    public Actividad() {

    }

    public Actividad(String descripcion, Itinerario itinerario){
        this.descripcion = descripcion;
        this.itinerario = itinerario;
    }
}