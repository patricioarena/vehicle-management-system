package app.core.model;

import app.core.component.motor.Motor;
import app.core.model.vo.State;
import lombok.Builder;

/**
 * Modelo genérico de vehículo que soporta cualquier tipo definido en TypeVehicle.
 * Para tipos que no requieren un record especializado, este modelo sirve como base.
 */
@Builder
public record GenericVehicle(
    String name,
    String typeVehicle,
    Motor motor,
    State state
) implements Vehicle {}
