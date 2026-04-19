package app.core.component.vehicle.impl;

import static org.assertj.core.api.Assertions.assertThat;

import app.core.component.vehicle.VehicleFactory;
import app.core.constant.enums.StateVehicle;
import app.core.constant.enums.TypeVehicle;
import app.core.model.Car;
import app.core.model.Vehicle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CarFactoryImplTest {

  @Test
  @DisplayName("Should create Car with engine")
  void shouldCreateCarWithEngine() {
    final VehicleFactory factory = new CarFactoryImpl();

    final var vehicle = factory.apply(TypeVehicle.CAR, StateVehicle.NEW);

    assertThat(vehicle).isNotNull();
    assertThat(vehicle).isInstanceOf(Car.class);
  }

  @Test
  @DisplayName("Should create Car with different states")
  void shouldCreateCarWithDifferentStates() {
    final VehicleFactory factory = new CarFactoryImpl();

    for (final StateVehicle state : StateVehicle.values()) {
      final var vehicle = factory.apply(TypeVehicle.CAR, state);
      assertThat(vehicle).isNotNull();
    }
  }

  @Test
  @DisplayName("Should create Car with correct type vehicle")
  void shouldCreateCarWithCorrectTypeVehicle() {
    final VehicleFactory factory = new CarFactoryImpl();

    final var vehicle = factory.apply(TypeVehicle.CAR, StateVehicle.NEW);
    final var car = (Car) vehicle;

    assertThat(car.typeVehicle()).isEqualTo(TypeVehicle.CAR.getDrivingType());
  }

  @Test
  @DisplayName("Should create Car that is a Vehicle")
  void shouldCreateCarThatIsAVehicle() {
    final VehicleFactory factory = new CarFactoryImpl();

    final var vehicle = factory.apply(TypeVehicle.CAR, StateVehicle.NEW);

    assertThat(vehicle).isInstanceOf(Vehicle.class);
  }

  @Test
  @DisplayName("Should handle vehicle type without engine")
  void shouldHandleVehicleTypeWithoutEngine() {
    final VehicleFactory factory = new CarFactoryImpl();

    final var vehicle = factory.apply(TypeVehicle.SKATEBOARD, StateVehicle.NEW);

    assertThat(vehicle).isNotNull();
  }
}
