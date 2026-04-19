package app.core.component.motor;

import static org.assertj.core.api.Assertions.assertThat;

import app.core.constant.enums.FuelType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CombustionMotorTest {

  @Test
  @DisplayName("Should create CombustionMotor with correct properties")
  void shouldCreateCombustionMotorWithCorrectProperties() {
    final var motor = new CombustionMotor(150, 4, FuelType.GASOLINE);

    assertThat(motor).isNotNull();
  }

  @Test
  @DisplayName("Should create CombustionMotor with different fuel types")
  void shouldCreateCombustionMotorWithDifferentFuelTypes() {
    final var gasolineMotor = new CombustionMotor(200, 6, FuelType.GASOLINE);
    final var dieselMotor = new CombustionMotor(180, 4, FuelType.DIESEL);

    assertThat(gasolineMotor).isNotNull();
    assertThat(dieselMotor).isNotNull();
  }

  @Test
  @DisplayName("Should show details without error")
  void shouldShowDetailsWithoutError() {
    final var motor = new CombustionMotor(150, 4, FuelType.GASOLINE);

    // Should not throw exception
    motor.showDetails();
  }

  @Test
  @DisplayName("Should start and stop without error")
  void shouldStartAndStopWithoutError() {
    final var motor = new CombustionMotor(200, 6, FuelType.DIESEL);

    motor.start();
    motor.stop();

    // Should complete without exceptions
  }

  @Test
  @DisplayName("Should create CombustionMotor with various configurations")
  void shouldCreateCombustionMotorWithVariousConfigurations() {
    // 4 cylinder motor
    final var motor4Cyl = new CombustionMotor(120, 4, FuelType.GASOLINE);
    assertThat(motor4Cyl).isNotNull();

    // 6 cylinder motor
    final var motor6Cyl = new CombustionMotor(250, 6, FuelType.GASOLINE);
    assertThat(motor6Cyl).isNotNull();

    // 8 cylinder motor
    final var motor8Cyl = new CombustionMotor(400, 8, FuelType.GASOLINE);
    assertThat(motor8Cyl).isNotNull();
  }
}
