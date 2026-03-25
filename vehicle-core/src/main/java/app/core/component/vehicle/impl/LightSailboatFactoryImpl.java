package app.core.component.vehicle.impl;

import app.core.component.motor.Motor;
import app.core.component.vehicle.VehicleFactory;
import app.core.constant.enums.StateVehicle;
import app.core.constant.enums.TypeVehicle;
import app.core.model.LightSailboat;
import app.core.model.Vehicle;
import app.core.model.vo.State;
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