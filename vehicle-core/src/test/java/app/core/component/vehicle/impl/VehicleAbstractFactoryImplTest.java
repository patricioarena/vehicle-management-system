package app.core.component.vehicle.impl;

import static org.assertj.core.api.Assertions.assertThat;

import app.core.component.vehicle.VehicleFactory;
import app.core.constant.enums.StateVehicle;
import app.core.constant.enums.TypeVehicle;
import app.core.model.GenericVehicle;
import app.core.model.Vehicle;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VehicleAbstractFactoryImplTest {

  @Test
  @DisplayName("Should create factory with empty specific factories")
  void shouldCreateFactoryWithEmptySpecificFactories() {
    final Map<TypeVehicle, VehicleFactory> emptyMap = Collections.emptyMap();
    final var genericFactory = new GenericVehicleFactoryImpl();

    final var factory = new VehicleAbstractFactoryImpl(emptyMap, genericFactory);

    assertThat(factory).isNotNull();
  }

  @Test
  @DisplayName("Should create vehicle using generic factory")
  void shouldCreateVehicleUsingGenericFactory() {
    final Map<TypeVehicle, VehicleFactory> emptyMap = Collections.emptyMap();
    final var genericFactory = new GenericVehicleFactoryImpl();
    final var factory = new VehicleAbstractFactoryImpl(emptyMap, genericFactory);

    final var vehicle = factory.apply(TypeVehicle.TRUCK, StateVehicle.NEW);

    assertThat(vehicle).isNotNull();
    assertThat(vehicle).isInstanceOf(GenericVehicle.class);
  }

  @Test
  @DisplayName("Should create vehicles for all types")
  void shouldCreateVehiclesForAllTypes() {
    final Map<TypeVehicle, VehicleFactory> emptyMap = Collections.emptyMap();
    final var genericFactory = new GenericVehicleFactoryImpl();
    final var factory = new VehicleAbstractFactoryImpl(emptyMap, genericFactory);

    for (final TypeVehicle type : TypeVehicle.values()) {
      final var vehicle = factory.apply(type, StateVehicle.NEW);
      assertThat(vehicle).isNotNull();
    }
  }

  @Test
  @DisplayName("Should use specific factory when available")
  void shouldUseSpecificFactoryWhenAvailable() {
    final var specificFactory = new CarFactoryImpl();
    final var genericFactory = new GenericVehicleFactoryImpl();

    final Map<TypeVehicle, VehicleFactory> specificFactories = new EnumMap<>(TypeVehicle.class);
    specificFactories.put(TypeVehicle.CAR, specificFactory);

    final var factory = new VehicleAbstractFactoryImpl(specificFactories, genericFactory);

    final var vehicle = factory.apply(TypeVehicle.CAR, StateVehicle.NEW);

    assertThat(vehicle).isNotNull();
  }

  @Test
  @DisplayName("Should handle multiple specific factories")
  void shouldHandleMultipleSpecificFactories() {
    final var carFactory = new CarFactoryImpl();
    final var sailboatFactory = new LightSailboatFactoryImpl();
    final var genericFactory = new GenericVehicleFactoryImpl();

    final Map<TypeVehicle, VehicleFactory> specificFactories = new EnumMap<>(TypeVehicle.class);
    specificFactories.put(TypeVehicle.CAR, carFactory);
    specificFactories.put(TypeVehicle.LIGHT_SAILBOAT, sailboatFactory);

    final var factory = new VehicleAbstractFactoryImpl(specificFactories, genericFactory);

    final var car = factory.apply(TypeVehicle.CAR, StateVehicle.NEW);
    final var sailboat = factory.apply(TypeVehicle.LIGHT_SAILBOAT, StateVehicle.NEW);
    final var truck = factory.apply(TypeVehicle.TRUCK, StateVehicle.NEW);

    assertThat(car).isNotNull();
    assertThat(sailboat).isNotNull();
    assertThat(truck).isNotNull();
  }

  @Test
  @DisplayName("Should return Vehicle type")
  void shouldReturnVehicleType() {
    final Map<TypeVehicle, VehicleFactory> emptyMap = Collections.emptyMap();
    final var genericFactory = new GenericVehicleFactoryImpl();
    final var factory = new VehicleAbstractFactoryImpl(emptyMap, genericFactory);

    final var vehicle = factory.apply(TypeVehicle.CAR, StateVehicle.NEW);

    assertThat(vehicle).isInstanceOf(Vehicle.class);
  }
}
