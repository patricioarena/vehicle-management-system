package app.core.constant.enums;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StateVehicleTest {

  @Test
  @DisplayName("Should have correct number of states")
  void shouldHaveCorrectNumberOfStates() {
    final var states = StateVehicle.values();

    assertThat(states).hasSize(4);
  }

  @Test
  @DisplayName("Should contain expected states")
  void shouldContainExpectedStates() {
    assertThat(StateVehicle.valueOf("NEW")).isEqualTo(StateVehicle.NEW);
    assertThat(StateVehicle.valueOf("CLEAN")).isEqualTo(StateVehicle.CLEAN);
    assertThat(StateVehicle.valueOf("ROUGH")).isEqualTo(StateVehicle.ROUGH);
    assertThat(StateVehicle.valueOf("DAMAGED")).isEqualTo(StateVehicle.DAMAGED);
  }

  @Test
  @DisplayName("Should get description for each state")
  void shouldGetDescriptionForEachState() {
    assertThat(StateVehicle.NEW.getDescription()).isNotNull();
    assertThat(StateVehicle.CLEAN.getDescription()).isNotNull();
    assertThat(StateVehicle.ROUGH.getDescription()).isNotNull();
    assertThat(StateVehicle.DAMAGED.getDescription()).isNotNull();
  }

  @Test
  @DisplayName("Should get name in lowercase for each state")
  void shouldGetNameInLowercaseForEachState() {
    assertThat(StateVehicle.NEW.getName()).isEqualTo("new");
    assertThat(StateVehicle.CLEAN.getName()).isEqualTo("clean");
    assertThat(StateVehicle.ROUGH.getName()).isEqualTo("rough");
    assertThat(StateVehicle.DAMAGED.getName()).isEqualTo("damaged");
  }

  @Test
  @DisplayName("Should get different descriptions for different states")
  void shouldGetDifferentDescriptionsForDifferentStates() {
    final var newDescription = StateVehicle.NEW.getDescription();
    final var cleanDescription = StateVehicle.CLEAN.getDescription();
    final var roughDescription = StateVehicle.ROUGH.getDescription();
    final var damagedDescription = StateVehicle.DAMAGED.getDescription();

    assertThat(newDescription).isNotEqualTo(cleanDescription);
    assertThat(cleanDescription).isNotEqualTo(roughDescription);
    assertThat(roughDescription).isNotEqualTo(damagedDescription);
  }

  @Test
  @DisplayName("Should iterate over all states")
  void shouldIterateOverAllStates() {
    int count = 0;
    for (final StateVehicle state : StateVehicle.values()) {
      assertThat(state.getName()).isNotNull();
      assertThat(state.getDescription()).isNotNull();
      count++;
    }
    assertThat(count).isEqualTo(4);
  }

  @Test
  @DisplayName("Should get ordinal values")
  void shouldGetOrdinalValues() {
    assertThat(StateVehicle.NEW.ordinal()).isZero();
    assertThat(StateVehicle.CLEAN.ordinal()).isEqualTo(1);
    assertThat(StateVehicle.ROUGH.ordinal()).isEqualTo(2);
    assertThat(StateVehicle.DAMAGED.ordinal()).isEqualTo(3);
  }
}
