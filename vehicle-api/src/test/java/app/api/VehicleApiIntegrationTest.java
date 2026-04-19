package app.api;

import static org.assertj.core.api.Assertions.assertThat;

import app.api.config.ApiModule;
import app.api.controller.VehicleController;
import app.core.config.AppModule;
import app.core.model.VehicleCreateRequest;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VehicleApiIntegrationTest {

  private static Javalin app;
  private static int port;
  private static String baseUrl;

  @BeforeAll
  static void setUp() {
    final Injector injector = Guice.createInjector(new ApiModule());
    final VehicleController controller = injector.getInstance(VehicleController.class);

    app =
        Javalin.create(
            config -> {
              config.showJavalinBanner = false;
              config.jsonMapper(
                  new JavalinJackson()
                      .updateMapper(
                          mapper ->
                              mapper.configure(
                                  SerializationFeature.FAIL_ON_EMPTY_BEANS, false)));
            })
            .start(0);

    port = app.port();
    baseUrl = "http://localhost:" + port;

    controller.registerRoutes(app);
  }

  @AfterAll
  static void tearDown() {
    if (app != null) {
      app.stop();
    }
  }

  @Test
  @DisplayName("Should return health status UP")
  void shouldReturnHealthStatus() throws Exception {
    final var url = new URL(baseUrl + "/health");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");

    final var responseCode = conn.getResponseCode();

    assertThat(responseCode).isEqualTo(200);
  }

  @Test
  @DisplayName("Should create vehicle via API")
  void shouldCreateVehicle() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"CAR\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();

    assertThat(responseCode).isEqualTo(201);
  }

  @Test
  @DisplayName("Should return vehicle types")
  void shouldReturnVehicleTypes() throws Exception {
    final var url = new URL(baseUrl + "/vehicles/types");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");

    final var responseCode = conn.getResponseCode();

    assertThat(responseCode).isEqualTo(200);
  }

  @Test
  @DisplayName("Should return 404 for invalid vehicle type")
  void shouldReturn404ForInvalidType() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"INVALID\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();

    assertThat(responseCode).isEqualTo(404);
  }

  @Test
  @DisplayName("Should return 400 for missing type")
  void shouldReturn400ForMissingType() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();

    assertThat(responseCode).isEqualTo(400);
  }
}