package app.core.usecase;

import app.core.component.vehicle.impl.VehicleAbstractFactoryImpl;
import app.core.constant.enums.StateVehicle;
import app.core.constant.enums.TypeVehicle;
import app.core.custom.exception.UnsupportedVehicleTypeException;
import app.core.mapper.TypeVehicleMapper;
import app.core.model.Vehicle;
import app.core.model.VehicleCreateRequest;
import jakarta.inject.Inject;

/**
 * Adaptador para manejar la creación de vehículos.
 */
public class VehicleAdapterImpl implements VehicleAdapter {

  private final VehicleAbstractFactoryImpl factory;

  @Inject
  public VehicleAdapterImpl(final VehicleAbstractFactoryImpl factory) {
    this.factory = factory;
  }

  /**
   * Creates a vehicle based on the provided request.
   *
   * @param request the vehicle creation request
   * @return the created vehicle
   * @throws IllegalArgumentException if type or state is invalid
   * @throws UnsupportedVehicleTypeException if vehicle type is not supported
   */
  @Override
  public Vehicle createVehicle(final VehicleCreateRequest request) {
    final var typeVehicle = TypeVehicleMapper.toEnum(request.type())
        .orElseThrow(() -> new UnsupportedVehicleTypeException(
            "Vehicle type '" + request.type() + "' not found"));

    final var stateVehicle = parseStateVehicle(request.state());

    return factory.apply(typeVehicle, stateVehicle);
  }

  private StateVehicle parseStateVehicle(final String state) {
    if (state == null || state.isBlank()) {
      throw new IllegalArgumentException("State cannot be null or empty");
    }

    try {
      return StateVehicle.valueOf(state.toUpperCase(java.util.Locale.ROOT));
    } catch (final IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid state: " + state);
    }
  }
}
