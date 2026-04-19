package app.core.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests para AppModule.
 * Nota: AppModule es configuración Guice que está excluida de cobertura.
 * Estos tests verifican la estructura básica únicamente.
 */
class AppModuleTest {

  @Test
  @DisplayName("Should create AppModule instance")
  void shouldCreateAppModuleInstance() {
    final var module = new AppModule();

    assertThat(module).isNotNull();
    assertThat(module).isInstanceOf(com.google.inject.AbstractModule.class);
  }

  @Test
  @DisplayName("Should be a Guice module")
  void shouldBeAGuiceModule() {
    final var module = new AppModule();

    assertThat(module).isInstanceOf(com.google.inject.Module.class);
  }
}