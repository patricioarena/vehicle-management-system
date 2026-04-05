# Unified Vehicle Creation Endpoint

## Description
Single API endpoint `POST /vehicles` that accepts any vehicle type and state via JSON payload.

## Requirements

### REQ-1: Endpoint accepts POST requests at /vehicles
**Given** a client sends a POST request
**When** the path is `/vehicles`
**Then** the endpoint processes the request

### REQ-2: Request body must contain type and state fields
**Given** a valid POST request to `/vehicles`
**When** the JSON body is parsed
**Then** it must contain:
- `type`: string (required) - vehicle type identifier
- `state`: string (required) - vehicle state (NEW, CLEAN, ROUGH, DAMAGED)

### REQ-3: Invalid JSON returns 400 Bad Request
**Given** a POST request with malformed JSON
**When** the request is processed
**Then** return HTTP 400 with error message

### REQ-4: Missing required fields returns 400
**Given** a POST request missing type or state
**When** the request is validated
**Then** return HTTP 400 with error message

### REQ-5: Successful creation returns 201 with vehicle data
**Given** valid type and state
**When** vehicle is created successfully
**Then** return HTTP 201 with created vehicle JSON

### REQ-6: Unknown vehicle type returns 404
**Given** a type that doesn't exist in TypeVehicle enum
**When** the type is mapped
**Then** return HTTP 404 with error message

## API Specification

### Request
```http
POST /vehicles
Content-Type: application/json

{
  "type": "car",
  "state": "NEW"
}
```

### Response 201 Created
```json
{
  "type": "CAR",
  "state": {
    "value": "NEW",
    "description": "Unused, in perfect mechanical..."
  },
  "motor": {
    "power": 150,
    "cylinders": 4,
    "fuelType": "GASOLINE"
  }
}
```

### Response 400 Bad Request
```json
{
  "error": "Invalid request: missing required field 'type'"
}
```

### Response 404 Not Found
```json
{
  "error": "Vehicle type 'spaceship' not found"
}
```

## Acceptance Criteria

- [ ] Endpoint responds to POST /vehicles
- [ ] Accepts JSON payload with type and state
- [ ] Returns 201 for successful creation
- [ ] Returns 400 for invalid/missing fields
- [ ] Returns 404 for unknown vehicle types
- [ ] Response includes complete vehicle data
