## Why

El bloque `catch (Exception e)` en `VehicleController.createVehicle` retorna HTTP 500 ante errores inesperados, pero los tests de integración end-to-end no pueden forzar ese camino porque dependen del stack real de Guice. Esto deja esa rama sin cobertura en JaCoCo, lo que genera ruido en los reportes y obliga a mantener el threshold de verificación artificialmente bajo en `vehicle-api`.

## What Changes

- Se agrega `BaseControllerTest`: clase base abstracta que levanta un servidor Javalin con puerto aleatorio e inyecta un `VehicleAdapter` mockeado, exponiendo helpers `get(path)` y `post(path, body)`.
- Se agrega `VehicleControllerTest`: test que extiende `BaseControllerTest` y configura el mock para lanzar `RuntimeException`, verificando que el controller responde HTTP 500.

## Capabilities

### New Capabilities

- `controller-exception-coverage`: Tests unitarios con mock que cubren el bloque `catch(Exception)` de `VehicleController`, garantizando respuesta HTTP 500 ante errores inesperados del adapter.

### Modified Capabilities

<!-- Ninguna: no cambia ningún comportamiento ni requisito existente -->

## Impact

- Nuevos archivos en `vehicle-api/src/test/java/app/api/`: `BaseControllerTest.java`, `VehicleControllerTest.java`
- La cobertura de `vehicle-api` sube al incluir el bloque `catch(Exception)` previamente no alcanzable
- No hay cambios en código de producción ni en APIs expuestas
