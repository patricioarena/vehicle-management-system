## ADDED Requirements

### Requirement: REST server uses Javalin framework
The vehicle-api SHALL use Javalin version 5.6.3 or compatible as the REST framework.

#### Scenario: Javalin dependency present
- **WHEN** vehicle-api/build.gradle is examined
- **THEN** it SHALL declare `implementation 'io.javalin:javalin:5.6.3'`

#### Scenario: Javalin application creation
- **WHEN** the API starts
- **THEN** it SHALL use `Javalin.create()` to configure and start the server

### Requirement: Server configuration disables banner
The Javalin server SHALL be configured with `showJavalinBanner = false`.

#### Scenario: Server starts without banner
- **WHEN** the API application starts
- **THEN** no Javalin ASCII art banner SHALL appear in console output

### Requirement: Server listens on port 8080
The Javalin server SHALL listen on port 8080 by default.

#### Scenario: Default port configuration
- **WHEN** `./gradlew :vehicle-api:run` executes
- **THEN** Javalin SHALL call `.start(8080)`

### Requirement: Server gracefully handles requests
The Javalin server SHALL handle REST requests without errors and return appropriate HTTP status codes.

#### Scenario: Successful request handling
- **WHEN** valid requests are made to defined endpoints
- **THEN** server SHALL return HTTP 200/201 with valid JSON

#### Scenario: Error handling
- **WHEN** unexpected errors occur in request processing
- **THEN** server SHALL return HTTP 500 with error details
