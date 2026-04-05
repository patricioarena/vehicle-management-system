# Vehicle Type Mapper

## Description
Utility component that maps string representations of vehicle types to TypeVehicle enum values with case-insensitive matching.

## Requirements

### REQ-1: Map valid vehicle type strings to enum
**Given** a valid vehicle type string (e.g., "car", "CAR", "Car")
**When** toEnum() is called
**Then** return Optional containing the matching TypeVehicle

### REQ-2: Support case-insensitive matching
**Given** any case variation of a valid type ("car", "CAR", "Car", "cAr")
**When** toEnum() is called
**Then** return Optional with the same TypeVehicle

### REQ-3: Return empty optional for invalid types
**Given** a string that doesn't match any TypeVehicle
**When** toEnum() is called
**Then** return Optional.empty()

### REQ-4: Handle null input gracefully
**Given** a null input string
**When** toEnum() is called
**Then** return Optional.empty()

### REQ-5: Handle empty string gracefully
**Given** an empty string
**When** toEnum() is called
**Then** return Optional.empty()

## Interface Specification

```java
public class TypeVehicleMapper {
    /**
     * Maps a string to TypeVehicle enum case-insensitively.
     * 
     * @param type the vehicle type string
     * @return Optional containing the TypeVehicle, or empty if not found
     */
    public static Optional<TypeVehicle> toEnum(String type);
}
```

## Examples

| Input | Output |
|-------|--------|
| "car" | Optional[CAR] |
| "CAR" | Optional[CAR] |
| "Car" | Optional[CAR] |
| "light_sailboat" | Optional[LIGHT_SAILBOAT] |
| "spaceship" | Optional.empty() |
| null | Optional.empty() |
| "" | Optional.empty() |

## Acceptance Criteria

- [ ] Maps all 49 TypeVehicle enum values
- [ ] Case-insensitive matching works correctly
- [ ] Returns empty for invalid/null/empty inputs
- [ ] Unit tests cover all edge cases
