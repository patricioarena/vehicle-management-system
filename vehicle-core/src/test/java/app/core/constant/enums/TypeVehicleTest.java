package app.core.constant.enums;

import static app.core.constant.DrivingType.AERIAL;
import static app.core.constant.DrivingType.AQUATIC;
import static app.core.constant.DrivingType.LAND;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TypeVehicleTest {

  @Test
  @DisplayName("Should have correct number of vehicle types")
  void shouldHaveCorrectNumberOfVehicleTypes() {
    final var types = TypeVehicle.values();

    assertThat(types).hasSizeGreaterThan(0);
  }

  @Test
  @DisplayName("Should get ID for vehicle type")
  void shouldGetIdForVehicleType() {
    assertThat(TypeVehicle.CAR.getId()).isEqualTo(1);
    assertThat(TypeVehicle.TRUCK.getId()).isEqualTo(2);
    assertThat(TypeVehicle.AIRPLANE.getId()).isEqualTo(8);
  }

  @Test
  @DisplayName("Should check if vehicle has engine")
  void shouldCheckIfVehicleHasEngine() {
    assertThat(TypeVehicle.CAR.hasEngine()).isTrue();
    assertThat(TypeVehicle.BICYCLE.hasEngine()).isFalse();
    assertThat(TypeVehicle.LIGHT_SAILBOAT.hasEngine()).isFalse();
    assertThat(TypeVehicle.AIRPLANE.hasEngine()).isTrue();
  }

  @Test
  @DisplayName("Should get driving type for vehicle")
  void shouldGetDrivingTypeForVehicle() {
    assertThat(TypeVehicle.CAR.getDrivingType()).isEqualTo(LAND);
    assertThat(TypeVehicle.BICYCLE.getDrivingType()).isEqualTo(LAND);
    assertThat(TypeVehicle.LIGHT_SAILBOAT.getDrivingType()).isEqualTo(AQUATIC);
    assertThat(TypeVehicle.AIRPLANE.getDrivingType()).isEqualTo(AERIAL);
    assertThat(TypeVehicle.HELICOPTER.getDrivingType()).isEqualTo(AERIAL);
  }

  @Test
  @DisplayName("Should get enum by name")
  void shouldGetEnumByName() {
    final var car = TypeVehicle.valueOf("CAR");
    final var truck = TypeVehicle.valueOf("TRUCK");

    assertThat(car).isEqualTo(TypeVehicle.CAR);
    assertThat(truck).isEqualTo(TypeVehicle.TRUCK);
  }

  @Test
  @DisplayName("Should iterate over all vehicle types")
  void shouldIterateOverAllVehicleTypes() {
    int landCount = 0;
    int aquaticCount = 0;
    int aerialCount = 0;
    int engineCount = 0;

    for (final TypeVehicle type : TypeVehicle.values()) {
      if (LAND.equals(type.getDrivingType())) {
        landCount++;
      }
      if (AQUATIC.equals(type.getDrivingType())) {
        aquaticCount++;
      }
      if (AERIAL.equals(type.getDrivingType())) {
        aerialCount++;
      }
      if (type.hasEngine()) {
        engineCount++;
      }
    }

    assertThat(landCount).isGreaterThan(0);
    assertThat(aquaticCount).isGreaterThan(0);
    assertThat(aerialCount).isGreaterThan(0);
    assertThat(engineCount).isGreaterThan(0);
  }

  @Test
  @DisplayName("Should have correct ID sequence")
  void shouldHaveCorrectIdSequence() {
    int previousId = 0;
    for (final TypeVehicle type : TypeVehicle.values()) {
      assertThat(type.getId()).isGreaterThan(previousId);
      previousId = type.getId();
    }
  }

  @Test
  @DisplayName("Should verify specific vehicle types")
  void shouldVerifySpecificVehicleTypes() {
    // Verify some specific types
    assertThat(TypeVehicle.CAR).isNotNull();
    assertThat(TypeVehicle.MOTORCYCLE).isNotNull();
    assertThat(TypeVehicle.BICYCLE).isNotNull();
    assertThat(TypeVehicle.BUS).isNotNull();
    assertThat(TypeVehicle.TRAIN).isNotNull();
    assertThat(TypeVehicle.BOAT).isNotNull();
    assertThat(TypeVehicle.SUBMARINE).isNotNull();
    assertThat(TypeVehicle.AIRPLANE).isNotNull();
    assertThat(TypeVehicle.HELICOPTER).isNotNull();
  }
}
