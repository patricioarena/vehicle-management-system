package app.core.component.vehicle.impl;

import app.core.component.motor.Motor;
import app.core.component.vehicle.VehicleFactory;
import app.core.constant.enums.StateVehicle;
import app.core.constant.enums.TypeVehicle;
import app.core.model.GenericVehicle;
import app.core.model.Vehicle;
import app.core.model.vo.State;
import jakarta.inject.Singleton;

/**
 * Fábrica genérica que puede construir un vehículo para cualquier TypeVehicle.
 * Utiliza GenericVehicle como modelo base, asignando motor según la propiedad
 * hasEngine del tipo de vehículo.
 */
@Singleton
public class GenericVehicleFactoryImpl implements VehicleFactory {

    /**
     * Crea un vehículo genérico basado en el tipo y estado proporcionados.
     *
     * @param typeVehicle el tipo de vehículo a crear
     * @param stateVehicle el estado inicial del vehículo
     * @return una instancia de GenericVehicle
     */
    @Override
    public Vehicle apply(
            final TypeVehicle typeVehicle,
            final StateVehicle stateVehicle) {

        final var builder = GenericVehicle.builder()
            .name(typeVehicle.name())
            .typeVehicle(typeVehicle.getDrivingType())
            .state(State.buildFrom(stateVehicle));

        if (typeVehicle.hasEngine()) {
            builder.motor(Motor.empty());
        }

        return builder.build();
    }
}
