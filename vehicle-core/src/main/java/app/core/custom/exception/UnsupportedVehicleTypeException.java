package app.core.custom.exception;

/**
 * Excepción personalizada para manejar tipos de vehículos no soportados.
 */
public class UnsupportedVehicleTypeException extends RuntimeException {

    /**
     * Constructor que acepta un mensaje de error.
     *
     * @param message El mensaje de error.
     */
    public UnsupportedVehicleTypeException(String message) {
        super(message);
    }
}