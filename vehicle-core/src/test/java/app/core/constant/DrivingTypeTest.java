package app.core.constant;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DrivingTypeTest {

  @Test
  @DisplayName("Should have correct constant values")
  void shouldHaveCorrectConstantValues() {
    assertThat(DrivingType.LAND).isEqualTo("LAND");
    assertThat(DrivingType.AQUATIC).isEqualTo("AQUATIC");
    assertThat(DrivingType.AERIAL).isEqualTo("AERIAL");
  }

  @Test
  @DisplayName("Should be interface with constants")
  void shouldBeInterfaceWithConstants() {
    assertThat(DrivingType.class).isInterface();
  }

  @Test
  @DisplayName("Should be able to reference constants")
  void shouldBeAbleToReferenceConstants() {
    final String landType = DrivingType.LAND;
    final String aquaticType = DrivingType.AQUATIC;
    final String aerialType = DrivingType.AERIAL;

    assertThat(landType).isNotNull();
    assertThat(aquaticType).isNotNull();
    assertThat(aerialType).isNotNull();
  }
}
