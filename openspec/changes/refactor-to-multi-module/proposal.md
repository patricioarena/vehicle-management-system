## Why

The current project structure uses a single monolithic Gradle module that combines business logic (Guice modules, factories, models, adapters) with application entry points (Main class, console menu). This coupling makes it impossible to deploy the business logic independently or add multiple application interfaces (CLI, REST API) without duplicating code.

Separating into multi-modules will:
- Allow the core business logic to be tested and deployed independently
- Enable multiple application entry points (CLI for testing, REST API for production) sharing the same core
- Keep Guice DI configuration reusable across all modules
- Make the project structure scalable for future vehicle types and application types

## What Changes

**BREAKING**: Restructure entire project from single `app` module to multi-module Gradle structure

1. **New Module: `vehicle-core`** (java-library plugin)
   - Contains all business logic: models, factories, motors, adapters, Guice bindings
   - Reusable across all application types
   - No Main classes or framework dependencies

2. **New Module: `vehicle-cli`** (application plugin)
   - Console application for interactive testing
   - Interactive menu (Scanner-based) with options to create vehicles
   - Depends on vehicle-core, provides CLI entry point

3. **New Module: `vehicle-api`** (application plugin)
   - REST API using Javalin framework
   - Endpoints: POST /vehicles/car, POST /vehicles/sailboat, GET /vehicles/types, GET /health
   - Jackson for JSON serialization (works with Java records automatically)
   - Depends on vehicle-core

4. **Build Configuration Updates**
   - New `settings.gradle` with all 3 modules
   - Root `build.gradle` with common configuration
   - Each module has its own dependencies (Javalin only in api, picocli optional in cli)

5. **Code Migration**
   - Move `app/src/main/java/app/` content to `vehicle-core/src/main/java/app/`
   - Create new package structures: `app.cli` and `app.api`
   - Refactor Main.java into two separate entry points
   - Keep all existing business logic unchanged

## Capabilities

### New Capabilities

- `multi-module-structure`: Gradle multi-module configuration with dependency management
- `vehicle-cli`: Console application for interactive vehicle creation and testing
- `vehicle-api`: REST API endpoints for vehicle management using Javalin
- `javalin-rest-server`: Lightweight REST server configuration with Guice integration

### Modified Capabilities

- None (no existing capabilities in openspec/specs/)

## Impact

**Affected Code:**
- `app/` directory will be removed and replaced with 3 new module directories
- `settings.gradle` complete rewrite
- New `build.gradle` at root level with common configuration
- `vehicle-core/src/main/java/app/` - moved from current `app/src/main/java/app/`
- `vehicle-core/src/test/java/app/` - moved from current `app/src/test/java/app/`

**API Changes:**
- New REST endpoints exposed (no existing API to break)
- CLI interface remains similar but in separate module

**Dependencies:**
- Add: `io.javalin:javalin:5.6.3` (only in vehicle-api)
- Add: `com.fasterxml.jackson.core:jackson-databind:2.15.2` (only in vehicle-api)
- Keep: Guice, Lombok, JUnit5, AssertJ, Mockito in vehicle-core
- All existing dependencies remain available to vehicle-core

**Build Commands:**
- `./gradlew build` - builds all modules
- `./gradlew :vehicle-cli:run` - runs console application
- `./gradlew :vehicle-api:run` - starts REST server on port 8080
- `./gradlew :vehicle-core:test` - runs unit tests for business logic

**Project Structure:**
```
guice-practice/
├── build.gradle              # Common configuration
├── settings.gradle           # All 3 modules
├── vehicle-core/
│   ├── build.gradle          # java-library, deps: guice, lombok, etc.
│   └── src/main/java/app/    # All current code moved here
├── vehicle-cli/
│   ├── build.gradle          # application, deps: vehicle-core
│   └── src/main/java/app/cli/Main.java
└── vehicle-api/
    ├── build.gradle          # application, deps: vehicle-core, javalin, jackson
    └── src/main/java/app/api/
        ├── Main.java
        ├── config/ApiModule.java
        └── controller/VehicleController.java
```
