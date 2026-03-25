package app.component.vehicle.impl;

import app.component.motor.Motor;
import app.component.vehicle.VehicleFactory;
import app.constant.enums.StateVehicle;
import app.constant.enums.TypeVehicle;
import app.model.Car;
import app.model.Vehicle;
import app.model.vo.State;
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
