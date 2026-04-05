package app.api.controller;

import app.core.constant.enums.TypeVehicle;
import app.core.custom.exception.UnsupportedVehicleTypeException;
import app.core.model.VehicleCreateRequest;
import app.core.usecase.VehicleAdapter;
import com.google.inject.Inject;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VehicleController {

  private final VehicleAdapter vehicleAdapter;

  @Inject
  public VehicleController(final VehicleAdapter vehicleAdapter) {
    this.vehicleAdapter = vehicleAdapter;
  }

  public void registerRoutes(final Javalin app) {
    // Health check
    app.get("/health", this::healthCheck);

    // Vehicle creation endpoint
    app.post("/vehicles", this::createVehicle);

    // Vehicle types endpoint
    app.get("/vehicles/types", this::getVehicleTypes);

    log.info("Vehicle routes registered");
  }

  private void healthCheck(final Context ctx) {
    ctx.json(Map.of("status", "UP"));
    log.debug("Health check requested");
  }

  private void createVehicle(final Context ctx) {
    try {
      final var request = ctx.bodyAsClass(VehicleCreateRequest.class);

      if (request.type() == null || request.type().isBlank()) {
        ctx.status(400);
        ctx.json(Map.of("error", "Missing required field: type"));
        return;
      }

      if (request.state() == null || request.state().isBlank()) {
        ctx.status(400);
        ctx.json(Map.of("error", "Missing required field: state"));
        return;
      }

      final var vehicle = vehicleAdapter.createVehicle(request);
      log.info("Created vehicle via API: {}", vehicle);
      ctx.status(201);
      ctx.json(vehicle);

    } catch (final UnsupportedVehicleTypeException e) {
      log.error("Vehicle type not found: {}", e.getMessage());
      ctx.status(404);
      ctx.json(Map.of("error", e.getMessage()));
    } catch (final IllegalArgumentException e) {
      log.error("Invalid request: {}", e.getMessage());
      ctx.status(400);
      ctx.json(Map.of("error", e.getMessage()));
    } catch (final Exception e) {
      log.error("Error creating vehicle: {}", e.getMessage());
      ctx.status(500);
      ctx.json(Map.of("error", e.getMessage()));
    }
  }

  private void getVehicleTypes(final Context ctx) {
    final List<Map<String, Object>> types =
        Arrays.stream(TypeVehicle.values())
            .map(
                type ->
                    Map.<String, Object>of(
                        "name", type.name(),
                        "id", type.getId(),
                        "hasEngine", type.hasEngine(),
                        "drivingType", type.getDrivingType()))
            .toList();

    log.debug("Vehicle types requested, returning {} types", types.size());
    ctx.json(types);
  }
}
