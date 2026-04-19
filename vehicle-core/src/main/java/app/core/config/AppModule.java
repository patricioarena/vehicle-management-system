package app.core.config;

import app.core.component.vehicle.VehicleFactory;
import app.core.component.vehicle.impl.CarFactoryImpl;
import app.core.component.vehicle.impl.LightSailboatFactoryImpl;
import app.core.component.vehicle.impl.VehicleAbstractFactoryImpl;
import app.core.constant.enums.TypeVehicle;
import app.core.usecase.VehicleAdapter;
import app.core.usecase.VehicleAdapterImpl;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;

/**
 * Módulo de configuración Guice para la inyección de dependencias.
 * Usa MapBinder para registrar fábricas específicas por tipo de vehículo.
 */
public class AppModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(VehicleAdapter.class).to(VehicleAdapterImpl.class);

        bind(VehicleFactory.class).to(VehicleAbstractFactoryImpl.class);

        // MapBinder para factories específicas por tipo de vehículo.
        // Para agregar soporte especial a un tipo, solo agregar una línea aquí.
        final var factoryBinder = MapBinder.newMapBinder(
            binder(), TypeVehicle.class, VehicleFactory.class);

        factoryBinder.addBinding(TypeVehicle.CAR)
            .to(CarFactoryImpl.class);

        factoryBinder.addBinding(TypeVehicle.LIGHT_SAILBOAT)
            .to(LightSailboatFactoryImpl.class);
    }
}
