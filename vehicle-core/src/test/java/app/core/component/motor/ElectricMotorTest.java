package app.core.component.motor;

import static org.assertj.core.api.Assertions.assertThat;

import app.core.constant.enums.FuelType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElectricMotorTest {

  @Test
  @DisplayName("Should create ElectricMotor with correct properties")
  void shouldCreateElectricMotorWithCorrectProperties() {
    final var motor = new ElectricMotor(150, 75);

    assertThat(motor).isNotNull();
  }

  @Test
  @DisplayName("Should create ElectricMotor with various configurations")
  void shouldCreateElectricMotorWithVariousConfigurations() {
    final var smallMotor = new ElectricMotor(80, 40);
    final var mediumMotor = new ElectricMotor(150, 75);
    final var largeMotor = new ElectricMotor(300, 100);

    assertThat(smallMotor).isNotNull();
    assertThat(mediumMotor).isNotNull();
    assertThat(largeMotor).isNotNull();
  }

  @Test
  @DisplayName("Should show details without error")
  void shouldShowDetailsWithoutError() {
    final var motor = new ElectricMotor(200, 80);

    // Should not throw exception
    motor.showDetails();
  }

  @Test
  @DisplayName("Should start and stop without error")
  void shouldStartAndStopWithoutError() {
    final var motor = new ElectricMotor(150, 60);

    motor.start();
    motor.stop();

    // Should complete without exceptions
  }

  @Test
  @DisplayName("Should be instance of Motor")
  void shouldBeInstanceOfMotor() {
    final var motor = new ElectricMotor(100, 50);

    assertThat(motor).isInstanceOf(Motor.class);
  }
}
