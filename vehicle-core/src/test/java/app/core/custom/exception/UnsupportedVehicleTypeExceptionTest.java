package app.core.custom.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UnsupportedVehicleTypeExceptionTest {

  @Test
  @DisplayName("Should create exception with message")
  void shouldCreateExceptionWithMessage() {
    final String message = "Unsupported vehicle type: SPACESHIP";
    final var exception = new UnsupportedVehicleTypeException(message);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo(message);
  }

  @Test
  @DisplayName("Should be instance of RuntimeException")
  void shouldBeInstanceOfRuntimeException() {
    final var exception = new UnsupportedVehicleTypeException("Test message");

    assertThat(exception).isInstanceOf(RuntimeException.class);
  }

  @Test
  @DisplayName("Should throw exception with correct message")
  void shouldThrowExceptionWithCorrectMessage() {
    final String message = "Type XYZ is not supported";

    assertThatThrownBy(() -> {
      throw new UnsupportedVehicleTypeException(message);
    }).isInstanceOf(UnsupportedVehicleTypeException.class)
        .hasMessage(message);
  }

  @Test
  @DisplayName("Should create exception with different messages")
  void shouldCreateExceptionWithDifferentMessages() {
    final var exception1 = new UnsupportedVehicleTypeException("Message 1");
    final var exception2 = new UnsupportedVehicleTypeException("Message 2");

    assertThat(exception1.getMessage()).isEqualTo("Message 1");
    assertThat(exception2.getMessage()).isEqualTo("Message 2");
    assertThat(exception1.getMessage()).isNotEqualTo(exception2.getMessage());
  }

  @Test
  @DisplayName("Should create exception with empty message")
  void shouldCreateExceptionWithEmptyMessage() {
    final var exception = new UnsupportedVehicleTypeException("");

    assertThat(exception.getMessage()).isEmpty();
  }

  @Test
  @DisplayName("Should create exception with null message")
  void shouldCreateExceptionWithNullMessage() {
    final var exception = new UnsupportedVehicleTypeException(null);

    assertThat(exception.getMessage()).isNull();
  }
}
