package app.core.usecase;

import app.core.component.vehicle.impl.VehicleAbstractFactoryImpl;
import app.core.constant.enums.StateVehicle;
import app.core.constant.enums.TypeVehicle;
import app.core.model.Car;
import app.core.model.LightSailboat;
import app.core.model.Vehicle;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

/** Adaptador para manejar la creación de vehículos con tipos específicos. */
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class VehicleAdapterImpl implements VehicleAdapter {

  private final VehicleAbstractFactoryImpl factory;

  public Car createCar(StateVehicle state) {
    Vehicle vehicle = factory.apply(TypeVehicle.CAR, state);
    if (vehicle instanceof Car) {
      return (Car) vehicle;
    }
    throw new ClassCastException("El vehículo no es del tipo Car");
  }

  public LightSailboat createLightSailboat(StateVehicle state) {
    Vehicle vehicle = factory.apply(TypeVehicle.LIGHT_SAILBOAT, state);
    if (vehicle instanceof LightSailboat) {
      return (LightSailboat) vehicle;
    }
    throw new ClassCastException("El vehículo no es del tipo LightSailboat");
  }
}
