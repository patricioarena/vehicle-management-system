## Context

**Current State:**
The project is a single Gradle module (`app`) that contains:
- Business logic: Guice bindings (`AppModule`), vehicle factories (`CarFactoryImpl`, `LightSailboatFactoryImpl`), motors (`CombustionMotor`, `ElectricMotor`), models (`Car`, `LightSailboat` records), and adapters (`VehicleAdapter`, `VehicleAdapterImpl`)
- Application entry point: `Main.java` with direct vehicle creation and a commented-out interactive menu
- Everything is coupled in one module, making it impossible to reuse the business logic independently

**Constraints:**
- Must preserve existing Guice DI configuration (`AppModule.java`)
- Must keep all current business logic unchanged (no refactoring of factories, motors, or models)
- Must maintain Java 17, Gradle 7.3, Lombok, JUnit5 stack
- Must follow existing code style: 2-space indentation, K&R braces, final var, records with @Builder
- Vehicle creation flow must remain: Injector → AppModule → VehicleAdapterImpl → VehicleAbstractFactoryImpl → Specific Factory → Vehicle

**Stakeholders:**
- Developers extending vehicle types
- API consumers (future)
- CLI users (testing/debugging)

## Goals / Non-Goals

**Goals:**
1. Separate business logic from application entry points
2. Enable multiple application interfaces (CLI, REST) sharing the same core
3. Make vehicle-core reusable as a library dependency
4. Maintain existing vehicle creation patterns and Guice configuration
5. Provide REST API using Javalin (lightweight, compatible with Guice)
6. Keep interactive CLI for testing and development

**Non-Goals:**
1. Refactoring business logic (factories, motors, models remain unchanged)
2. Adding new vehicle types (only reorganizing existing CAR and LIGHT_SAILBOAT)
3. Database persistence (keep in-memory only)
4. Authentication/authorization
5. Changing dependency injection framework (keep Guice)
6. Docker/containerization
7. picocli integration (not required, Scanner is sufficient)

## Decisions

### 1. Multi-Module Gradle Structure
**Decision:** Create 3 modules: `vehicle-core` (java-library), `vehicle-cli` (application), `vehicle-api` (application)

**Rationale:**
- `java-library` plugin for core allows it to be published/reused as JAR
- `application` plugin for cli/api provides runnable distributions
- Clear separation of concerns: core has no framework dependencies, api/cli depend on core
- Gradle's `implementation project(':vehicle-core')` handles dependency graph

**Alternatives Considered:**
- Single module with source sets (`src/main`, `src/cli`, `src/api`): Rejected because doesn't enable independent deployment of core
- Maven multi-module: Rejected because project already uses Gradle

### 2. Javalin over Spring Boot for REST
**Decision:** Use Javalin 5.6.3 + Jackson for REST API

**Rationale:**
- **Guice Compatibility**: Javalin is DI-agnostic, Spring Boot brings its own DI container that conflicts with Guice
- **Lightweight**: Startup ~100ms vs several seconds for Spring Boot
- **Simplicity**: No auto-configuration magic, explicit control over Guice injector setup
- **Learning Value**: Reinforces manual DI understanding vs Spring's abstraction

**Alternatives Considered:**
- Spring Boot: Rejected due to DI conflicts and heavyweight nature
- Micronaut: Rejected - less familiar, adds complexity
- Spark Java: Rejected - older, less maintained than Javalin
- Vert.x: Rejected - reactive model, steeper learning curve

### 3. Package Naming Convention
**Decision:** Use `app.core.*` for core (moved from `app.*`), `app.cli.*` for CLI, `app.api.*` for API

**Rationale:**
- Avoids package conflicts when core is consumed by cli/api
- Clear ownership: `app.core.component` vs `app.api.controller`
- Aligns with Java package naming best practices for multi-module projects

**Alternatives Considered:**
- Keep `app.*` for core, `app.cli` and `app.api` as subpackages: Rejected - less clear separation
- Different root packages (`com.example.core`, `com.example.cli`): Rejected - inconsistent with existing codebase style

### 4. Guice Module Composition
**Decision:** `ApiModule` installs `AppModule` (core), `CliModule` also installs `AppModule`

**Rationale:**
- `AppModule` contains all business bindings (factories, adapters)
- Application modules only add framework-specific bindings (Javalin routes, CLI handlers)
- Single source of truth for business DI configuration

**Implementation:**
```java
// vehicle-api
public class ApiModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new AppModule()); // From vehicle-core
    // Additional API bindings here
  }
}
```

### 5. Testing Strategy
**Decision:** Tests distributed per module - unit tests in vehicle-core, integration tests in cli/api

**Rationale:**
- Unit tests for factories, motors, adapters stay close to implementation in vehicle-core
- Integration tests in cli/api verify end-to-end flow with real Guice injector
- No separate test module needed - overkill for this project size

**Alternatives Considered:**
- Centralized test module: Rejected - adds unnecessary complexity

### 6. JSON Serialization
**Decision:** Use Jackson with default configuration (works with Java 17 records automatically)

**Rationale:**
- Jackson is Javalin's default JSON mapper
- Java records serialize/deserialize automatically with field names as JSON keys
- No custom serializers needed for `Car` or `LightSailboat` records

**Configuration:**
```java
// In VehicleController
ctx.json(car); // Jackson auto-serializes record to JSON
```

## Risks / Trade-offs

### Risk: Breaking existing build/setup
**Impact:** High - developers may have scripts/workflows depending on current structure
**Mitigation:** 
- Clear documentation in README.md about new module structure
- Update all build commands in documentation
- Provide migration guide for IDE configurations (IntelliJ module setup)

### Risk: Circular dependencies between modules
**Impact:** Medium - Gradle build failures, confusing error messages
**Mitigation:**
- Strictly enforce: api → core, cli → core only
- No dependencies from core to api/cli
- Code review checklist for module dependencies

### Risk: Javalin version compatibility with Java 17
**Impact:** Low - Javalin 5.x supports Java 11+
**Mitigation:**
- Use Javalin 5.6.3 (latest stable in v5)
- Test startup on Java 17 before committing

### Risk: IDE confusion with same package names
**Impact:** Medium - IntelliJ may show duplicate classes if modules not imported correctly
**Mitigation:**
- Import entire project in IntelliJ (not just app/ directory)
- Gradle tool window will show all 3 modules
- Use "Reimport Gradle project" after structural changes

### Trade-off: Increased build complexity vs. flexibility
**Acceptance:** Yes - Multi-module builds are slightly slower and more complex than single module, but the flexibility to deploy core independently and add new application types justifies the trade-off.

### Trade-off: Javalin learning curve vs. Spring familiarity
**Acceptance:** Yes - Team may be less familiar with Javalin than Spring Boot, but the Guice compatibility and lightweight nature make it the right choice for this architecture.

## Migration Plan

**Phase 1: Setup (No code changes)**
1. Create 3 module directories: `vehicle-core/`, `vehicle-cli/`, `vehicle-api/`
2. Write `settings.gradle` with all 3 modules
3. Write root `build.gradle` with common configuration
4. Create module-specific `build.gradle` files with correct dependencies
5. Verify `./gradlew projects` shows all 3 modules

**Phase 2: Core Migration**
1. Copy `app/src/main/java/app/` → `vehicle-core/src/main/java/app/core/`
2. Copy `app/src/test/java/app/` → `vehicle-core/src/test/java/app/core/`
3. Update package declarations in all files: `package app.core.component;` etc.
4. Verify `./gradlew :vehicle-core:build` passes

**Phase 3: CLI Application**
1. Create `vehicle-cli/src/main/java/app/cli/Main.java`
2. Move menu logic from old Main.java (refactor package and imports)
3. Add Guice injector setup with CoreModule
4. Verify `./gradlew :vehicle-cli:run` works

**Phase 4: API Application**
1. Create `vehicle-api/src/main/java/app/api/Main.java`
2. Create `ApiModule.java` that installs `AppModule`
3. Create `VehicleController.java` with Javalin routes
4. Verify `./gradlew :vehicle-api:run` and test endpoints with curl/browser

**Phase 5: Cleanup**
1. Delete old `app/` directory
2. Verify `./gradlew build` passes for all modules
3. Update README.md with new structure and commands
4. Verify all tests pass: `./gradlew test`

**Rollback:**
- If issues arise, restore from git: `git checkout -- app/` and delete new module directories
- Or keep old app/ directory until validation complete, then delete

## Open Questions

1. **Should vehicle-cli use picocli for better CLI experience?**
   - Current proposal uses Scanner-based menu (simple)
   - Picocli adds dependency but provides better UX
   - Decision: Defer to future enhancement (Scanner sufficient for now)

2. **Should vehicle-core expose a ServiceLoader for auto-discovery?**
   - Currently factories are registered in VehicleAbstractFactoryImpl constructor
   - ServiceLoader would enable plugin-like architecture
   - Decision: Keep current EnumMap approach (explicit registration is clearer)

3. **Should we add integration tests in vehicle-api that verify JSON structure?**
   - Proposal includes integration tests but not specific JSON schema validation
   - Decision: Add basic JSON assertions (e.g., check field names exist)

4. **Port configuration for vehicle-api?**
   - Currently hardcoded to 8080
   - Should support env var or config file?
   - Decision: Hardcoded 8080 for simplicity, add configuration later if needed
