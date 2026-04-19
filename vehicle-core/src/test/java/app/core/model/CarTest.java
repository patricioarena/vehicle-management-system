package app.core.model;

import static org.assertj.core.api.Assertions.assertThat;

import app.core.component.motor.Motor;
import app.core.model.vo.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CarTest {

  @Test
  @DisplayName("Should create Car using builder")
  void shouldCreateCarUsingBuilder() {
    final var state = State.builder()
        .name("ACTIVE")
        .description("Active state")
        .build();
    final var motor = Motor.empty();

    final var car = Car.builder()
        .typeVehicle("Sedan")
        .motor(motor)
        .state(state)
        .build();

    assertThat(car).isNotNull();
    assertThat(car.typeVehicle()).isEqualTo("Sedan");
    assertThat(car.motor()).isEqualTo(motor);
    assertThat(car.state()).isEqualTo(state);
  }

  @Test
  @DisplayName("Should create Car with null motor")
  void shouldCreateCarWithNullMotor() {
    final var state = State.builder()
        .name("INACTIVE")
        .description("Inactive state")
        .build();

    final var car = Car.builder()
        .typeVehicle("SUV")
        .motor(null)
        .state(state)
        .build();

    assertThat(car).isNotNull();
    assertThat(car.typeVehicle()).isEqualTo("SUV");
    assertThat(car.motor()).isNull();
    assertThat(car.state()).isEqualTo(state);
  }

  @Test
  @DisplayName("Should verify Car is a Vehicle")
  void shouldVerifyCarIsAVehicle() {
    final var car = Car.builder()
        .typeVehicle("Coupe")
        .motor(Motor.empty())
        .state(State.builder().name("NEW").description("New car").build())
        .build();

    assertThat(car).isInstanceOf(Vehicle.class);
  }

  @Test
  @DisplayName("Should verify Car equals and hashCode")
  void shouldVerifyCarEqualsAndHashCode() {
    final var motor = Motor.empty();
    final var state = State.builder().name("TEST").description("Test").build();

    final var car1 = Car.builder()
        .typeVehicle("Hatchback")
        .motor(motor)
        .state(state)
        .build();

    final var car2 = Car.builder()
        .typeVehicle("Hatchback")
        .motor(motor)
        .state(state)
        .build();

    assertThat(car1).isEqualTo(car2);
    assertThat(car1.hashCode()).isEqualTo(car2.hashCode());
  }

  @Test
  @DisplayName("Should verify Car toString")
  void shouldVerifyCarToString() {
    final var car = Car.builder()
        .typeVehicle("Convertible")
        .motor(Motor.empty())
        .state(State.builder().name("ACTIVE").description("Active").build())
        .build();

    assertThat(car.toString()).contains("Convertible");
  }
}
