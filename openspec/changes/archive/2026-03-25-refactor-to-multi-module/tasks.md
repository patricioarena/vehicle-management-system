## 1. Gradle Configuration Setup

- [x] 1.1 Create root `build.gradle` with common configuration (Java 17, repositories, plugins)
- [x] 1.2 Update `settings.gradle` to include vehicle-core, vehicle-cli, and vehicle-api modules
- [x] 1.3 Create `vehicle-core/build.gradle` with java-library plugin and all current dependencies (Guice, Lombok, SLF4J, etc.)
- [x] 1.4 Create `vehicle-cli/build.gradle` with application plugin and dependency on vehicle-core
- [x] 1.5 Create `vehicle-api/build.gradle` with application plugin, dependencies on vehicle-core, Javalin, and Jackson
- [x] 1.6 Verify `./gradlew projects` lists all 3 modules correctly

## 2. Vehicle Core Module Migration

- [x] 2.1 Create `vehicle-core/src/main/java/app/core/` directory structure
- [x] 2.2 Move `app/src/main/java/app/component/` to `vehicle-core/src/main/java/app/core/component/`
- [x] 2.3 Move `app/src/main/java/app/config/` to `vehicle-core/src/main/java/app/core/config/`
- [x] 2.4 Move `app/src/main/java/app/constant/` to `vehicle-core/src/main/java/app/core/constant/`
- [x] 2.5 Move `app/src/main/java/app/custom/` to `vehicle-core/src/main/java/app/core/custom/`
- [x] 2.6 Move `app/src/main/java/app/model/` to `vehicle-core/src/main/java/app/core/model/`
- [x] 2.7 Move `app/src/main/java/app/usecase/` to `vehicle-core/src/main/java/app/core/usecase/`
- [x] 2.8 Update all package declarations from `package app.X;` to `package app.core.X;`
- [x] 2.9 Update all import statements to reflect new package structure
- [x] 2.10 Move `app/src/test/java/app/` to `vehicle-core/src/test/java/app/core/`
- [x] 2.11 Update test package declarations and imports
- [x] 2.12 Verify `./gradlew :vehicle-core:build` passes successfully
- [x] 2.13 Verify `./gradlew :vehicle-core:test` runs existing tests

## 3. Vehicle CLI Module Implementation

- [x] 3.1 Create `vehicle-cli/src/main/java/app/cli/Main.java`
- [x] 3.2 Implement Guice injector setup in CLI Main: `Guice.createInjector(new AppModule())`
- [x] 3.3 Inject VehicleAdapter via constructor injection
- [x] 3.4 Implement interactive menu with Scanner (options: 1=Create car, 2=Create sailboat, 3=Exit)
- [x] 3.5 Call `vehicleAdapter.createCar(StateVehicle.NEW)` when user selects option 1
- [x] 3.6 Call `vehicleAdapter.createLightSailboat(StateVehicle.NEW)` when user selects option 2
- [x] 3.7 Display created vehicle using log.info() or System.out.println()
- [x] 3.8 Set `mainClass = 'app.cli.Main'` in vehicle-cli/build.gradle
- [x] 3.9 Verify `./gradlew :vehicle-cli:run` starts the interactive menu
- [x] 3.10 Test menu options manually to ensure vehicles are created correctly

## 4. Vehicle API Module Implementation

- [x] 4.1 Create `vehicle-api/src/main/java/app/api/Main.java`
- [x] 4.2 Create `vehicle-api/src/main/java/app/api/config/ApiModule.java`
- [x] 4.3 Configure ApiModule to install AppModule: `install(new AppModule())`
- [x] 4.4 Create `vehicle-api/src/main/java/app/api/controller/VehicleController.java`
- [x] 4.5 Inject VehicleAdapter into VehicleController via `@RequiredArgsConstructor(onConstructor = @__(@Inject))`
- [x] 4.6 Implement `registerRoutes(Javalin app)` method in VehicleController
- [x] 4.7 Add GET /health endpoint returning `{"status":"UP"}`
- [x] 4.8 Add POST /vehicles/car endpoint calling `vehicleAdapter.createCar(StateVehicle.NEW)` and returning JSON
- [x] 4.9 Add POST /vehicles/sailboat endpoint calling `vehicleAdapter.createLightSailboat(StateVehicle.NEW)` and returning JSON
- [x] 4.10 Add GET /vehicles/types endpoint returning all TypeVehicle enum values as JSON
- [x] 4.11 Configure Javalin in Main.java with `showJavalinBanner = false`
- [x] 4.12 Start Javalin on port 8080: `Javalin.create(...).start(8080)`
- [x] 4.13 Set `mainClass = 'app.api.Main'` in vehicle-api/build.gradle
- [x] 4.14 Add Jackson dependency to vehicle-api/build.gradle for JSON serialization
- [x] 4.15 Verify `./gradlew :vehicle-api:run` starts server on port 8080
- [x] 4.16 Test endpoints manually using curl or browser:
  - `curl http://localhost:8080/health`
  - `curl -X POST http://localhost:8080/vehicles/car`
  - `curl -X POST http://localhost:8080/vehicles/sailboat`
  - `curl http://localhost:8080/vehicles/types`

## 5. Cleanup and Verification

- [x] 5.1 Delete old `app/` directory
- [x] 5.2 Verify no compilation errors with `./gradlew build`
- [x] 5.3 Verify all tests pass with `./gradlew test`
- [x] 5.4 Verify `./gradlew :vehicle-cli:run` works from clean build
- [x] 5.5 Verify `./gradlew :vehicle-api:run` works from clean build
- [x] 5.6 Test parallel execution: run CLI in one terminal, API in another
- [x] 5.7 Ensure vehicle-core JAR can be built independently: `./gradlew :vehicle-core:jar`
- [x] 5.8 Verify no circular dependencies in build output
- [x] 5.9 Check that no references to vehicle-cli or vehicle-api exist in vehicle-core code
- [x] 5.10 Run `./gradlew clean build` from scratch to ensure everything works

## 6. Documentation and Final Review

- [x] 6.1 Update README.md with new module structure explanation
- [x] 6.2 Document new Gradle commands in README.md:
  - `./gradlew :vehicle-cli:run` for CLI
  - `./gradlew :vehicle-api:run` for API
  - `./gradlew :vehicle-core:test` for core tests
- [x] 6.3 Add example curl commands to README.md for API testing
- [x] 6.4 Verify all links and file paths in documentation are correct
- [x] 6.5 Commit all changes with descriptive commit message
- [x] 6.6 Final review of all spec requirements to ensure compliance
