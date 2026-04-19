package app.core.component.vehicle.impl;

import static org.assertj.core.api.Assertions.assertThat;

import app.core.component.vehicle.VehicleFactory;
import app.core.constant.enums.StateVehicle;
import app.core.constant.enums.TypeVehicle;
import app.core.model.GenericVehicle;
import app.core.model.Vehicle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GenericVehicleFactoryImplTest {

  @Test
  @DisplayName("Should create GenericVehicle for vehicle with engine")
  void shouldCreateGenericVehicleForVehicleWithEngine() {
    final VehicleFactory factory = new GenericVehicleFactoryImpl();

    final var vehicle = factory.apply(TypeVehicle.TRUCK, StateVehicle.NEW);

    assertThat(vehicle).isNotNull();
    assertThat(vehicle).isInstanceOf(GenericVehicle.class);
  }

  @Test
  @DisplayName("Should create GenericVehicle for vehicle without engine")
  void shouldCreateGenericVehicleForVehicleWithoutEngine() {
    final VehicleFactory factory = new GenericVehicleFactoryImpl();

    final var vehicle = factory.apply(TypeVehicle.BICYCLE, StateVehicle.NEW);

    assertThat(vehicle).isNotNull();
    assertThat(vehicle).isInstanceOf(GenericVehicle.class);
  }

  @Test
  @DisplayName("Should create GenericVehicle for all vehicle types")
  void shouldCreateGenericVehicleForAllVehicleTypes() {
    final VehicleFactory factory = new GenericVehicleFactoryImpl();

    for (final TypeVehicle type : TypeVehicle.values()) {
      final var vehicle = factory.apply(type, StateVehicle.NEW);
      assertThat(vehicle).isNotNull();
      assertThat(vehicle).isInstanceOf(GenericVehicle.class);
    }
  }

  @Test
  @DisplayName("Should create GenericVehicle with correct name")
  void shouldCreateGenericVehicleWithCorrectName() {
    final VehicleFactory factory = new GenericVehicleFactoryImpl();

    final var vehicle = factory.apply(TypeVehicle.CAR, StateVehicle.NEW);
    final var genericVehicle = (GenericVehicle) vehicle;

    assertThat(genericVehicle.name()).isEqualTo(TypeVehicle.CAR.name());
  }

  @Test
  @DisplayName("Should create GenericVehicle with correct type")
  void shouldCreateGenericVehicleWithCorrectType() {
    final VehicleFactory factory = new GenericVehicleFactoryImpl();

    final var vehicle = factory.apply(TypeVehicle.TRUCK, StateVehicle.NEW);
    final var genericVehicle = (GenericVehicle) vehicle;

    assertThat(genericVehicle.typeVehicle()).isEqualTo(TypeVehicle.TRUCK.getDrivingType());
  }

  @Test
  @DisplayName("Should create GenericVehicle with motor when hasEngine is true")
  void shouldCreateGenericVehicleWithMotorWhenHasEngineIsTrue() {
    final VehicleFactory factory = new GenericVehicleFactoryImpl();

    final var vehicle = factory.apply(TypeVehicle.CAR, StateVehicle.NEW);
    final var genericVehicle = (GenericVehicle) vehicle;

    assertThat(genericVehicle.motor()).isNotNull();
  }

  @Test
  @DisplayName("Should create GenericVehicle without motor when hasEngine is false")
  void shouldCreateGenericVehicleWithoutMotorWhenHasEngineIsFalse() {
    final VehicleFactory factory = new GenericVehicleFactoryImpl();

    final var vehicle = factory.apply(TypeVehicle.BICYCLE, StateVehicle.NEW);
    final var genericVehicle = (GenericVehicle) vehicle;

    assertThat(genericVehicle.motor()).isNull();
  }

  @Test
  @DisplayName("Should create GenericVehicle with all states")
  void shouldCreateGenericVehicleWithAllStates() {
    final VehicleFactory factory = new GenericVehicleFactoryImpl();

    for (final StateVehicle state : StateVehicle.values()) {
      final var vehicle = factory.apply(TypeVehicle.CAR, state);
      assertThat(vehicle).isNotNull();
    }
  }
}
