package pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "recomendacion")
@Entity
public class Recomendacion extends Publicacion{

    public Recomendacion() {
        super();
        this.setTipo("recomendacion");
    }
}

