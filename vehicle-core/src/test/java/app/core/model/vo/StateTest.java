package app.core.model.vo;

import static org.assertj.core.api.Assertions.assertThat;

import app.core.constant.enums.StateVehicle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StateTest {

  @Test
  @DisplayName("Should create State using builder")
  void shouldCreateStateUsingBuilder() {
    final var state = State.builder()
        .name("ACTIVE")
        .description("Vehicle is active")
        .build();

    assertThat(state).isNotNull();
    assertThat(state.name()).isEqualTo("ACTIVE");
    assertThat(state.description()).isEqualTo("Vehicle is active");
  }

  @Test
  @DisplayName("Should build State from StateVehicle")
  void shouldBuildStateFromStateVehicle() {
    final var state = State.buildFrom(StateVehicle.NEW);

    assertThat(state).isNotNull();
    assertThat(state.name()).isEqualTo(StateVehicle.NEW.getName());
    assertThat(state.description()).isEqualTo(StateVehicle.NEW.getDescription());
  }

  @Test
  @DisplayName("Should build State from all StateVehicle values")
  void shouldBuildStateFromAllStateVehicleValues() {
    for (final StateVehicle stateVehicle : StateVehicle.values()) {
      final var state = State.buildFrom(stateVehicle);

      assertThat(state.name()).isEqualTo(stateVehicle.getName());
      assertThat(state.description()).isEqualTo(stateVehicle.getDescription());
    }
  }

  @Test
  @DisplayName("Should verify State equals and hashCode")
  void shouldVerifyStateEqualsAndHashCode() {
    final var state1 = State.builder()
        .name("TEST")
        .description("Test description")
        .build();

    final var state2 = State.builder()
        .name("TEST")
        .description("Test description")
        .build();

    assertThat(state1).isEqualTo(state2);
    assertThat(state1.hashCode()).isEqualTo(state2.hashCode());
  }

  @Test
  @DisplayName("Should verify State toString")
  void shouldVerifyStateToString() {
    final var state = State.builder()
        .name("ACTIVE")
        .description("Active state")
        .build();

    assertThat(state.toString()).contains("ACTIVE");
    assertThat(state.toString()).contains("Active state");
  }
}
