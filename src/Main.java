public class Main {
    public static void main(String[] args) {
        String rutaDeAlmacenamiento = "C:\\Users\\Nomak\\OneDrive\\Escritorio\\alumnos.txt";
        AplicacionLecturaEscritura aplicacion = new AplicacionLecturaEscritura(rutaDeAlmacenamiento);

        Menu menu = new Menu(aplicacion);
        menu.mostrarMenu();
    }
}
