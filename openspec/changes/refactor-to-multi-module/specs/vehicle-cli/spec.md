## ADDED Requirements

### Requirement: CLI provides interactive vehicle creation menu
The vehicle-cli module SHALL provide a console-based interactive menu allowing users to create vehicles through the VehicleAdapter.

#### Scenario: Display menu options
- **WHEN** the CLI application starts
- **THEN** it SHALL display: "1. Crear un coche", "2. Crear un velero ligero", "3. Salir"

#### Scenario: Create car via menu
- **WHEN** user selects option 1
- **THEN** the CLI SHALL call `vehicleAdapter.createCar(StateVehicle.NEW)` and display the created car

#### Scenario: Create sailboat via menu
- **WHEN** user selects option 2
- **THEN** the CLI SHALL call `vehicleAdapter.createLightSailboat(StateVehicle.NEW)` and display the created sailboat

#### Scenario: Exit application
- **WHEN** user selects option 3
- **THEN** the CLI SHALL terminate gracefully

### Requirement: CLI uses Guice for dependency injection
The vehicle-cli SHALL use Google Guice to inject the VehicleAdapter, using the same AppModule from vehicle-core.

#### Scenario: Guice injector setup
- **WHEN** Main.java in vehicle-cli executes
- **THEN** it SHALL create a Guice injector with `new AppModule()`

#### Scenario: VehicleAdapter injection
- **WHEN** the menu handler needs to create vehicles
- **THEN** VehicleAdapter SHALL be injected via constructor or field injection

### Requirement: CLI runs via Gradle application plugin
The vehicle-cli SHALL be runnable via `./gradlew :vehicle-cli:run`.

#### Scenario: Execute CLI via Gradle
- **WHEN** developer runs `./gradlew :vehicle-cli:run`
- **THEN** the interactive menu SHALL start and accept user input
