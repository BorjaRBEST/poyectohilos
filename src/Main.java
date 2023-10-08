public class Main {
    public static void main(String[] args) {
        // Ruta del archivo donde se almacenarán los datos
        String rutaDeAlmacenamiento = "C:\\Users\\Nomak\\OneDrive\\Escritorio\\alumnos.txt";

        // Crear una instancia de AplicacionLecturaEscritura con la ruta de almacenamiento
        AplicacionLecturaEscritura aplicacion = new AplicacionLecturaEscritura(rutaDeAlmacenamiento);

        // Crear una instancia de Menu y pasar la instancia de AplicacionLecturaEscritura
        Menu menu = new Menu(aplicacion);

        // Mostrar el menú interactivo
        menu.mostrarMenu();
    }
}
// Holaaaaaa
