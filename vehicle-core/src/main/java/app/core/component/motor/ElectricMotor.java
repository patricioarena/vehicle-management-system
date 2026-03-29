package app.core.component.motor;

import app.core.constant.enums.FuelType;

/**
 * Representa un motor eléctrico con propiedades específicas como capacidad de batería. Extiende la
 * clase abstracta {@link Motor}.
 */
public class ElectricMotor extends Motor {
  /**
   * Capacidad de la batería en kWh. Representa la cantidad de energía que puede almacenar el motor
   * eléctrico.
   */
  private final int batteryCapacity;

  /**
   * Constructor para inicializar un motor eléctrico con su potencia y capacidad de batería.
   *
   * @param power Potencia del motor en kW.
   * @param batteryCapacity Capacidad de la batería en kWh.
   */
  public ElectricMotor(final int power, final int batteryCapacity) {
    super(FuelType.ELECTRIC.name(), power);
    this.batteryCapacity = batteryCapacity;
  }

  /**
   * Muestra los detalles específicos del motor eléctrico, incluyendo potencia y capacidad de
   * batería.
   */
  @Override
  public void showDetails() {
    System.out.println("Electric Motor:");
    System.out.println("Power: " + power + " kW");
    System.out.println("Battery Capacity: " + batteryCapacity + " kWh");
  }
}
