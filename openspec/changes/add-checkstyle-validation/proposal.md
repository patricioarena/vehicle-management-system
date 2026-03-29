## Why

El proyecto actualmente permite el uso de wildcards en imports estáticos (`import static package.*`), lo cual contradice las directrices de estilo definidas en AGENTS.md que especifican "No wildcard imports (except for static enums)". Esto hace que sea difícil mantener consistencia en el código y previene que los pre-commit hooks bloqueen commits con estos patrones no deseados. Se necesita una herramienta automatizada que detecte y bloquee el uso de wildcards en imports estáticos, permitiendo únicamente las excepciones definidas para enums.

## What Changes

- Configurar Checkstyle 10.x en el proyecto Gradle multi-module
- Crear archivo `checkstyle.xml` con regla `AvoidStaticImport` configurada
- Permitir wildcards únicamente para imports de enums (excepción configurable)
- Integrar Checkstyle con pre-commit hooks para bloquear commits con violaciones
- Añadir tarea Gradle `checkstyleMain` y `checkstyleTest` para todos los módulos
- Configurar exclusiones para enums según patrón `*.constant.enums.*`

## Capabilities

### New Capabilities
- `checkstyle-validation`: Configuración de Checkstyle para validación de imports estáticos con reglas específicas del proyecto

### Modified Capabilities
- `pre-commit-hooks`: Agregar integración con Checkstyle para validar código antes de commits

## Impact

- **Build System**: Se añade plugin checkstyle a build.gradle raíz
- **Pre-commit**: El hook ejecutará `./gradlew checkstyleMain` antes de permitir commits
- **Developer Workflow**: Los desarrolladores verán errores de estilo en tiempo de build
- **CI/CD**: Las validaciones de Checkstyle se ejecutarán en pipelines
- **Excepciones**: Los imports estáticos de enums (ej: `import static app.core.constant.enums.FuelType.*`) seguirán permitidos según AGENTS.md línea 61
