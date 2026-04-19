package app.core.component.vehicle.impl;

import app.core.component.vehicle.VehicleFactory;
import app.core.constant.enums.StateVehicle;
import app.core.constant.enums.TypeVehicle;
import app.core.model.Vehicle;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * Fábrica general de vehículos con auto-registro desde el enum TypeVehicle.
 * Registra automáticamente una fábrica genérica para todos los tipos del enum
 * y permite sobreescribir con fábricas específicas mediante MapBinder.
 */
@Slf4j
@Singleton
public class VehicleAbstractFactoryImpl implements VehicleFactory {

    private final Map<TypeVehicle, VehicleFactory> factories;

    /**
     * Inicializa la fábrica registrando todos los tipos del enum.
     * Primero asigna la fábrica genérica a todos los tipos,
     * luego sobreescribe con fábricas específicas cuando existen.
     *
     * @param specificFactories mapa de fábricas específicas por tipo
     * @param genericFactory fábrica genérica como fallback
     */
    @Inject
    public VehicleAbstractFactoryImpl(
            final Map<TypeVehicle, VehicleFactory> specificFactories,
            final GenericVehicleFactoryImpl genericFactory) {

        factories = new EnumMap<>(TypeVehicle.class);

        // Registrar la factory genérica para TODOS los tipos del enum
        Arrays.stream(TypeVehicle.values())
            .forEach(type -> factories.put(type, genericFactory));

        // Sobreescribir con factories específicas cuando existan
        factories.putAll(specificFactories);

        log.info(
            "Factories initialized for {} vehicle types. "
                + "Specific overrides: {}",
            factories.size(),
            specificFactories.keySet()
        );
    }

    /**
     * Crea un vehículo basado en el tipo y estado proporcionados.
     *
     * @param vehicleType El tipo de vehículo a crear.
     * @param state El estado inicial del vehículo.
     * @return Una instancia del vehículo creado.
     */
    @Override
    public Vehicle apply(
            final TypeVehicle vehicleType,
            final StateVehicle state) {
        return factories.get(vehicleType).apply(vehicleType, state);
    }
}
