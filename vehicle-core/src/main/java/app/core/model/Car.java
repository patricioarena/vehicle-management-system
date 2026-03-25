package app.core.model;

import app.core.component.motor.Motor;
import app.core.model.vo.State;
import lombok.Builder;

@Builder
public record Car(String typeVehicle, Motor motor, State state)
    implements Vehicle {}