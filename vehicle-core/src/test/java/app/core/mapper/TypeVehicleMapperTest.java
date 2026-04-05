package app.core.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import app.core.constant.enums.TypeVehicle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TypeVehicleMapperTest {

  @Test
  @DisplayName("Should map valid type strings case-insensitively")
  void shouldMapValidTypesCaseInsensitively() {
    // Test various case combinations
    assertThat(TypeVehicleMapper.toEnum("car")).hasValue(TypeVehicle.CAR);
    assertThat(TypeVehicleMapper.toEnum("CAR")).hasValue(TypeVehicle.CAR);
    assertThat(TypeVehicleMapper.toEnum("Car")).hasValue(TypeVehicle.CAR);
    assertThat(TypeVehicleMapper.toEnum("cAr")).hasValue(TypeVehicle.CAR);
    assertThat(TypeVehicleMapper.toEnum("light_sailboat")).hasValue(TypeVehicle.LIGHT_SAILBOAT);
    assertThat(TypeVehicleMapper.toEnum("LIGHT_SAILBOAT")).hasValue(TypeVehicle.LIGHT_SAILBOAT);
    assertThat(TypeVehicleMapper.toEnum("truck")).hasValue(TypeVehicle.TRUCK);
    assertThat(TypeVehicleMapper.toEnum("airplane")).hasValue(TypeVehicle.AIRPLANE);
  }

  @Test
  @DisplayName("Should return empty for unknown type")
  void shouldReturnEmptyForUnknownType() {
    final var result = TypeVehicleMapper.toEnum("spaceship");

    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("Should return empty for null input")
  void shouldReturnEmptyForNull() {
    final var result = TypeVehicleMapper.toEnum(null);

    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("Should return empty for empty string")
  void shouldReturnEmptyForEmptyString() {
    final var result = TypeVehicleMapper.toEnum("");

    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("Should return empty for blank string")
  void shouldReturnEmptyForBlankString() {
    final var result = TypeVehicleMapper.toEnum("   ");

    assertThat(result).isEmpty();
  }
}
