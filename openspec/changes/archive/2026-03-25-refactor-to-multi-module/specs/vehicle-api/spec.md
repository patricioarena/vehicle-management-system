## ADDED Requirements

### Requirement: API exposes REST endpoints for vehicle management
The vehicle-api module SHALL expose REST endpoints using Javalin framework for creating and retrieving vehicles.

#### Scenario: Health check endpoint
- **WHEN** a GET request is made to `/health`
- **THEN** the API SHALL return HTTP 200 with JSON body `{"status":"UP"}`

#### Scenario: Create car endpoint
- **WHEN** a POST request is made to `/vehicles/car`
- **THEN** the API SHALL create a new car via VehicleAdapter and return HTTP 201 with the car as JSON

#### Scenario: Create sailboat endpoint
- **WHEN** a POST request is made to `/vehicles/sailboat`
- **THEN** the API SHALL create a new LightSailboat via VehicleAdapter and return HTTP 201 with the sailboat as JSON

#### Scenario: Get vehicle types endpoint
- **WHEN** a GET request is made to `/vehicles/types`
- **THEN** the API SHALL return HTTP 200 with a JSON array of all TypeVehicle enum values

### Requirement: API uses Jackson for JSON serialization
The vehicle-api SHALL use Jackson library to serialize Java records (Car, LightSailboat) to JSON automatically.

#### Scenario: Car record serialization
- **WHEN** a car is returned from the API
- **THEN** the JSON SHALL contain all car fields: type, motor, and state

#### Scenario: Sailboat record serialization
- **WHEN** a sailboat is returned from the API
- **THEN** the JSON SHALL contain all sailboat fields: type, motor, and state

### Requirement: API integrates Guice with Javalin
The vehicle-api SHALL use Google Guice for dependency injection of controllers and adapters, integrating with Javalin's routing.

#### Scenario: ApiModule configuration
- **WHEN** ApiModule is configured
- **THEN** it SHALL install AppModule from vehicle-core and expose VehicleController

#### Scenario: Controller injection
- **WHEN** VehicleController is instantiated
- **THEN** VehicleAdapter SHALL be injected via constructor injection

### Requirement: API runs via Gradle application plugin
The vehicle-api SHALL be runnable via `./gradlew :vehicle-api:run` and listen on port 8080.

#### Scenario: Start API server
- **WHEN** developer runs `./gradlew :vehicle-api:run`
- **THEN** Javalin SHALL start and listen on port 8080
- **AND** log message SHALL indicate "API iniciada en http://localhost:8080"
