package app.core.component.vehicle;

import app.core.constant.enums.StateVehicle;
import app.core.constant.enums.TypeVehicle;
import app.core.model.Vehicle;

@FunctionalInterface
public interface VehicleFactory {

  Vehicle apply(TypeVehicle typeVehicle, StateVehicle stateVehicle);
}
