## 1. Configuración Checkstyle

- [x] 1.1 Crear directorio `config/checkstyle/` en la raíz del proyecto
- [x] 1.2 Crear archivo `config/checkstyle/checkstyle.xml` con regla `AvoidStaticImport` y exclusión para enums
- [x] 1.3 Configurar plugin Checkstyle 10.x en `build.gradle` raíz (aplicar a todos los subproyectos)
- [x] 1.4 Ejecutar `./gradlew checkstyleMain` y verificar que no hay errores en código existente

## 2. Integración Pre-commit

- [x] 2.1 Añadir hook de sistema en `.pre-commit-config.yaml` para ejecutar `./gradlew checkstyleMain`
- [x] 2.2 Configurar el hook para ejecutarse solo en archivos Java modificados
- [ ] 2.3 Probar el hook haciendo un commit de prueba con código válido

## 3. Validación y Pruebas

- [ ] 3.1 Crear archivo de prueba temporal con import estático wildcard y verificar que Checkstyle lo detecta
- [ ] 3.2 Verificar que imports estáticos de enums (ej: `FuelType.*`) son permitidos
- [ ] 3.3 Ejecutar `./gradlew checkstyleMain` en cada módulo (vehicle-core, vehicle-cli, vehicle-api)
- [ ] 3.4 Verificar que los mensajes de error son claros e indican archivo y línea

## 4. Documentación

- [ ] 4.1 Actualizar AGENTS.md con referencia a Checkstyle y reglas de imports
- [ ] 4.2 Añadir sección en README.md sobre validación de código con Checkstyle
- [ ] 4.3 Incluir ejemplos de imports correctos e incorrectos en la documentación
