package pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Exception;

public class UsuarioYaExisteException extends RuntimeException {

    public UsuarioYaExisteException(String mensaje) {
        super(mensaje);
    }

}
