package app.core.usecase;

import app.core.constant.enums.StateVehicle;
import app.core.model.Car;
import app.core.model.LightSailboat;

public interface VehicleAdapter {

  Car createCar(StateVehicle state);
  LightSailboat createLightSailboat(StateVehicle state);
}
