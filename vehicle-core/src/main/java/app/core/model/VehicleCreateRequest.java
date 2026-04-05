package app.core.model;

import lombok.Builder;

/**
 * Request model for creating a new vehicle via API.
 */
@Builder
public record VehicleCreateRequest(
    String type,
    String state
) {
}
