/*
 * CLI Application for Vehicle Management
 */
package app.cli;

import app.core.config.AppModule;
import app.core.model.Vehicle;
import app.core.model.VehicleCreateRequest;
import app.core.usecase.VehicleAdapter;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

  public static void main(final String[] args) {
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

        Vehicle vehicle;
        switch (option) {
          case 1:
            vehicle = adapter.createVehicle(VehicleCreateRequest.builder()
                .type("CAR")
                .state("NEW")
                .build());
            System.out.println("Coche creado: " + vehicle);
            log.info("Created car: {}", vehicle);
            break;
          case 2:
            vehicle = adapter.createVehicle(VehicleCreateRequest.builder()
                .type("LIGHT_SAILBOAT")
                .state("NEW")
                .build());
            System.out.println("Velero ligero creado: " + vehicle);
            log.info("Created sailboat: {}", vehicle);
            break;
          case 3:
            exit = true;
            System.out.println("Saliendo del programa...");
            break;
          default:
            System.out.println("Opción no válida. Intente de nuevo.");
        }
      } catch (final Exception e) {
        System.out.println("Error: " + e.getMessage());
        scanner.nextLine(); // clear invalid input
      }
    }
    scanner.close();
    log.info("Vehicle CLI stopped");
  }
}
