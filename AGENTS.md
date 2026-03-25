# AGENTS.md

Guidelines for AI agents working on this Guice dependency injection practice project.

## Build Commands

Gradle-based Java 17 project (Gradle wrapper included):

```bash
# Build
./gradlew build

# Run application
./gradlew run

# Run all tests
./gradlew test

# Run single test class
./gradlew test --tests "app.MainTest"

# Run single test method
./gradlew test --tests "app.MainTest.appHasAGreeting"

# Clean build artifacts
./gradlew clean

# Check dependencies
./gradlew dependencies
```

On Windows: use `gradlew.bat` instead of `./gradlew`.

## Code Style Guidelines

### Language & Formatting
- **Java 17** - Use modern features (records, var, switch expressions)
- **Indent**: 2 spaces (not tabs)
- **Line length**: 100 characters max
- **Braces**: Same line (K&R style)

### Imports
- Group: java.*, then javax.*, then third-party, then project
- Static imports last
- No wildcard imports (except for static enums: `import static app.constant.DrivingType.*;`)
- Example order:
  ```java
  import java.util.*;
  import com.google.inject.*;
  import lombok.*;
  import app.component.*;
  import static app.constant.enums.FuelType.*;
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
- Use `@Named("FactoryName")` for multiple bindings
- Prefer `@Singleton` for shared instances

### Factory Pattern
- `VehicleFactory` is a functional interface (BiFunction equivalent)
- Concrete factories implement `VehicleFactory`
- Register new factories in `VehicleAbstractFactoryImpl` EnumMap
- Add named binding in `AppModule`

### Error Handling
- Use custom exceptions in `app.custom.exception`
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
  component/     # Business logic, factories, motors
  config/        # Guice modules
  constant/      # Enums, constants
  custom/exception/  # Custom exceptions
  model/         # Domain models (records)
  model/vo/      # Value objects
  usecase/       # Application services, adapters
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
1. Create model record in `app.model` with `@Builder`
2. Create factory in `app.component.vehicle.impl`
3. Add type to `TypeVehicle` enum with id, hasEngine, drivingType
4. Register factory in `VehicleAbstractFactoryImpl` constructor
5. Add `@Named` binding in `AppModule`
6. Add adapter method in `VehicleAdapter`/`VehicleAdapterImpl`

## Key Dependencies

- Guice 7.0.0 - Dependency injection
- Lombok 1.18.30 - Code generation
- JUnit 5 + AssertJ + Mockito - Testing
- SLF4J + Logback - Logging
- Apache Commons Lang3 - Utilities
