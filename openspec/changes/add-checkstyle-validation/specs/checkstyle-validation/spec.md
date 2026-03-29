## ADDED Requirements

### Requirement: Detectar imports estáticos con wildcards
El sistema Checkstyle DEBE detectar y reportar como error cualquier import estático que use wildcards (ej: `import static package.*;`), excepto para los casos permitidos explícitamente.

#### Scenario: Import estático con wildcard detectado
- **WHEN** un archivo Java contiene `import static app.core.component.*;`
- **THEN** Checkstyle reporta un error indicando que los imports estáticos con wildcards no están permitidos

#### Scenario: Import estático sin wildcard permitido
- **WHEN** un archivo Java contiene `import static app.core.component.VehicleFactory;`
- **THEN** Checkstyle no reporta error y la validación pasa exitosamente

### Requirement: Permitir wildcards en imports de enums
El sistema Checkstyle DEBE permitir imports estáticos con wildcards únicamente cuando provienen de paquetes de enums según el patrón configurado (`*.constant.enums.*`).

#### Scenario: Import estático de enum con wildcard permitido
- **WHEN** un archivo Java contiene `import static app.core.constant.enums.FuelType.*;`
- **THEN** Checkstyle no reporta error porque el patrón coincide con la exclusión configurada

#### Scenario: Import estático de enum fuera del patrón no permitido
- **WHEN** un archivo Java contiene `import static app.other.enums.FuelType.*;`
- **THEN** Checkstyle reporta error porque el paquete no coincide con el patrón `*.constant.enums.*`

### Requirement: Ejecutar validación en pre-commit
El hook de pre-commit DEBE ejecutar Checkstyle antes de permitir un commit y bloquear el commit si existen violaciones de imports estáticos.

#### Scenario: Commit bloqueado por violación de import
- **WHEN** un desarrollador intenta hacer commit con código que tiene `import static app.core.component.*;`
- **THEN** el hook de pre-commit ejecuta Checkstyle, detecta la violación, muestra mensaje de error claro y bloquea el commit

#### Scenario: Commit permitido sin violaciones
- **WHEN** un desarrollador intenta hacer commit con código que solo tiene imports estáticos válidos o de enums permitidos
- **THEN** el hook de pre-commit ejecuta Checkstyle, no detecta violaciones y permite el commit

### Requirement: Proveer mensajes de error claros
Los mensajes de error de Checkstyle DEBEN indicar claramente el archivo, línea y tipo de violación, sugiriendo la corrección apropiada.

#### Scenario: Mensaje de error informativo
- **WHEN** Checkstyle detecta `import static app.core.component.*;` en la línea 10 de VehicleFactory.java
- **THEN** el mensaje de error indica: "Avoid using static imports with wildcards in VehicleFactory.java:10. Import specific classes instead."

### Requirement: Configuración multi-module
Checkstyle DEBE ejecutarse para todos los módulos del proyecto (vehicle-core, vehicle-cli, vehicle-api) usando la misma configuración centralizada.

#### Scenario: Validación en módulo core
- **WHEN** se ejecuta `./gradlew :vehicle-core:checkstyleMain`
- **THEN** Checkstyle valida todos los archivos Java en vehicle-core usando checkstyle.xml

#### Scenario: Validación en módulo api
- **WHEN** se ejecuta `./gradlew :vehicle-api:checkstyleMain`
- **THEN** Checkstyle valida todos los archivos Java en vehicle-api usando checkstyle.xml

#### Scenario: Validación en módulo cli
- **WHEN** se ejecuta `./gradlew :vehicle-cli:checkstyleMain`
- **THEN** Checkstyle valida todos los archivos Java en vehicle-cli usando checkstyle.xml
