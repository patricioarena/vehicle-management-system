package app.api.config;

import app.core.config.AppModule;
import com.google.inject.AbstractModule;

public class ApiModule extends AbstractModule {

  @Override
  protected void configure() {
    // Install the core module to get all business bindings
    install(new AppModule());
    
    // Additional API-specific bindings can be added here
  }
}
