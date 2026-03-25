package app.component.vehicle.impl;

import app.component.motor.Motor;
import app.component.vehicle.VehicleFactory;
import app.constant.enums.StateVehicle;
import app.constant.enums.TypeVehicle;
import app.model.LightSailboat;
import app.model.Vehicle;
import app.model.vo.State;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class LightSailboatFactoryImpl implements VehicleFactory {

  @Override
  public Vehicle apply(final TypeVehicle typeVehicle, final StateVehicle stateVehicle) {

    final var lightSailboatBuilder = LightSailboat.builder();
    lightSailboatBuilder.typeVehicle(typeVehicle.getDrivingType());
    lightSailboatBuilder.state(State.buildFrom(stateVehicle));

    buildEngine(typeVehicle, lightSailboatBuilder);

    return lightSailboatBuilder.build();
  }

  private void buildEngine(final TypeVehicle typeVehicle,
      final LightSailboat.LightSailboatBuilder sailboat) {
    if (typeVehicle.hasEngine()) {
      sailboat.motor(Motor.empty());
    }
    sailboat.motor(Motor.empty());
  }
}