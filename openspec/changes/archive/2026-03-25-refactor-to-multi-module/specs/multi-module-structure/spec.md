## ADDED Requirements

### Requirement: Project uses Gradle multi-module structure
The project SHALL be organized into 3 Gradle modules: vehicle-core (java-library), vehicle-cli (application), and vehicle-api (application).

#### Scenario: Module dependencies are configured
- **WHEN** developer runs `./gradlew projects`
- **THEN** all 3 modules SHALL be listed with correct paths

#### Scenario: Core module is reusable
- **WHEN** vehicle-cli and vehicle-api define dependencies
- **THEN** both SHALL use `implementation project(':vehicle-core')` to consume the library

### Requirement: vehicle-core contains business logic only
The vehicle-core module SHALL contain all domain logic including Guice modules, factories, motors, models, and adapters, but SHALL NOT contain Main classes or framework dependencies.

#### Scenario: Core module builds as library
- **WHEN** `./gradlew :vehicle-core:build` is executed
- **THEN** it SHALL produce a JAR file without application plugin

#### Scenario: Core has no application entry points
- **WHEN** searching for main methods in vehicle-core source
- **THEN** no classes with `public static void main` SHALL exist

### Requirement: Module dependencies are strictly hierarchical
The dependency graph SHALL enforce: cli → core and api → core only. There SHALL be no circular dependencies or dependencies from core to api/cli.

#### Scenario: No circular dependencies
- **WHEN** `./gradlew build` runs
- **THEN** no circular dependency errors SHALL occur

#### Scenario: Core independence
- **WHEN** vehicle-core build.gradle is examined
- **THEN** no references to vehicle-cli or vehicle-api SHALL exist
