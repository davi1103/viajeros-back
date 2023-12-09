package pe.edu.upao.sistemas.Plataforma_Viajeros.authentication.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginRequest {
    private String email;
    private String password;

}


