### Para correr el proyecto, se necesitan los siguientes requisitos:

1. **Java**:
   - Versión mínima recomendada: **Java 17.0.5** o superior.
   - Asegúrate de tener configurada la variable de entorno `JAVA_HOME` apuntando a la instalación de Java.

2. **Gradle**:
   - El proyecto utiliza Gradle como herramienta de construcción.
   - Versión mínima recomendada: **Gradle 8.5** (incluido con el wrapper).
   - Puedes instalar Gradle manualmente o usar el wrapper incluido (`./gradlew` en macOS/Linux o `gradlew.bat` en Windows).

3. **Dependencias**:
   - Las dependencias del proyecto se gestionan automáticamente por Gradle. Solo necesitas ejecutar los comandos de Gradle para descargarlas.

4. **Configuración del entorno**:
   - Asegúrate de tener acceso a internet para que Gradle pueda descargar las dependencias necesarias en la primera ejecución.

---

## Estructura del Proyecto

Este proyecto utiliza una arquitectura de **módulos Gradle** para separar la lógica de negocio de las aplicaciones:

```
guice-practice/
├── vehicle-core/       # Módulo de lógica de negocio (librería)
├── vehicle-cli/       # Aplicación de consola interactiva
└── vehicle-api/       # API REST con Javalin
```

### Módulos

| Módulo | Descripción |
|--------|-------------|
| `vehicle-core` | Contiene toda la lógica de negocio: modelos, factories, motores, adapters, configuración Guice. Es una librería reusable. |
| `vehicle-cli` | Aplicación de consola interactiva para probar la creación de vehículos. |
| `vehicle-api` | API REST que expone endpoints para crear vehículos y consultar tipos. |

---

## Comandos

### Compilar todo el proyecto
```bash
./gradlew build
```

### Ejecutar la CLI (aplicación de consola)
```bash
./gradlew :vehicle-cli:run
```
Menú interactivo:
- Opción 1: Crear un coche
- Opción 2: Crear un velero ligero
- Opción 3: Salir

### Ejecutar la API REST
```bash
./gradlew :vehicle-api:run
```
La API inicia en: **http://localhost:8080**

### Endpoints de la API

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/health` | Health check |
| POST | `/vehicles/car` | Crear un coche |
| POST | `/vehicles/sailboat` | Crear un velero ligero |
| GET | `/vehicles/types` | Listar todos los tipos de vehículos |

### Ejemplos con curl

```bash
# Health check
curl http://localhost:8080/health

# Crear un coche
curl -X POST http://localhost:8080/vehicles/car

# Crear un velero
curl -X POST http://localhost:8080/vehicles/sailboat

# Listar tipos de vehículos
curl http://localhost:8080/vehicles/types
```

### Ejecutar tests del módulo core
```bash
./gradlew :vehicle-core:test
```

### Ver proyectos disponibles
```bash
./gradlew projects
```

---

## IDE recomendado

Puedes usar **IntelliJ IDEA** para abrir y trabajar con el proyecto. Asegúrate de que el IDE esté configurado para usar la versión correcta de Java (17+) y que reconozca el proyecto como un proyecto multi-módulo Gradle.

---

### Resumen de patrones de diseño aplicados en el código hasta el momento:

1. **Inyección de Dependencias (Dependency Injection)**:
    - Se utiliza el framework Guice para gestionar las dependencias entre clases. Por ejemplo, en `AppModule`, se configuran los bindings para que las interfaces como `VehicleAdapter` y `VehicleFactory` se asocien con sus implementaciones (`VehicleAdapterImpl`, `VehicleAbstractFactoryImpl`, etc.).
    - Esto permite desacoplar las dependencias y facilita la prueba y el mantenimiento del código.

2. **Fábrica (Factory)**:
    - El patrón Factory se aplica en `VehicleAbstractFactoryImpl`, que actúa como una fábrica general para crear vehículos. Utiliza un EnumMap para delegar la creación de vehículos a fábricas específicas (`CarFactoryImpl`, `LightSailboatFactoryImpl`).
    - Este patrón permite encapsular la lógica de creación de objetos y facilita la extensión para nuevos tipos de vehículos.

3. **Adaptador (Adapter)**:
    - `VehicleAdapter` y su implementación `VehicleAdapterImpl` actúan como un adaptador para simplificar la interacción con las fábricas de vehículos. Proveen métodos específicos (`createCar`, `createLightSailboat`) para crear vehículos de tipos concretos.
    - Este patrón se utiliza para traducir interfaces incompatibles o simplificar el uso de una API compleja.

4. **Singleton**:
    - Las factories están configuradas con `@Singleton` en Guice, lo que asegura que solo exista una instancia de estas clases en toda la aplicación.

5. **Builder**:
    - Las clases `Car`, `LightSailboat` y `State` utilizan el patrón Builder (a través de Lombok con `@Builder`) para construir instancias de manera fluida y legible.

6. **Enum como Estrategia**:
    - `TypeVehicle` y `StateVehicle` se utilizan como enumeraciones para representar tipos y estados de vehículos. Esto permite manejar valores constantes de manera segura y legible.

7. **Mapeo de Tipos (Type Map)**:
    - En `VehicleAbstractFactoryImpl`, se utiliza un `EnumMap` para mapear tipos de vehículos (`TypeVehicle`) a sus respectivas fábricas.

Estos patrones trabajan juntos para crear un sistema modular, extensible y fácil de mantener.
