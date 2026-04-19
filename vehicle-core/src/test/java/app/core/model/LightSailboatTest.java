package app.core.model;

import static org.assertj.core.api.Assertions.assertThat;

import app.core.component.motor.Motor;
import app.core.model.vo.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LightSailboatTest {

  @Test
  @DisplayName("Should create LightSailboat using builder")
  void shouldCreateLightSailboatUsingBuilder() {
    final var state = State.builder()
        .name("ACTIVE")
        .description("Active state")
        .build();
    final var motor = Motor.empty();

    final var sailboat = LightSailboat.builder()
        .typeVehicle("Sailboat")
        .motor(motor)
        .state(state)
        .build();

    assertThat(sailboat).isNotNull();
    assertThat(sailboat.typeVehicle()).isEqualTo("Sailboat");
    assertThat(sailboat.motor()).isEqualTo(motor);
    assertThat(sailboat.state()).isEqualTo(state);
  }

  @Test
  @DisplayName("Should create LightSailboat with null motor")
  void shouldCreateLightSailboatWithNullMotor() {
    final var state = State.builder()
        .name("DOCKED")
        .description("Docked state")
        .build();

    final var sailboat = LightSailboat.builder()
        .typeVehicle("Yacht")
        .motor(null)
        .state(state)
        .build();

    assertThat(sailboat).isNotNull();
    assertThat(sailboat.typeVehicle()).isEqualTo("Yacht");
    assertThat(sailboat.motor()).isNull();
    assertThat(sailboat.state()).isEqualTo(state);
  }

  @Test
  @DisplayName("Should verify LightSailboat is a Vehicle")
  void shouldVerifyLightSailboatIsAVehicle() {
    final var sailboat = LightSailboat.builder()
        .typeVehicle("Catamaran")
        .motor(Motor.empty())
        .state(State.builder().name("NEW").description("New boat").build())
        .build();

    assertThat(sailboat).isInstanceOf(Vehicle.class);
  }

  @Test
  @DisplayName("Should verify LightSailboat equals and hashCode")
  void shouldVerifyLightSailboatEqualsAndHashCode() {
    final var motor = Motor.empty();
    final var state = State.builder().name("TEST").description("Test").build();

    final var sailboat1 = LightSailboat.builder()
        .typeVehicle("Sloop")
        .motor(motor)
        .state(state)
        .build();

    final var sailboat2 = LightSailboat.builder()
        .typeVehicle("Sloop")
        .motor(motor)
        .state(state)
        .build();

    assertThat(sailboat1).isEqualTo(sailboat2);
    assertThat(sailboat1.hashCode()).isEqualTo(sailboat2.hashCode());
  }

  @Test
  @DisplayName("Should verify LightSailboat toString")
  void shouldVerifyLightSailboatToString() {
    final var sailboat = LightSailboat.builder()
        .typeVehicle("Cutter")
        .motor(Motor.empty())
        .state(State.builder().name("ACTIVE").description("Active").build())
        .build();

    assertThat(sailboat.toString()).contains("Cutter");
  }
}
