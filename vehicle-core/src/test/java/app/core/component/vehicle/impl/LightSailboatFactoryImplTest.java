package app.core.component.vehicle.impl;

import static org.assertj.core.api.Assertions.assertThat;

import app.core.component.vehicle.VehicleFactory;
import app.core.constant.enums.StateVehicle;
import app.core.constant.enums.TypeVehicle;
import app.core.model.LightSailboat;
import app.core.model.Vehicle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LightSailboatFactoryImplTest {

  @Test
  @DisplayName("Should create LightSailboat")
  void shouldCreateLightSailboat() {
    final VehicleFactory factory = new LightSailboatFactoryImpl();

    final var vehicle = factory.apply(TypeVehicle.LIGHT_SAILBOAT, StateVehicle.NEW);

    assertThat(vehicle).isNotNull();
    assertThat(vehicle).isInstanceOf(LightSailboat.class);
  }

  @Test
  @DisplayName("Should create LightSailboat with different states")
  void shouldCreateLightSailboatWithDifferentStates() {
    final VehicleFactory factory = new LightSailboatFactoryImpl();

    for (final StateVehicle state : StateVehicle.values()) {
      final var vehicle = factory.apply(TypeVehicle.LIGHT_SAILBOAT, state);
      assertThat(vehicle).isNotNull();
    }
  }

  @Test
  @DisplayName("Should create LightSailboat with correct type vehicle")
  void shouldCreateLightSailboatWithCorrectTypeVehicle() {
    final VehicleFactory factory = new LightSailboatFactoryImpl();

    final var vehicle = factory.apply(TypeVehicle.LIGHT_SAILBOAT, StateVehicle.NEW);
    final var sailboat = (LightSailboat) vehicle;

    assertThat(sailboat.typeVehicle()).isEqualTo(TypeVehicle.LIGHT_SAILBOAT.getDrivingType());
  }

  @Test
  @DisplayName("Should create LightSailboat that is a Vehicle")
  void shouldCreateLightSailboatThatIsAVehicle() {
    final VehicleFactory factory = new LightSailboatFactoryImpl();

    final var vehicle = factory.apply(TypeVehicle.LIGHT_SAILBOAT, StateVehicle.NEW);

    assertThat(vehicle).isInstanceOf(Vehicle.class);
  }

  @Test
  @DisplayName("Should handle vehicle type with engine")
  void shouldHandleVehicleTypeWithEngine() {
    final VehicleFactory factory = new LightSailboatFactoryImpl();

    final var vehicle = factory.apply(TypeVehicle.CAR, StateVehicle.NEW);

    assertThat(vehicle).isNotNull();
  }
}
