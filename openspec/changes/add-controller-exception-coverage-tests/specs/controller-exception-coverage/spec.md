## ADDED Requirements

### Requirement: Controller retorna HTTP 500 ante excepción inesperada del adapter
Cuando `VehicleAdapter.createVehicle` lanza una excepción no tipada (`RuntimeException` o subclase), el sistema SHALL responder con HTTP 500 y un body JSON con la clave `error`.

#### Scenario: RuntimeException en el adapter produce respuesta 500
- **WHEN** se envía `POST /vehicles` con payload válido `{"type":"CAR","state":"NEW"}` y el adapter está configurado para lanzar `RuntimeException("unexpected error")`
- **THEN** el controller responde con HTTP 500

### Requirement: Infraestructura de test con mock reutilizable para controllers
El módulo `vehicle-api` SHALL proveer una clase base abstracta de test (`BaseControllerTest`) que levante un servidor Javalin con `VehicleAdapter` mockeado, de modo que los tests de controller puedan ejecutarse sin el stack real de Guice.

#### Scenario: Servidor levanta en puerto aleatorio antes de los tests
- **WHEN** una clase de test extiende `BaseControllerTest`
- **THEN** el servidor Javalin inicia con puerto `:0` (asignado por el OS) antes del primer test y se detiene después del último

#### Scenario: Mock se resetea entre tests
- **WHEN** se ejecutan múltiples tests en la misma clase
- **THEN** el mock de `VehicleAdapter` se resetea antes de cada test para garantizar aislamiento
