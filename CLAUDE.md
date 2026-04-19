# CLAUDE.md

## Project Overview

Vehicle Management System - A Java 21 multi-module Gradle project demonstrating dependency injection with Google Guice, factory patterns, and clean architecture.

**Modules:**
- `vehicle-core` - Core business logic library (models, factories, adapters, DI config)
- `vehicle-cli` - Interactive console application (`app.cli.Main`)
- `vehicle-api` - REST API with Javalin on port 8080 (`app.api.Main`)

## Build & Run

```bash
# Build all modules
./gradlew build

# Run tests
./gradlew test                          # all modules
./gradlew :vehicle-core:test            # core only
./gradlew :vehicle-core:test --tests "app.core.mapper.TypeVehicleMapperTest"  # single class

# Run applications
./gradlew :vehicle-api:run              # REST API on localhost:8080
./gradlew :vehicle-cli:run              # Interactive CLI

# Code quality
./gradlew checkstyleMain                # Checkstyle on main sources
./gradlew checkstyleTest                # Checkstyle on test sources
./gradlew check                         # All checks (Checkstyle + tests)

# On Windows: use gradlew.bat instead of ./gradlew
```

## Code Style (enforced by Checkstyle)

- **Java 21** with modern features (records, var, switch expressions, streams)
- **4-space indentation**, max **100 chars/line**, K&R braces
- **No wildcard imports** except `DrivingType.*` and `FuelType.*` (static)
- **Jakarta EE over javax** - use `jakarta.inject.Inject`, `jakarta.annotation.Nullable`
- **No magic numbers** except -1, 0, 1, 2
- **Javadoc required** for public and protected methods
- **Diamond operator** required for generics
- Records with `@Builder` for data models; `@Slf4j` for logging
- DI pattern: `@RequiredArgsConstructor(onConstructor = @__(@Inject))`

See `AGENTS.md` for full code style guidelines, naming conventions, and architectural patterns.

## Architecture & Patterns

**Design patterns used:** Dependency Injection (Guice), Factory, Abstract Factory, Adapter, Singleton, Builder, Enum Strategy, Type Map (EnumMap)

**Factory Pattern:**
- `GenericVehicleFactoryImpl` - Default factory for all vehicle types
- `VehicleAbstractFactoryImpl` - Uses Guice MapBinder for auto-registration of specific factories
- MapBinder allows adding specialized factories without modifying the abstract factory class

**Package structure:**
```
app.core.component.vehicle.impl/  # Vehicle factories
app.core.component.motor/         # Motor implementations
app.core.config/                  # Guice modules (AppModule)
app.core.constant.enums/          # TypeVehicle (49 types), StateVehicle, FuelType
app.core.model/                   # Domain records (Car, LightSailboat, Vehicle interface)
app.core.model.vo/                # Value objects (State)
app.core.mapper/                  # Type mappers
app.core.usecase/                 # VehicleAdapter (business logic orchestration)
app.core.custom.exception/        # Custom exceptions
app.api.controller/               # REST endpoint handlers
app.api.config/                   # API-specific Guice bindings (ApiModule)
```

## Testing

- **Frameworks:** JUnit 5, AssertJ, Mockito
- **Pattern:** Arrange-Act-Assert with `@DisplayName` annotations
- **Assertions:** Use AssertJ fluent style (`assertThat(...).hasValue(...)`)
- **Naming:** `{ClassUnderTest}Test` classes, descriptive method names
- Tests live in `src/test/java/` mirroring the main source structure

## API Endpoints

```
GET  /health          # Health check
POST /vehicles        # Create vehicle (JSON: {"type": "car", "state": "NEW"})
GET  /vehicles/types  # List all vehicle types
```

## Git Conventions

- **Commit format:** `type(scope): description` (conventional commits)
- **Types:** feat, fix, docs, style, refactor, test, chore
- **Branch:** single `main` branch
- **Pre-commit hooks:** trailing whitespace, EOF fixer, YAML/TOML validation, Java auto-formatting

## Key Dependencies

| Dependency | Version | Purpose |
|-----------|---------|---------|
| Guice | 7.0.0 | Dependency injection |
| Lombok | 1.18.30 | Code generation (@Slf4j, @Builder) |
| Javalin | 5.6.3 | REST framework (vehicle-api) |
| Jackson | 2.15.2 | JSON serialization |
| SLF4J + Logback | 2.0.9 / 1.4.11 | Logging |
| JUnit 5 | 5.10.1 | Testing |
| AssertJ | 3.24.2 | Fluent assertions |
| Mockito | 5.8.0 | Mocking |
| Checkstyle | 10.12.5 | Code style enforcement |

## Adding New Vehicle Types

### Simple Method (Generic Vehicle)
For vehicle types that don't require specialized behavior:

1. Add type to `TypeVehicle` enum with id, hasEngine, drivingType
2. That's it! The generic factory handles it automatically

### Specialized Factory Method
For vehicle types requiring custom creation logic:

1. Create model record in `app.core.model` with `@Builder`
2. Create factory in `app.core.component.vehicle.impl`
3. Add type to `TypeVehicle` enum with id, hasEngine, drivingType
4. Add MapBinder binding in `AppModule`:
   ```java
   factoryBinder.addBinding(TypeVehicle.YOUR_TYPE).to(YourFactoryImpl.class);
   ```
5. Add adapter method in `VehicleAdapter`/`VehicleAdapterImpl`
