import java.util.Scanner;

public class Menu {
    private final AplicacionLecturaEscritura aplicacion;

    // Constructor que recibe una instancia de AplicacionLecturaEscritura
    public Menu(AplicacionLecturaEscritura aplicacion) {
        this.aplicacion = aplicacion; // Asignar la instancia a la propiedad 'aplicacion'
    }

    // Método para mostrar el menú interactivo
    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("=== Menú ===");
            System.out.println("1. Agregar dato");
            System.out.println("2. Modificar dato");
            System.out.println("3. Eliminar dato");
            System.out.println("4. Buscar dato");
            System.out.println("5. Recuperar último borrado");
            System.out.println("6. Eliminar todos los datos");

            // Función eliminada hasta que sea reparada
            // System.out.println("7. Recuperar todos los datos eliminados ()");

            System.out.println("7. Mostrar datos");
            System.out.println("8. Salir");
            System.out.print("Elija una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> {
                    System.out.print("Ingrese el dato a agregar: ");
                    String dato = scanner.nextLine();
                    aplicacion.altaDato(dato); // Llamar al método altaDato de la instancia 'aplicacion'
                }
                case 2 -> {
                    System.out.print("Ingrese el índice del dato a modificar: ");
                    int indiceModificacion = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ingrese el nuevo dato: ");
                    String nuevoDato = scanner.nextLine();
                    aplicacion.modificarDato(indiceModificacion, nuevoDato); // Llamar al método modificarDato
                }
                case 3 -> {
                    System.out.print("Ingrese el índice del dato a eliminar: ");
                    int indiceEliminacion = scanner.nextInt();
                    aplicacion.eliminarDato(indiceEliminacion); // Llamar al método eliminarDato
                }
                case 4 -> {
                    System.out.print("Ingrese el término de búsqueda: ");
                    String terminoBusqueda = scanner.nextLine();
                    aplicacion.buscarDato(terminoBusqueda); // Llamar al método buscarDato
                }
                case 5 -> aplicacion.recuperarUltimoBorrado(); // Llamar al método recuperarUltimoBorrado
                case 6 -> aplicacion.eliminarTodosLosDatos(); // Llamar al método eliminarTodosLosDatos

                // Función eliminada hasta que sea reparada
                // case 7 -> aplicacion.recuperarTodosLosBorrados();

                case 7 -> aplicacion.mostrarDatos(); // Llamar al método mostrarDatos
                case 8 -> continuar = false; // Cambiar la bandera para salir del bucle
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
        scanner.close();
    }
}
