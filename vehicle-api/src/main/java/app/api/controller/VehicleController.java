package app.api.controller;

import app.core.constant.enums.StateVehicle;
import app.core.constant.enums.TypeVehicle;
import app.core.usecase.VehicleAdapter;
import com.google.inject.Inject;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class VehicleController {

  private final VehicleAdapter vehicleAdapter;

  public void registerRoutes(Javalin app) {
    // Health check
    app.get("/health", this::healthCheck);

    // Vehicle creation endpoints
    app.post("/vehicles/car", this::createCar);
    app.post("/vehicles/sailboat", this::createSailboat);

    // Vehicle types endpoint
    app.get("/vehicles/types", this::getVehicleTypes);

    log.info("Vehicle routes registered");
  }

  private void healthCheck(Context ctx) {
    ctx.json(Map.of("status", "UP"));
    log.debug("Health check requested");
  }

  private void createCar(Context ctx) {
    try {
      final var car = vehicleAdapter.createCar(StateVehicle.NEW);
      log.info("Created car via API: {}", car);
      ctx.status(201);
      ctx.json(car);
    } catch (Exception e) {
      log.error("Error creating car: {}", e.getMessage());
      ctx.status(500);
      ctx.json(Map.of("error", e.getMessage()));
    }
  }

  private void createSailboat(Context ctx) {
    try {
      final var boat = vehicleAdapter.createLightSailboat(StateVehicle.NEW);
      log.info("Created sailboat via API: {}", boat);
      ctx.status(201);
      ctx.json(boat);
    } catch (Exception e) {
      log.error("Error creating sailboat: {}", e.getMessage());
      ctx.status(500);
      ctx.json(Map.of("error", e.getMessage()));
    }
  }

  private void getVehicleTypes(Context ctx) {
    final List<Map<String, Object>> types = Arrays.stream(TypeVehicle.values())
        .map(type -> Map.<String, Object>of(
            "name", type.name(),
            "id", type.getId(),
            "hasEngine", type.hasEngine(),
            "drivingType", type.getDrivingType()
        ))
        .toList();
    
    log.debug("Vehicle types requested, returning {} types", types.size());
    ctx.json(types);
  }
}
