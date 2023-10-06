import java.util.Scanner;

public class Menu {
    private final AplicacionLecturaEscritura aplicacion;

    public Menu(AplicacionLecturaEscritura aplicacion) {
        this.aplicacion = aplicacion;
    }

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

            /* Funcion eliminada hasta que la reparemos
            System.out.println("7. Recuperar todos los datos eliminados ()");
             */
            System.out.println("7. Mostrar datos");
            System.out.println("8. Salir");
            System.out.print("Elija una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> {
                    System.out.print("Ingrese el dato a agregar: ");
                    String dato = scanner.nextLine();
                    aplicacion.altaDato(dato);
                }
                case 2 -> {
                    System.out.print("Ingrese el índice del dato a modificar: ");
                    int indiceModificacion = scanner.nextInt();
                    scanner.nextLine();  // Consumir la nueva línea
                    System.out.print("Ingrese el nuevo dato: ");
                    String nuevoDato = scanner.nextLine();
                    aplicacion.modificarDato(indiceModificacion, nuevoDato);
                }
                case 3 -> {
                    System.out.print("Ingrese el índice del dato a eliminar: ");
                    int indiceEliminacion = scanner.nextInt();
                    aplicacion.eliminarDato(indiceEliminacion);
                }
                case 4 -> {
                    System.out.print("Ingrese el término de búsqueda: ");
                    String terminoBusqueda = scanner.nextLine();
                    aplicacion.buscarDato(terminoBusqueda);
                }
                case 5 -> aplicacion.recuperarUltimoBorrado();
                case 6 -> aplicacion.eliminarTodosLosDatos();
               /* Buscar como implementar bien esta función
                case 7 -> aplicacion.recuperarTodosLosBorrados();
                */
                case 7 -> aplicacion.mostrarDatos();
                case 8 -> continuar = false;
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
        scanner.close();
    }
}
