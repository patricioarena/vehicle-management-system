## Context

El proyecto actualmente tiene configurado pre-commit con hooks básicos (`trailing-whitespace`, `end-of-file-fixer`, `check-yaml`, `check-toml`, `pretty-format-java`), pero carece de validación para imports estáticos con wildcards. Según AGENTS.md línea 61, el proyecto permite wildcards únicamente para imports estáticos de enums (`import static app.constant.DrivingType.*;`), pero no hay mecanismo automatizado que lo valide.

El proyecto es multi-module Gradle con Java 21, usando:
- `vehicle-core`: Core domain logic
- `vehicle-cli`: Command-line interface
- `vehicle-api`: REST API

Checkstyle es la herramienta estándar en la industria Java para validación de código fuente y puede integrarse perfectamente con Gradle y pre-commit.

## Goals / Non-Goals

**Goals:**
- Detectar y bloquear imports estáticos con wildcards (ej: `import static app.core.component.*;`)
- Permitir imports estáticos de enums específicos según AGENTS.md
- Integrar validación en el flujo de pre-commit para bloquear commits con violaciones
- Configurar Checkstyle para todos los módulos del proyecto
- Proveer mensajes de error claros indicando el problema y cómo solucionarlo

**Non-Goals:**
- Agregar otras reglas de Checkstyle (solo imports estáticos)
- Modificar la configuración de `pretty-format-java` existente
- Cambiar la versión de Java del proyecto
- Reescribir código existente para cumplir las reglas (solo validación)

## Decisions

### 1. Usar Checkstyle 10.x sobre SpotBugs o PMD
**Decision**: Usar Checkstyle con la regla `AvoidStaticImport`

**Rationale**: Checkstyle es más apropiado para validación de estilo de código, mientras que SpotBugs detecta bugs potenciales y PMD es más para análisis estático de código problemático. `AvoidStaticImport` permite configurar exclusiones específicas mediante el atributo `excludes`, que es exactamente lo que necesitamos para permitir enums.

**Alternatives considered**:
- SpotBugs: No tiene reglas específicas para imports estáticos
- PMD: La regla `AvoidStaticImports` no permite exclusiones configurables de forma granular
- Script Python custom: Menos estándar, requeriría mantenimiento adicional

### 2. Configurar exclusión para enums mediante patrón regex
**Decision**: Usar `excludes` en `AvoidStaticImport` con patrón `*.constant.enums.*`

**Rationale**: AGENTS.md específicamente menciona que los wildcards están permitidos para enums. El patrón `*.constant.enums.*` cubre la estructura de paquetes actual donde están definidos los enums (`app.core.constant.enums`).

**Ejemplo de configuración**:
```xml
<module name="AvoidStaticImport">
  <property name="excludes" value="*.constant.enums.*"/>
</module>
```

### 3. Integración con pre-commit mediante hook de sistema
**Decision**: Usar hook `system` de pre-commit para ejecutar `./gradlew checkstyleMain`

**Rationale**: No existe un hook oficial de pre-commit para Checkstyle. Usar un hook de sistema permite ejecutar la tarea Gradle directamente, garantizando que se use la misma versión de Checkstyle configurada en el build.

**Alternatives considered**:
- `pre-commit-hooks-java`: No tiene soporte nativo para Checkstyle
- Hook local Python: Añade complejidad innecesaria cuando Gradle ya gestiona Checkstyle

### 4. Ubicación del archivo checkstyle.xml
**Decision**: `config/checkstyle/checkstyle.xml` en la raíz del proyecto

**Rationale**: Esta es la ubicación convencional para archivos de configuración de Checkstyle en proyectos Gradle. El plugin de Checkstyle busca automáticamente en esta ruta.

## Risks / Trade-offs

| Risk | Mitigation |
|------|------------|
| **Falso positivo**: Import estático legítimo bloqueado | Configurar `excludes` cuidadosamente y documentar el patrón esperado en AGENTS.md |
| **Performance**: Checkstyle ralentiza builds | Ejecutar solo en pre-commit y CI, no en builds locales de desarrollo rápido. Usar `checkstyleMain` solo, no `checkstyleTest` en pre-commit |
| **Curva de aprendizaje**: Desarrolladores nuevos confundidos por errores | Documentar claramente en README.md con ejemplos de imports correctos e incorrectos |
| **Regex demasiado permisivo**: Exclusiones muy amplias | Revisar el patrón `*.constant.enums.*` y ajustar si se detectan casos no deseados |

## Migration Plan

1. **Fase 1 - Configuración**: Crear `checkstyle.xml` y configurar plugin en `build.gradle`
2. **Fase 2 - Validación**: Ejecutar `./gradlew checkstyleMain` y verificar que no hay violaciones en código existente
3. **Fase 3 - Pre-commit**: Añadir hook de sistema en `.pre-commit-config.yaml`
4. **Fase 4 - Documentación**: Actualizar AGENTS.md y README.md con ejemplos

**Rollback**: Remover plugin de build.gradle y archivo checkstyle.xml. No hay cambios en el código fuente.

## Open Questions

- ¿Se debe ejecutar `checkstyleTest` también en pre-commit o solo `checkstyleMain`?
- ¿El patrón `*.constant.enums.*` cubre todos los casos actuales y futuros de enums?
