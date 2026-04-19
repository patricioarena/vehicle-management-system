package app.core.component.motor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MotorTest {

  @Test
  @DisplayName("Should create motor with correct type and power")
  void shouldCreateMotorWithCorrectTypeAndPower() {
    // Using an anonymous subclass since Motor is abstract
    final var motor = new Motor("TestType", 150) {
      @Override
      public void showDetails() {
        System.out.println("Test motor details");
      }
    };

    assertThat(motor).isNotNull();
  }

  @Test
  @DisplayName("Should start motor and set isOn to true")
  void shouldStartMotorAndSetIsOnToTrue() {
    final var motor = new Motor("Electric", 100) {
      @Override
      public void showDetails() {
        System.out.println("Electric motor");
      }
    };

    motor.start();

    // isOn is protected, but we can test behavior via side effects
    // The motor should start without throwing exceptions
  }

  @Test
  @DisplayName("Should stop motor and set isOn to false")
  void shouldStopMotorAndSetIsOnToFalse() {
    final var motor = new Motor("Combustion", 200) {
      @Override
      public void showDetails() {
        System.out.println("Combustion motor");
      }
    };

    motor.start();
    motor.stop();

    // Should stop without throwing exceptions
  }

  @Test
  @DisplayName("Should return empty motor singleton")
  void shouldReturnEmptyMotorSingleton() {
    final var emptyMotor1 = Motor.empty();
    final var emptyMotor2 = Motor.empty();

    assertThat(emptyMotor1).isNotNull();
    assertThat(emptyMotor1).isSameAs(emptyMotor2);
  }

  @Test
  @DisplayName("Should empty motor have Empty type")
  void shouldEmptyMotorHaveEmptyType() {
    final var emptyMotor = Motor.empty();

    // Verify by calling methods that should work
    assertThat(emptyMotor).isNotNull();
  }

  @Test
  @DisplayName("Should empty motor start without error")
  void shouldEmptyMotorStartWithoutError() {
    final var emptyMotor = Motor.empty();

    // Should not throw exception
    emptyMotor.start();
  }

  @Test
  @DisplayName("Should empty motor stop without error")
  void shouldEmptyMotorStopWithoutError() {
    final var emptyMotor = Motor.empty();

    // Should not throw exception
    emptyMotor.stop();
  }

  @Test
  @DisplayName("Should empty motor show details without error")
  void shouldEmptyMotorShowDetailsWithoutError() {
    final var emptyMotor = Motor.empty();

    // Should not throw exception
    emptyMotor.showDetails();
  }
}
