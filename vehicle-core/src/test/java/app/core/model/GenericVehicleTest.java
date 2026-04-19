package app.core.model;

import static org.assertj.core.api.Assertions.assertThat;

import app.core.component.motor.Motor;
import app.core.model.vo.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GenericVehicleTest {

  @Test
  @DisplayName("Should create GenericVehicle using builder")
  void shouldCreateGenericVehicleUsingBuilder() {
    final var state = State.builder()
        .name("ACTIVE")
        .description("Active state")
        .build();
    final var motor = Motor.empty();

    final var vehicle = GenericVehicle.builder()
        .name("My Vehicle")
        .typeVehicle("Generic")
        .motor(motor)
        .state(state)
        .build();

    assertThat(vehicle).isNotNull();
    assertThat(vehicle.name()).isEqualTo("My Vehicle");
    assertThat(vehicle.typeVehicle()).isEqualTo("Generic");
    assertThat(vehicle.motor()).isEqualTo(motor);
    assertThat(vehicle.state()).isEqualTo(state);
  }

  @Test
  @DisplayName("Should create GenericVehicle without motor")
  void shouldCreateGenericVehicleWithoutMotor() {
    final var state = State.builder()
        .name("INACTIVE")
        .description("Inactive state")
        .build();

    final var vehicle = GenericVehicle.builder()
        .name("Simple Vehicle")
        .typeVehicle("Bicycle")
        .state(state)
        .build();

    assertThat(vehicle).isNotNull();
    assertThat(vehicle.name()).isEqualTo("Simple Vehicle");
    assertThat(vehicle.typeVehicle()).isEqualTo("Bicycle");
    assertThat(vehicle.motor()).isNull();
    assertThat(vehicle.state()).isEqualTo(state);
  }

  @Test
  @DisplayName("Should verify GenericVehicle is a Vehicle")
  void shouldVerifyGenericVehicleIsAVehicle() {
    final var vehicle = GenericVehicle.builder()
        .name("Test")
        .typeVehicle("TestType")
        .motor(Motor.empty())
        .state(State.builder().name("NEW").description("New").build())
        .build();

    assertThat(vehicle).isInstanceOf(Vehicle.class);
  }

  @Test
  @DisplayName("Should verify GenericVehicle equals and hashCode")
  void shouldVerifyGenericVehicleEqualsAndHashCode() {
    final var state = State.builder().name("TEST").description("Test").build();

    final var vehicle1 = GenericVehicle.builder()
        .name("Vehicle")
        .typeVehicle("Type")
        .motor(null)
        .state(state)
        .build();

    final var vehicle2 = GenericVehicle.builder()
        .name("Vehicle")
        .typeVehicle("Type")
        .motor(null)
        .state(state)
        .build();

    assertThat(vehicle1).isEqualTo(vehicle2);
    assertThat(vehicle1.hashCode()).isEqualTo(vehicle2.hashCode());
  }

  @Test
  @DisplayName("Should verify GenericVehicle toString")
  void shouldVerifyGenericVehicleToString() {
    final var vehicle = GenericVehicle.builder()
        .name("MyBoat")
        .typeVehicle("Boat")
        .motor(Motor.empty())
        .state(State.builder().name("ACTIVE").description("Active").build())
        .build();

    assertThat(vehicle.toString()).contains("MyBoat");
    assertThat(vehicle.toString()).contains("Boat");
  }
}
