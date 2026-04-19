package app.api;

import static org.assertj.core.api.Assertions.assertThat;

import app.api.config.ApiModule;
import app.api.controller.VehicleController;
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

  @Test
  @DisplayName("Should return 400 for null type")
  void shouldReturn400ForNullType() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":null,\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();

    assertThat(responseCode).isEqualTo(400);
  }

  @Test
  @DisplayName("Should return 400 for null state")
  void shouldReturn400ForNullState() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"CAR\",\"state\":null}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();

    assertThat(responseCode).isEqualTo(400);
  }

  @Test
  @DisplayName("Should return 400 for empty state")
  void shouldReturn400ForEmptyState() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"CAR\",\"state\":\"\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();

    assertThat(responseCode).isEqualTo(400);
  }

  @Test
  @DisplayName("Should create light sailboat via API")
  void shouldCreateLightSailboat() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"LIGHT_SAILBOAT\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();

    assertThat(responseCode).isEqualTo(201);
  }

  @Test
  @DisplayName("Should create vehicle with different states")
  void shouldCreateVehicleWithDifferentStates() throws Exception {
    for (final String state : new String[] {"NEW", "CLEAN", "ROUGH"}) {
      final var url = new URL(baseUrl + "/vehicles");
      final var conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setDoOutput(true);

      final var json = "{\"type\":\"CAR\",\"state\":\"" + state + "\"}";
      try (final var os = conn.getOutputStream()) {
        os.write(json.getBytes(StandardCharsets.UTF_8));
      }

      final var responseCode = conn.getResponseCode();
      assertThat(responseCode).isEqualTo(201);
    }
  }

  @Test
  @DisplayName("Should create boat via API")
  void shouldCreateBoat() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"BOAT\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();
    assertThat(responseCode).isEqualTo(201);
  }

  @Test
  @DisplayName("Should create motorcycle via API")
  void shouldCreateMotorcycle() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"MOTORCYCLE\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();
    assertThat(responseCode).isEqualTo(201);
  }

  @Test
  @DisplayName("Should create bicycle via API")
  void shouldCreateBicycle() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"BICYCLE\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();
    assertThat(responseCode).isEqualTo(201);
  }

  @Test
  @DisplayName("Should return 404 for invalid state value")
  void shouldReturn404ForInvalidState() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"CAR\",\"state\":\"INVALID_STATE\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();
    assertThat(responseCode).isEqualTo(400);
  }

  @Test
  @DisplayName("Should create truck via API")
  void shouldCreateTruck() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"TRUCK\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();
    assertThat(responseCode).isEqualTo(201);
  }

  @Test
  @DisplayName("Should create bus via API")
  void shouldCreateBus() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"BUS\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();
    assertThat(responseCode).isEqualTo(201);
  }

  @Test
  @DisplayName("Should create airplane via API")
  void shouldCreateAirplane() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"AIRPLANE\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();
    assertThat(responseCode).isEqualTo(201);
  }

  @Test
  @DisplayName("Should create train via API")
  void shouldCreateTrain() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"TRAIN\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();
    assertThat(responseCode).isEqualTo(201);
  }

  @Test
  @DisplayName("Should create van via API")
  void shouldCreateVan() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"VAN\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();
    assertThat(responseCode).isEqualTo(201);
  }

  @Test
  @DisplayName("Should create pickup truck via API")
  void shouldCreatePickupTruck() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"PICKUP_TRUCK\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();
    assertThat(responseCode).isEqualTo(201);
  }

  @Test
  @DisplayName("Should create ambulance via API")
  void shouldCreateAmbulance() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"AMBULANCE\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();
    assertThat(responseCode).isEqualTo(201);
  }

  @Test
  @DisplayName("Should create jet ski via API")
  void shouldCreateJetSki() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"JET_SKI\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();
    assertThat(responseCode).isEqualTo(201);
  }

  @Test
  @DisplayName("Should create submarine via API")
  void shouldCreateSubmarine() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"SUBMARINE\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();
    assertThat(responseCode).isEqualTo(201);
  }

  @Test
  @DisplayName("Should create skateboard via API")
  void shouldCreateSkateboard() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"SKATEBOARD\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();
    assertThat(responseCode).isEqualTo(201);
  }

  @Test
  @DisplayName("Should create electric scooter via API")
  void shouldCreateElectricScooter() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"ELECTRIC_SCOOTER\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();
    assertThat(responseCode).isEqualTo(201);
  }

  @Test
  @DisplayName("Should create quad via API")
  void shouldCreateQuad() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"QUAD\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();
    assertThat(responseCode).isEqualTo(201);
  }

  @Test
  @DisplayName("Should create snowmobile via API")
  void shouldCreateSnowmobile() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"SNOWMOBILE\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();
    assertThat(responseCode).isEqualTo(201);
  }

  @Test
  @DisplayName("Should create kayak via API")
  void shouldCreateKayak() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"KAYAK\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();
    assertThat(responseCode).isEqualTo(201);
  }

  @Test
  @DisplayName("Should create mountain bike via API")
  void shouldCreateMountainBike() throws Exception {
    final var url = new URL(baseUrl + "/vehicles");
    final var conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    final var json = "{\"type\":\"MOUNTAIN_BIKE\",\"state\":\"NEW\"}";
    try (final var os = conn.getOutputStream()) {
      os.write(json.getBytes(StandardCharsets.UTF_8));
    }

    final var responseCode = conn.getResponseCode();
    assertThat(responseCode).isEqualTo(201);
  }
}