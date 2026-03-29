package app.core.component.vehicle.impl;

import app.core.component.vehicle.VehicleFactory;
import app.core.constant.enums.StateVehicle;
import app.core.constant.enums.TypeVehicle;
import app.core.custom.exception.UnsupportedVehicleTypeException;
import app.core.model.Vehicle;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.EnumMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa una fábrica general de vehículos. Utiliza un mapa para asociar tipos de
 * vehículos con sus respectivas fábricas.
 */
@Slf4j
@Singleton
public class VehicleAbstractFactoryImpl implements VehicleFactory {

  private final Map<TypeVehicle, VehicleFactory> factories;

  @Inject
  public VehicleAbstractFactoryImpl(
      final @Named("CarFactory") VehicleFactory carFactory,
      final @Named("LightSailboatFactory") VehicleFactory sailboatFactory) {
    // Inicialización del mapa en el constructor
    factories = new EnumMap<>(TypeVehicle.class);
    factories.put(TypeVehicle.CAR, carFactory);
    factories.put(TypeVehicle.LIGHT_SAILBOAT, sailboatFactory);

    log.info("Factories initialized: {}", factories.keySet());
  }

  /**
   * Crea un vehículo basado en el tipo y estado proporcionados.
   *
   * @param vehicleType El tipo de vehículo a crear.
   * @param state El estado inicial del vehículo.
   * @return Una instancia del vehículo creado.
   * @throws UnsupportedVehicleTypeException Si el tipo de vehículo no está soportado.
   */
  @Override
  public Vehicle apply(final TypeVehicle vehicleType, final StateVehicle state) {
    final VehicleFactory factory = factories.get(vehicleType);
    if (factory == null) {
      throw new UnsupportedVehicleTypeException(
          "Tipo de vehículo no soportado: " + vehicleType.name());
    }
    return factory.apply(vehicleType, state);
  }
}
