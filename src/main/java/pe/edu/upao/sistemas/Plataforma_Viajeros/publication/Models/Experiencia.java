package pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Data
@Table (name = "experiencia")
public class Experiencia extends Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "imagen", columnDefinition = "MEDIUMBLOB")
    private byte[] imagen;

    // Constructor, getters y setters
    public Experiencia() {
        super();
        this.setTipo("experiencia");
    }
}
