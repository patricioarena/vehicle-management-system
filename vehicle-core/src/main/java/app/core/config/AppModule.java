package app.core.config;

import app.core.component.vehicle.impl.VehicleAbstractFactoryImpl;
import app.core.usecase.VehicleAdapter;
import app.core.usecase.VehicleAdapterImpl;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import app.core.component.vehicle.VehicleFactory;
import app.core.component.vehicle.impl.CarFactoryImpl;
import app.core.component.vehicle.impl.LightSailboatFactoryImpl;

public class AppModule extends AbstractModule {

  @Override
  protected void configure() {

    bind(VehicleAdapter.class).to(VehicleAdapterImpl.class);

    bind(VehicleFactory.class).to(VehicleAbstractFactoryImpl.class);

    bind(VehicleFactory.class)
        .annotatedWith(Names.named("CarFactory"))
        .to(CarFactoryImpl.class);

    bind(VehicleFactory.class)
        .annotatedWith(Names.named("LightSailboatFactory"))
        .to(LightSailboatFactoryImpl.class);
  }
}