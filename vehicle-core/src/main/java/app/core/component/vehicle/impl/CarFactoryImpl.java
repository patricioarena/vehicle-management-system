package app.core.component.vehicle.impl;

import app.core.component.motor.Motor;
import app.core.component.vehicle.VehicleFactory;
import app.core.constant.enums.StateVehicle;
import app.core.constant.enums.TypeVehicle;
import app.core.model.Car;
import app.core.model.Vehicle;
import app.core.model.vo.State;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CarFactoryImpl implements VehicleFactory {

  @Override
  public Vehicle apply(final TypeVehicle typeVehicle, final StateVehicle stateVehicle) {

    final var carBuilder = Car.builder();
    carBuilder.typeVehicle(typeVehicle.getDrivingType());
    carBuilder.state(State.buildFrom(stateVehicle));

    buildEngine(typeVehicle, carBuilder);

    return carBuilder.build();
  }

  private void buildEngine(final TypeVehicle typeVehicle,
      final Car.CarBuilder car) {
    if (typeVehicle.hasEngine()) {
      car.motor(Motor.empty());
    }
    car.motor(Motor.empty());
  }
}
