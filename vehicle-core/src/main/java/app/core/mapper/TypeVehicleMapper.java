package app.core.mapper;

import app.core.constant.enums.TypeVehicle;
import java.util.Locale;
import java.util.Optional;

/**
 * Maps string representations of vehicle types to TypeVehicle enum.
 * Supports case-insensitive matching.
 */
public final class TypeVehicleMapper {

    private TypeVehicleMapper() {
        // Utility class - prevent instantiation
    }

    /**
     * Maps a string to TypeVehicle enum case-insensitively.
     *
     * @param type the vehicle type string
     * @return Optional containing the TypeVehicle, or empty if not found
     */
    public static Optional<TypeVehicle> toEnum(final String type) {
        if (type == null || type.isBlank()) {
            return Optional.empty();
        }

        try {
            return Optional.of(TypeVehicle.valueOf(type.toUpperCase(Locale.ROOT)));
        } catch (final IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
