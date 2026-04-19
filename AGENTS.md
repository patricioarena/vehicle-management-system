# AGENTS.md

Guidelines for AI agents working on this Guice dependency injection practice project.

## Build Commands

Gradle-based Java 21 multi-module project (Gradle wrapper included):

### Project Structure
- `vehicle-core` - Core domain logic, models, factories, and business rules
- `vehicle-cli` - Interactive command-line interface
- `vehicle-api` - REST API using Javalin

### Commands

```bash
# Build all modules
./gradlew build

# Clean build artifacts
./gradlew clean

# Run CLI application
./gradlew :vehicle-cli:run

# Run API server
./gradlew :vehicle-api:run

# Run all tests
./gradlew test

# Run core module tests only
./gradlew :vehicle-core:test

# Run single test class
./gradlew :vehicle-core:test --tests "app.core.MainTest"

# Run single test method
./gradlew :vehicle-core:test --tests "app.core.MainTest.appHasAGreeting"

# Check dependencies
./gradlew dependencies

# Build vehicle-core JAR
./gradlew :vehicle-core:jar
```

On Windows: use `gradlew.bat` instead of `./gradlew`.

### Checkstyle Validation

```bash
# Run Checkstyle on all modules
./gradlew checkstyleMain

# Run Checkstyle on test classes
./gradlew checkstyleTest

# Run all checks (including Checkstyle)
./gradlew check
```

Checkstyle está configurado con las siguientes reglas:
- **AvoidStaticImport**: Solo permite imports estáticos de `DrivingType.*` y `FuelType.*`
- **AvoidStarImport**: No permite imports wildcard (excepto los enums permitidos)
- **UnusedImports**: Detecta imports sin usar
- **IllegalImport**: Prohíbe `javax` y `org.jetbrains.annotations`
- **DiamondOperator**: Requiere operador diamante `<>`
- **MagicNumber**: Evita números mágicos (excepto -1, 0, 1, 2)
- **JavadocMethod**: Requiere Javadoc en métodos public y protected
- **NeedBraces**: Requiere llaves en bloques de control
- **LineLength**: Máximo 100 caracteres por línea
- **Indentation**: 4 espacios

### Jakarta over javax

- **Prefiere Jakarta EE** sobre javax
- Usa `jakarta.annotation.api.*` en lugar de `javax.annotation.*`
- Las dependencias de Jakarta ya están incluidas en vehicle-core

### Anotaciones Nullable/NonNull

- **Usa anotaciones de Jakarta**: `jakarta.annotation.Nullable` y `jakarta.annotation.Nonnull`
- **NO uses** anotaciones de JetBrains (`org.jetbrains.annotations.*`)
- Ejemplos correctos:
  ```java
  import jakarta.annotation.Nullable;
  import jakarta.annotation.Nonnull;
  ```

## Code Style Guidelines

### Language & Formatting
- **Java 21** - Use modern features (records, var, switch expressions, streams)
- **Streams over for** - Prefer Java Streams API over traditional for loops
- **Indent**: 4 spaces (not tabs)
- **Line length**: 100 characters max
- **Braces**: Same line (K&R style)

### Imports
- Group: java.*, jakarta.*, then third-party, then project
- Static imports last
- No wildcard imports (AvoidStarImport - except for static enum constants allowed by Checkstyle)
- Example order:
  ```java
  import java.util.List;
  import java.util.Map;
  import com.google.inject.Inject;
  import lombok.Builder;
  import app.core.component.VehicleFactory;
  import static app.core.constant.enums.FuelType.*;
  ```

### Naming Conventions
- **Classes**: PascalCase (e.g., `VehicleFactory`, `CarBuilder`)
- **Interfaces**: PascalCase, no prefix (e.g., `Vehicle`, `VehicleFactory`)
- **Methods**: camelCase, verbs (e.g., `createCar`, `buildEngine`)
- **Variables**: camelCase, `final` by default (e.g., `final var car = ...`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `EMPTY_MOTOR`)
- **Packages**: lowercase, single word (e.g., `app.model`, `app.config`)
- **Generic types**: Single uppercase letter (e.g., `T`, `K`, `V`)

### Types & Records
- Use **records** for data models with `@Builder`
- Use `final var` for local variables (type inference)
- Use `Optional<T>` for nullable returns
- Use `sealed` interfaces/classes where appropriate

### Dependency Injection (Guice)
- Use `@Inject` constructor injection
- Pattern: `@RequiredArgsConstructor(onConstructor = @__(@Inject))`
- Bindings in `AppModule` extending `AbstractModule`
- Use `MapBinder` for multiple factories (preferred over `@Named`)
- Prefer `@Singleton` for shared instances

### Factory Pattern
- `VehicleFactory` is a functional interface (`BiFunction<TypeVehicle, StateVehicle, Vehicle>`)
- `GenericVehicleFactoryImpl` - Default factory for all vehicle types, auto-registered for every TypeVehicle
- `VehicleAbstractFactoryImpl` - Uses Guice MapBinder for auto-registration:
  - All types from TypeVehicle enum get the generic factory by default
  - Specific factories override the generic one via MapBinder in AppModule
- To add specialized factory: use MapBinder in AppModule (no changes to VehicleAbstractFactoryImpl needed)

### Error Handling
- Use custom exceptions in `app.core.custom.exception`
- Example: `UnsupportedVehicleTypeException`
- Prefer unchecked exceptions for programming errors
- Validate inputs with `Objects.requireNonNull()`

### Testing (JUnit 5)
- Use `@ExtendWith(MockitoExtension.class)` for Mockito
- Implement `WithAssertions` for AssertJ static methods
- Test class naming: `{ClassUnderTest}Test`
- Test method naming: descriptive, camelCase or snake_case
- Use `@DisplayName` for human-readable test names
- Structure: Arrange-Act-Assert (AAA)

### Lombok Usage
- `@Slf4j` for logging (use `log.info()`, `log.debug()`)
- `@Builder` for complex object creation
- `@RequiredArgsConstructor` for DI
- `@Value` or records for immutable data
- Avoid `@Data` on entities

### Package Structure
```
app/
  core/
    component/     # Business logic, factories, motors
    config/        # Guice modules
    constant/      # Enums, constants
    custom/exception/  # Custom exceptions
    model/         # Domain models (records)
    model/vo/      # Value objects
    usecase/       # Application services, adapters
  cli/             # CLI application entry point
  api/             # REST API application entry point
```

### Logging
- Use SLF4J via `@Slf4j`
- Levels: ERROR, WARN, INFO (app flow), DEBUG (details)
- Template: `log.info("Created vehicle: {}", vehicle);`
- Never log sensitive data

### Comments & Documentation
- Javadoc for public APIs only
- Explain "why", not "what"
- Spanish comments allowed (existing codebase has some)
- Keep comments current with code changes

### Adding New Vehicle Types

#### Simple Method (Generic Vehicle)
For vehicle types that don't require specialized behavior:

1. Add type to `TypeVehicle` enum with id, hasEngine, drivingType
2. That's it! The generic factory handles it automatically via auto-registration

#### Specialized Factory Method
For vehicle types requiring custom creation logic:

1. Create model record in `app.core.model` with `@Builder`
2. Create factory in `app.core.component.vehicle.impl`
3. Add type to `TypeVehicle` enum with id, hasEngine, drivingType
4. Add MapBinder binding in `AppModule`:
   ```java
   factoryBinder.addBinding(TypeVehicle.YOUR_TYPE).to(YourFactoryImpl.class);
   ```
5. Add adapter method in `VehicleAdapter`/`VehicleAdapterImpl`

## Key Dependencies

### Core Dependencies (vehicle-core)
- Guice 7.0.0 - Dependency injection
- Lombok 1.18.30 - Code generation
- SLF4J + Logback - Logging
- Apache Commons Lang3 - Utilities

### Testing (All modules)
- JUnit 5 + AssertJ + Mockito - Testing

### API Dependencies (vehicle-api)
- Javalin 6.1.3 - Web framework
- Jackson 2.16.0 - JSON serialization
