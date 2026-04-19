package app.core.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import app.core.component.vehicle.impl.GenericVehicleFactoryImpl;
import app.core.component.vehicle.impl.VehicleAbstractFactoryImpl;
import app.core.constant.enums.StateVehicle;
import app.core.constant.enums.TypeVehicle;
import app.core.custom.exception.UnsupportedVehicleTypeException;
import app.core.model.GenericVehicle;
import app.core.model.VehicleCreateRequest;
import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VehicleAdapterImplTest {

  private VehicleAbstractFactoryImpl createMockFactory() {
    return new VehicleAbstractFactoryImpl(
        Collections.emptyMap(),
        new GenericVehicleFactoryImpl()
    );
  }

  @Test
  @DisplayName("Should create vehicle with valid request")
  void shouldCreateVehicleWithValidRequest() {
    final var factory = createMockFactory();
    final var adapter = new VehicleAdapterImpl(factory);

    final var request = VehicleCreateRequest.builder()
        .type("CAR")
        .state("NEW")
        .build();

    final var vehicle = adapter.createVehicle(request);

    assertThat(vehicle).isNotNull();
    assertThat(vehicle).isInstanceOf(GenericVehicle.class);
  }

  @Test
  @DisplayName("Should throw exception for unsupported vehicle type")
  void shouldThrowExceptionForUnsupportedVehicleType() {
    final var factory = createMockFactory();
    final var adapter = new VehicleAdapterImpl(factory);

    final var request = VehicleCreateRequest.builder()
        .type("SPACESHIP")
        .state("NEW")
        .build();

    assertThatThrownBy(() -> adapter.createVehicle(request))
        .isInstanceOf(UnsupportedVehicleTypeException.class)
        .hasMessageContaining("SPACESHIP");
  }

  @Test
  @DisplayName("Should throw exception for null state")
  void shouldThrowExceptionForNullState() {
    final var factory = createMockFactory();
    final var adapter = new VehicleAdapterImpl(factory);

    final var request = VehicleCreateRequest.builder()
        .type("CAR")
        .state(null)
        .build();

    assertThatThrownBy(() -> adapter.createVehicle(request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("State cannot be null or empty");
  }

  @Test
  @DisplayName("Should throw exception for blank state")
  void shouldThrowExceptionForBlankState() {
    final var factory = createMockFactory();
    final var adapter = new VehicleAdapterImpl(factory);

    final var request = VehicleCreateRequest.builder()
        .type("CAR")
        .state("   ")
        .build();

    assertThatThrownBy(() -> adapter.createVehicle(request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("State cannot be null or empty");
  }

  @Test
  @DisplayName("Should throw exception for empty state")
  void shouldThrowExceptionForEmptyState() {
    final var factory = createMockFactory();
    final var adapter = new VehicleAdapterImpl(factory);

    final var request = VehicleCreateRequest.builder()
        .type("CAR")
        .state("")
        .build();

    assertThatThrownBy(() -> adapter.createVehicle(request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("State cannot be null or empty");
  }

  @Test
  @DisplayName("Should throw exception for invalid state")
  void shouldThrowExceptionForInvalidState() {
    final var factory = createMockFactory();
    final var adapter = new VehicleAdapterImpl(factory);

    final var request = VehicleCreateRequest.builder()
        .type("CAR")
        .state("INVALID_STATE")
        .build();

    assertThatThrownBy(() -> adapter.createVehicle(request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Invalid state");
  }

  @Test
  @DisplayName("Should create vehicle with all valid states")
  void shouldCreateVehicleWithAllValidStates() {
    final var factory = createMockFactory();
    final var adapter = new VehicleAdapterImpl(factory);

    for (final StateVehicle state : StateVehicle.values()) {
      final var request = VehicleCreateRequest.builder()
          .type("CAR")
          .state(state.name())
          .build();

      final var vehicle = adapter.createVehicle(request);
      assertThat(vehicle).isNotNull();
    }
  }

  @Test
  @DisplayName("Should create vehicle with case insensitive type")
  void shouldCreateVehicleWithCaseInsensitiveType() {
    final var factory = createMockFactory();
    final var adapter = new VehicleAdapterImpl(factory);

    final var request = VehicleCreateRequest.builder()
        .type("car")
        .state("NEW")
        .build();

    final var vehicle = adapter.createVehicle(request);

    assertThat(vehicle).isNotNull();
  }

  @Test
  @DisplayName("Should create vehicle with different types")
  void shouldCreateVehicleWithDifferentTypes() {
    final var factory = createMockFactory();
    final var adapter = new VehicleAdapterImpl(factory);

    final String[] types = {"CAR", "TRUCK", "MOTORCYCLE", "BICYCLE"};

    for (final String type : types) {
      final var request = VehicleCreateRequest.builder()
          .type(type)
          .state("NEW")
          .build();

      final var vehicle = adapter.createVehicle(request);
      assertThat(vehicle).isNotNull();
    }
  }
}
