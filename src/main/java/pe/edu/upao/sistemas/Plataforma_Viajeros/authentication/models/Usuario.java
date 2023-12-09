package pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Models.Pais;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "users")

public class Usuario implements UserDetails {
    @Getter

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (nullable = false)
    private String nombre;

    @Column (nullable = false)
    private String apellidos;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private Sexo sexo;

    @Temporal(TemporalType.DATE)
    @Column (nullable = false)
    private LocalDate fechaNacimiento;

    private String descripcion;

    @Column (nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column (nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paisOrigen_codigo", nullable = false)
    @JsonIgnore
    private Pais paisOrigen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paisVive_codigo", nullable = false)
    @JsonIgnore
    private Pais paisVive;

    @Enumerated(EnumType.STRING)
    private TipoViajero tipoViajero;

    private String fotoPerfilUrl;

    //El usuario podra agregar sus contactos(opcional)
    private String urlFacebook;
    private String urlTwiter;
    private String urlInstagram;

    // creacion de los Enum para los atributos que solo cuentan con 3 o pocas opciones

    //Clase Sexo
    public enum Sexo{
        MASCULINO, FEMENINO, OTRO
    }

    //Clase Tipo de viajero

    public enum TipoViajero {
        NOVATO, EXPERTO
    }

    public String getNombreCompleto() {
        return nombre + " " + apellidos; // O cualquier lógica que defina el nombre completo
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Devuelve una lista vacía de GrantedAuthority si no estás utilizando roles o permisos
        return new ArrayList<>();
    }



    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}