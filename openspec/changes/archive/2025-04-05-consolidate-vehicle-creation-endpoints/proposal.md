## Why

The current vehicle creation API requires separate endpoints for each vehicle type (`/vehicles/car`, `/vehicles/sailboat`). This pattern doesn't scale well—every new vehicle type requires adding a new endpoint, modifying the controller, adapter interface, and adapter implementation. We need a unified endpoint that can handle any vehicle type through configuration, making the system extensible without code changes to the API layer.

## What Changes

- **BREAKING**: Remove individual endpoints `/vehicles/car` and `/vehicles/sailboat`
- **BREAKING**: Introduce new unified endpoint `POST /vehicles` accepting JSON payload `{type: "string", state: "string"}`
- Create `VehicleCreateRequest` record to model the API request payload
- Create `TypeVehicleMapper` utility to map strings (case-insensitive) to `TypeVehicle` enum
- Simplify `VehicleAdapter` interface to single method `createVehicle(VehicleCreateRequest)`
- Update `VehicleController` to handle 404 for invalid vehicle types and 400 for invalid payloads
- Return created vehicle JSON with HTTP 201 status

## Capabilities

### New Capabilities
- `unified-vehicle-creation`: Single API endpoint that accepts any vehicle type via JSON payload, delegates to mapper and factory for creation
- `vehicle-type-mapping`: Case-insensitive string-to-enum mapper for TypeVehicle with validation and 404 handling

### Modified Capabilities
<!-- No existing specs modified -->

## Impact

- **API Layer**: `VehicleController` - new unified endpoint, remove specific endpoints
- **Core Domain**: New `VehicleCreateRequest` model, new `TypeVehicleMapper`, simplified `VehicleAdapter`
- **Breaking Changes**: Clients using `/vehicles/car` or `/vehicles/sailboat` must migrate to `POST /vehicles`
- **Benefits**: Adding new vehicle types only requires creating the model and factory - no API changes needed
