## Context

`VehicleController.createVehicle` tiene tres bloques catch: `UnsupportedVehicleTypeException` (404), `IllegalArgumentException` (400) y `Exception` (500). Los tests de integración end-to-end en `VehicleApiIntegrationTest` levantan el stack real de Guice y no pueden inducir el tercer bloque sin modificar código de producción.

El threshold de verificación de JaCoCo en `vehicle-api` quedó fijo en 0.60 para evitar fallos por esa rama no cubierta.

## Goals / Non-Goals

**Goals:**
- Cubrir el bloque `catch(Exception)` de `VehicleController` con un test aislado usando Mockito
- Proveer una clase base reutilizable (`BaseControllerTest`) para futuros tests de controller con mock
- No modificar código de producción

**Non-Goals:**
- Reemplazar los tests de integración end-to-end existentes
- Subir el threshold de JaCoCo (eso es decisión separada)
- Cubrir otros métodos del controller (`healthCheck`, `getVehicleTypes`) en este change

## Decisions

### Decisión 1: Servidor Javalin real con mock de adapter (no MockMvc)

**Elegido:** Levantar un `Javalin` con puerto aleatorio (`:0`) e inyectar un `VehicleAdapter` mockeado directamente en el constructor de `VehicleController`.

**Alternativa descartada:** Framework de mocking HTTP (MockMvc, WireMock) — introduce dependencias adicionales y no es necesario para este stack.

**Rationale:** El constructor de `VehicleController` acepta `VehicleAdapter` directamente gracias a la inyección de dependencias. Pasar un mock es trivial y el servidor Javalin levanta en milisegundos con puerto 0. Esto ejerce el código real del controller (serialización, routing) sin el overhead del stack completo de Guice.

### Decisión 2: Clase base abstracta compartida

**Elegido:** `BaseControllerTest` abstracta con `@BeforeAll`/`@AfterAll` estáticos para ciclo de vida del servidor y `@BeforeEach` para reset de mocks.

**Rationale:** Patrón reutilizable si en el futuro se agregan tests para otros controllers. El reset en `@BeforeEach` garantiza aislamiento entre tests dentro de una misma clase.

### Decisión 3: `HttpResult` como record interno

El resultado de los helpers `get`/`post` se modela como `record HttpResult(int statusCode, String body)` dentro de `BaseControllerTest`. Simple y suficiente para las aserciones.

## Risks / Trade-offs

- **Puerto efímero**: Si el OS no libera el puerto inmediatamente entre suites, podría haber conflictos. El puerto `:0` (asignado por el OS) mitiga esto casi por completo.
- **Acoplamiento al constructor de `VehicleController`**: Si el controller cambia su firma de constructor, `BaseControllerTest` requiere actualización. Bajo riesgo dado el diseño estable del módulo.
