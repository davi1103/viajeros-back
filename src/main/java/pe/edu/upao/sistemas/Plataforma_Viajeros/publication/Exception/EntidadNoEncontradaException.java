package pe.edu.upao.sistemas.Plataforma_Viajeros.publication.Exception;

import org.springframework.http.HttpStatus;

public class EntidadNoEncontradaException extends RuntimeException {

    public EntidadNoEncontradaException(String message) {
        super(message);
    }

    public EntidadNoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

    private HttpStatus status = HttpStatus.NOT_FOUND;

    public HttpStatus getStatus() {
        return status;
    }
}