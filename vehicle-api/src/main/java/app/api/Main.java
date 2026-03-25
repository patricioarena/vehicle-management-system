/*
 * REST API Application using Javalin
 */
package app.api;

import app.api.config.ApiModule;
import app.api.controller.VehicleController;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

  public static void main(String[] args) {
    log.info("Starting Vehicle API...");

    // Create Guice injector with API module
    final Injector injector = Guice.createInjector(new ApiModule());

    // Get the controller with injected dependencies
    final VehicleController controller = injector.getInstance(VehicleController.class);

    // Configure and start Javalin
    final Javalin app = Javalin.create(config -> {
      config.showJavalinBanner = false;
      config.jsonMapper(new JavalinJackson().updateMapper(mapper ->
          mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)));
    }).start(8080);

    // Register routes
    controller.registerRoutes(app);

    log.info("API iniciada en http://localhost:8080");
    log.info("Available endpoints:");
    log.info("  GET  /health");
    log.info("  POST /vehicles/car");
    log.info("  POST /vehicles/sailboat");
    log.info("  GET  /vehicles/types");
  }
}
