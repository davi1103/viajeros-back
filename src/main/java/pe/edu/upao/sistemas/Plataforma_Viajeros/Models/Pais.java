package pe.edu.upao.sistemas.Plataforma_Viajeros.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Table(name = "pais")
public class Pais {

    @Id
    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String nombre;


    public Pais() {

    }
}
