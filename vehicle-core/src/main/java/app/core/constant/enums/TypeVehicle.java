package app.core.constant.enums;

import static app.core.constant.DrivingType.AQUATIC;
import static app.core.constant.DrivingType.AERIAL;
import static app.core.constant.DrivingType.LAND;

public enum TypeVehicle {
  CAR(1, true, LAND),
  TRUCK(2, true, LAND),
  MOTORCYCLE(3, true, LAND),
  BICYCLE(4, false, LAND),
  BUS(5, true, LAND),
  TRACTOR_TRAILER(6, true, LAND),
  LIGHT_SAILBOAT(7, false, AQUATIC),
  AIRPLANE(8, true, AERIAL),
  HELICOPTER(9, true, AERIAL),
  TRAIN(10, true, LAND),
  TRAM(11, true, LAND),
  SKATEBOARD(12, false, LAND),
  ELECTRIC_SCOOTER(13, true, LAND),
  SCOOTER(14, false, LAND),
  GOLF_CART(15, true, LAND),
  SNOWMOBILE(16, true, LAND),
  BOAT(17, true, AQUATIC),
  SUBMARINE(18, true, AQUATIC),
  QUAD(19, true, LAND),
  CARAVAN(20, false, LAND),
  TRICYCLE(21, false, LAND),
  TUKTUK(22, true, LAND),
  AMBULANCE(23, true, LAND),
  POLICE_CAR(24, true, LAND),
  FIRE_TRUCK(25, true, LAND),
  MINIVAN(26, true, LAND),
  VAN(27, true, LAND),
  PICKUP_TRUCK(28, true, LAND),
  CONVERTIBLE(29, true, LAND),
  COUPE(30, true, LAND),
  LIMOUSINE(31, true, LAND),
  MICROCAR(32, true, LAND),
  HANDCART(33, false, LAND),
  CARGO_TRICYCLE(34, false, LAND),
  SNOW_QUAD(35, true, LAND),
  GLIDER(36, false, AERIAL),
  CANOE(37, false, AQUATIC),
  KAYAK(38, false, AQUATIC),
  JET_SKI(39, true, AQUATIC),
  PERSONAL_SUBMARINE(40, true, AQUATIC),
  MOUNTAIN_BIKE(41, false, LAND),
  ELECTRIC_BIKE(42, true, LAND),
  GARBAGE_TRUCK(43, true, LAND),
  EXCAVATOR(44, true, LAND),
  BACKHOE(45, true, LAND),
  MOTOR_TRICYCLE(46, true, LAND),
  ELECTRIC_GOLF_CART(47, true, LAND),
  ELECTRIC_SHOPPING_CART(48, true, LAND),
  HORSE_DRAWN_CARRIAGE(49, false, LAND);

  private final int id;
  private final boolean hasEngine;
  private final String drivingType;

  TypeVehicle(int id, boolean hasEngine, String drivingType) {
    this.id = id;
    this.hasEngine = hasEngine;
    this.drivingType = drivingType;
  }

  public int getId() {
    return id;
  }

  public boolean hasEngine() {
    return hasEngine;
  }

  public String getDrivingType() {
    return drivingType;
  }
}