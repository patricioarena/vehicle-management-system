## Overview

Consolidate vehicle creation endpoints into a single unified endpoint that accepts vehicle type as a parameter. Introduce a mapper to handle string-to-enum conversion, keeping the adapter pattern for abstraction.

## Architecture

### Component Diagram

```
Client → POST /vehicles (JSON: {type, state})
         ↓
VehicleController (receives payload)
         ↓
VehicleAdapter.createVehicle(VehicleCreateRequest)
         ↓
TypeVehicleMapper.toEnum(type) → Optional<TypeVehicle>
         ↓
VehicleAbstractFactoryImpl.apply(type, state)
         ↓
Concrete Factory (e.g., CarFactoryImpl)
         ↓
Vehicle Record (Car, LightSailboat, etc.)
```

## Key Design Decisions

1. **VehicleCreateRequest record**: Models the API contract with @Builder for extensibility
2. **TypeVehicleMapper**: Static utility class for string-to-enum mapping, case-insensitive
3. **Simplified VehicleAdapter**: Single method accepting request object instead of multiple typed methods
4. **HTTP Status Codes**: 
   - 201 Created on success
   - 400 Bad Request for invalid/missing payload
   - 404 Not Found for unknown vehicle types

## API Contract

```json
POST /vehicles
Content-Type: application/json

{
  "type": "car",
  "state": "NEW"
}

Response: 201 Created
{
  "type": "CAR",
  "state": {...},
  "motor": {...}
}
```

## Testing Strategy

- Unit tests for TypeVehicleMapper (case-insensitive matching, invalid types)
- Integration tests for the complete flow via VehicleController
- Edge cases: empty type, null state, unknown type
