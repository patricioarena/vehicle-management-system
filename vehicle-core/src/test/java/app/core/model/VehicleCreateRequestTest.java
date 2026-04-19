package app.core.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VehicleCreateRequestTest {

  @Test
  @DisplayName("Should create VehicleCreateRequest using builder")
  void shouldCreateVehicleCreateRequestUsingBuilder() {
    final var request = VehicleCreateRequest.builder()
        .type("CAR")
        .state("ACTIVE")
        .build();

    assertThat(request).isNotNull();
    assertThat(request.type()).isEqualTo("CAR");
    assertThat(request.state()).isEqualTo("ACTIVE");
  }

  @Test
  @DisplayName("Should create VehicleCreateRequest with null values")
  void shouldCreateVehicleCreateRequestWithNullValues() {
    final var request = VehicleCreateRequest.builder()
        .type(null)
        .state(null)
        .build();

    assertThat(request).isNotNull();
    assertThat(request.type()).isNull();
    assertThat(request.state()).isNull();
  }

  @Test
  @DisplayName("Should verify VehicleCreateRequest equals and hashCode")
  void shouldVerifyVehicleCreateRequestEqualsAndHashCode() {
    final var request1 = VehicleCreateRequest.builder()
        .type("TRUCK")
        .state("NEW")
        .build();

    final var request2 = VehicleCreateRequest.builder()
        .type("TRUCK")
        .state("NEW")
        .build();

    assertThat(request1).isEqualTo(request2);
    assertThat(request1.hashCode()).isEqualTo(request2.hashCode());
  }

  @Test
  @DisplayName("Should verify VehicleCreateRequest toString")
  void shouldVerifyVehicleCreateRequestToString() {
    final var request = VehicleCreateRequest.builder()
        .type("BOAT")
        .state("INACTIVE")
        .build();

    assertThat(request.toString()).contains("BOAT");
    assertThat(request.toString()).contains("INACTIVE");
  }
}
