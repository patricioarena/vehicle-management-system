package app.core.usecase;

import app.core.model.Vehicle;
import app.core.model.VehicleCreateRequest;

/**
 * Adapter interface for vehicle creation operations.
 */
@FunctionalInterface
public interface VehicleAdapter {

  /**
   * Creates a vehicle based on the provided request.
   *
   * @param request the vehicle creation request containing type and state
   * @return the created vehicle
   * @throws IllegalArgumentException if the vehicle type or state is invalid
   */
  Vehicle createVehicle(VehicleCreateRequest request);
}
