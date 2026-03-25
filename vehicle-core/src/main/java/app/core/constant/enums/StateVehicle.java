package app.core.constant.enums;

import java.util.Locale;

public enum StateVehicle {
  NEW("Unused, in perfect mechanical and aesthetic condition. It has no wear and does not require reconditioning."),
  CLEAN("Some normal wear, but no major mechanical or cosmetic problems. May require limited reconditioning."),
  ROUGH("Several mechanical and/or cosmetic problems requiring significant repairs."),
  DAMAGED("Major mechanical and/or body damage that may render it in non-safe running condition.");

  private final String description;

  StateVehicle(String description) {
    this.description = description;
  }

  public String getDescription() {
    return this.description;
  }

  public String getName() {
    return this.name().toLowerCase(Locale.ROOT);
  }
}
