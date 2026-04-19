package app.core.constant.enums;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FuelTypeTest {

  @Test
  @DisplayName("Should have correct number of fuel types")
  void shouldHaveCorrectNumberOfFuelTypes() {
    final var fuelTypes = FuelType.values();

    assertThat(fuelTypes).hasSize(11);
  }

  @Test
  @DisplayName("Should contain expected fuel types")
  void shouldContainExpectedFuelTypes() {
    assertThat(FuelType.valueOf("ELECTRIC")).isEqualTo(FuelType.ELECTRIC);
    assertThat(FuelType.valueOf("COMBUSTION")).isEqualTo(FuelType.COMBUSTION);
    assertThat(FuelType.valueOf("GASOLINE")).isEqualTo(FuelType.GASOLINE);
    assertThat(FuelType.valueOf("DIESEL")).isEqualTo(FuelType.DIESEL);
    assertThat(FuelType.valueOf("HYBRID")).isEqualTo(FuelType.HYBRID);
    assertThat(FuelType.valueOf("LPG")).isEqualTo(FuelType.LPG);
    assertThat(FuelType.valueOf("CNG")).isEqualTo(FuelType.CNG);
    assertThat(FuelType.valueOf("HYDROGEN")).isEqualTo(FuelType.HYDROGEN);
    assertThat(FuelType.valueOf("BIOFUEL")).isEqualTo(FuelType.BIOFUEL);
    assertThat(FuelType.valueOf("EMPTY")).isEqualTo(FuelType.EMPTY);
    assertThat(FuelType.valueOf("OTHER")).isEqualTo(FuelType.OTHER);
  }

  @Test
  @DisplayName("Should get enum by name")
  void shouldGetEnumByName() {
    final var electric = FuelType.valueOf("ELECTRIC");
    final var diesel = FuelType.valueOf("DIESEL");
    final var empty = FuelType.valueOf("EMPTY");

    assertThat(electric).isEqualTo(FuelType.ELECTRIC);
    assertThat(diesel).isEqualTo(FuelType.DIESEL);
    assertThat(empty).isEqualTo(FuelType.EMPTY);
  }

  @Test
  @DisplayName("Should get ordinal values")
  void shouldGetOrdinalValues() {
    assertThat(FuelType.ELECTRIC.ordinal()).isZero();
    assertThat(FuelType.COMBUSTION.ordinal()).isEqualTo(1);
    assertThat(FuelType.OTHER.ordinal()).isEqualTo(10);
  }

  @Test
  @DisplayName("Should iterate over all fuel types")
  void shouldIterateOverAllFuelTypes() {
    int count = 0;
    for (final FuelType fuelType : FuelType.values()) {
      assertThat(fuelType).isNotNull();
      count++;
    }
    assertThat(count).isEqualTo(11);
  }
}
