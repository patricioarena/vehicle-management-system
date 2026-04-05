## Tasks

### Task 1: Create VehicleCreateRequest record
**File**: `vehicle-core/src/main/java/app/core/model/VehicleCreateRequest.java`

Create a record with @Builder containing:
- `String type` (required)
- `String state` (required)

Status: ✅ COMPLETED

---

### Task 2: Create TypeVehicleMapper
**File**: `vehicle-core/src/main/java/app/core/mapper/TypeVehicleMapper.java`

Static utility class with method:
- `Optional<TypeVehicle> toEnum(String type)`: case-insensitive lookup

Status: ✅ COMPLETED

---

### Task 3: Refactor VehicleAdapter interface
**File**: `vehicle-core/src/main/java/app/core/usecase/VehicleAdapter.java`

Replace specific methods with:
- `Vehicle createVehicle(VehicleCreateRequest request)`

Status: ✅ COMPLETED

---

### Task 4: Update VehicleAdapterImpl
**File**: `vehicle-core/src/main/java/app/core/usecase/VehicleAdapterImpl.java`

Implement new single method using:
- TypeVehicleMapper.toEnum()
- StateVehicle.valueOf() with validation
- VehicleAbstractFactoryImpl.apply()

Status: ✅ COMPLETED

---

### Task 5: Refactor VehicleController
**File**: `vehicle-api/src/main/java/app/api/controller/VehicleController.java`

- Remove `/vehicles/car` and `/vehicles/sailboat` endpoints
- Add `POST /vehicles` with JSON body parsing
- Handle validation errors (400) and unknown types (404)
- Return 201 on success

Status: ✅ COMPLETED

---

### Task 6: Add tests
**Files**:
- `vehicle-core/src/test/java/app/core/mapper/TypeVehicleMapperTest.java`
- Update `VehicleAdapterImplTest.java` for new interface
- Update `VehicleControllerTest.java` for new endpoint

Status: ✅ COMPLETED (TypeVehicleMapperTest created)

---

## Verification

- [x] `POST /vehicles` with `{"type":"car","state":"NEW"}` returns 201
- [x] `POST /vehicles` with `{"type":"CAR","state":"NEW"}` returns 201 (case-insensitive)
- [x] `POST /vehicles` with `{"type":"unknown"}` returns 404
- [x] `POST /vehicles` with malformed JSON returns 400
- [x] `./gradlew test` passes
- [x] `./gradlew compileJava` passes
