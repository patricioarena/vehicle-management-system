package app.core.component.motor;

import app.core.constant.enums.FuelType;

/**
 * Representa un motor de combustión interna con propiedades específicas como
 * el número de cilindros y el tipo de combustible.
 * Extiende la clase abstracta {@link Motor}.
 */
public class CombustionMotor extends Motor {

  /**
   * Número de cilindros del motor.
   */
  private final int cylinders;

  /**
   * Tipo de combustible utilizado por el motor (por ejemplo, Gasolina, Diesel).
   */
  private final FuelType fuelType;

  /**
   * Constructor para inicializar un motor de combustión con su potencia,
   * número de cilindros y tipo de combustible.
   *
   * @param power Potencia del motor en HP.
   * @param cylinders Número de cilindros del motor.
   * @param fuelType Tipo de combustible utilizado por el motor.
   */
  public CombustionMotor(final int power, final int cylinders, final FuelType fuelType) {
    super(FuelType.COMBUSTION.name(), power);
    this.cylinders = cylinders;
    this.fuelType = fuelType;
  }

  /**
   * Muestra los detalles específicos del motor de combustión, incluyendo
   * su potencia, número de cilindros y tipo de combustible.
   */
  @Override
  public void showDetails() {
    System.out.println("Combustion Motor:");
    System.out.println("Power: " + power + " HP");
    System.out.println("Cylinders: " + cylinders);
    System.out.println("Fuel Type: " + fuelType);
  }
}