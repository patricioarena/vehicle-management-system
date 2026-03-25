/*
 * CLI Application for Vehicle Management
 */
package app.cli;

import app.core.config.AppModule;
import app.core.constant.enums.StateVehicle;
import app.core.usecase.VehicleAdapter;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

  public static void main(String[] args) {
    final Injector injector = Guice.createInjector(new AppModule());
    final VehicleAdapter adapter = injector.getInstance(VehicleAdapter.class);

    log.info("Vehicle CLI started");
    menu(adapter);
  }

  private static void menu(final VehicleAdapter adapter) {
    final var scanner = new Scanner(System.in);
    boolean exit = false;

    while (!exit) {
      System.out.println("\n=== Vehicle CLI ===");
      System.out.println("1. Crear un coche");
      System.out.println("2. Crear un velero ligero");
      System.out.println("3. Salir");
      System.out.print("Seleccione una opción: ");

      try {
        final var option = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (option) {
          case 1:
            final var car = adapter.createCar(StateVehicle.NEW);
            System.out.println("Coche creado: " + car);
            log.info("Created car: {}", car);
            break;
          case 2:
            final var boat = adapter.createLightSailboat(StateVehicle.NEW);
            System.out.println("Velero ligero creado: " + boat);
            log.info("Created sailboat: {}", boat);
            break;
          case 3:
            exit = true;
            System.out.println("Saliendo del programa...");
            break;
          default:
            System.out.println("Opción no válida. Intente de nuevo.");
        }
      } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
        scanner.nextLine(); // clear invalid input
      }
    }
    scanner.close();
    log.info("Vehicle CLI stopped");
  }
}
