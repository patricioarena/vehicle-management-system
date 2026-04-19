## 1. Clase base de tests de controller

- [x] 1.1 Crear `BaseControllerTest.java` en `vehicle-api/src/test/java/app/api/` con servidor Javalin en puerto `:0`, mock de `VehicleAdapter`, helpers `get(path)` y `post(path, body)`, y record interno `HttpResult`
- [x] 1.2 Verificar que `BaseControllerTest` compila y que los lifecycle hooks (`@BeforeAll`, `@AfterAll`, `@BeforeEach`) están correctamente declarados como `static` donde corresponde

## 2. Test unitario de excepción inesperada

- [x] 2.1 Crear `VehicleControllerTest.java` en `vehicle-api/src/test/java/app/api/` extendiendo `BaseControllerTest`, con el test `shouldReturn500OnUnexpectedError` que configura el mock para lanzar `RuntimeException` y verifica HTTP 500
- [x] 2.2 Ejecutar `./gradlew :vehicle-api:test` y confirmar que el nuevo test pasa

## 3. Verificación de cobertura

- [x] 3.1 Ejecutar `./gradlew :vehicle-api:test :vehicle-api:jacocoTestReport` y confirmar que el bloque `catch(Exception)` en `VehicleController` aparece cubierto en el reporte HTML
