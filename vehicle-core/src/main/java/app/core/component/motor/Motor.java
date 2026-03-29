package app.core.component.motor;

/**
 * Representa un motor genérico con propiedades y métodos comunes. Esta clase es abstracta y debe
 * ser extendida por tipos específicos de motores.
 */
public abstract class Motor {
  /**
   * Tipo de motor (por ejemplo, Combustión o Eléctrico). Representa la categoría o naturaleza del
   * motor.
   */
  protected final String type;

  /** Potencia del motor en HP o kW. Representa la capacidad de trabajo del motor. */
  protected final int power;

  /**
   * Estado del motor (encendido o apagado). Indica si el motor está actualmente en funcionamiento.
   */
  protected boolean isOn;

  /**
   * Constructor para inicializar un motor con su tipo y potencia.
   *
   * @param type Tipo de motor.
   * @param power Potencia del motor.
   */
  public Motor(final String type, final int power) {
    this.type = type;
    this.power = power;
    this.isOn = false;
  }

  /** Enciende el motor. */
  public void start() {
    this.isOn = true;
    System.out.println("The motor is now ON.");
  }

  /** Apaga el motor. */
  public void stop() {
    this.isOn = false;
    System.out.println("The motor is now OFF.");
  }

  /**
   * Muestra los detalles específicos del motor. Este método debe ser implementado por las clases
   * que extienden esta clase.
   */
  public abstract void showDetails();

  // Static method to create or retrieve the Singleton instance of EmptyMotor
  public static Motor empty() {
    return EmptyMotor.getInstance();
  }

  // Inner class: Represents an "empty" motor
  private static class EmptyMotor extends Motor {
    // Singleton instance
    private static final EmptyMotor INSTANCE = new EmptyMotor();

    // Private constructor to prevent instantiation
    private EmptyMotor() {
      super("Empty", 0);
    }

    // Public method to retrieve the Singleton instance
    public static EmptyMotor getInstance() {
      return INSTANCE;
    }

    @Override
    public void showDetails() {
      System.out.println("This is an empty motor. No details available.");
    }

    @Override
    public void start() {
      System.out.println("Empty motor cannot be started.");
    }

    @Override
    public void stop() {
      System.out.println("Empty motor cannot be stopped.");
    }
  }
}
