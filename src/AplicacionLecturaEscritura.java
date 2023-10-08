import java.io.*;
import java.util.*;

public class AplicacionLecturaEscritura {
    private final List<String> datos = new ArrayList<>(); // Almacena los datos en memoria
    private final Stack<String> elementosBorrados = new Stack<>(); // Almacena los elementos eliminados temporalmente
    private final String rutaDeAlmacenamiento; // Ruta del archivo donde se guarda la información permanente
    private final PipedOutputStream pipedOutputStream; // Para enviar mensajes a través de un flujo de tubería
    private PipedInputStream pipedInputStream; // Para recibir mensajes a través de un flujo de tubería
    private final Thread escrituraThread; // Hilo para escribir datos en el archivo
    private final Thread lecturaThread; // Hilo para cargar datos desde el archivo

    public AplicacionLecturaEscritura(String rutaDeAlmacenamiento) {
        this.rutaDeAlmacenamiento = rutaDeAlmacenamiento;
        cargarDatosDesdeArchivo(); // Cargar datos existentes desde el archivo
        pipedOutputStream = new PipedOutputStream(); // Inicializar el flujo de salida
        try {
            pipedInputStream = new PipedInputStream(pipedOutputStream); // Inicializar el flujo de entrada
        } catch (IOException e) {
            e.printStackTrace();
        }
        lecturaThread = new Thread(this::cargarDatosDesdeArchivo); // Iniciar hilo para cargar datos
        escrituraThread = new Thread(this::guardarDatosEnArchivo); // Iniciar hilo para guardar datos

        lecturaThread.start(); // Iniciar hilo de lectura
        escrituraThread.start(); // Iniciar hilo de escritura
    }
    // Método para dar de alta
    public void altaDato(String dato) {
        datos.add(dato); // Agregar dato a la lista en memoria
        guardarDatosEnArchivo(); // Guardar los datos en el archivo
        enviarMensaje("Dato agregado: " + dato);
    }
    // Método para modificar datos según índice que marcamos
    public void modificarDato(int indice, String nuevoDato) {
        if (indice >= 0 && indice < datos.size()) {
            datos.set(indice, nuevoDato); // Modificar dato en la lista en memoria
            guardarDatosEnArchivo(); // Actualizar el archivo con los datos modificados
            enviarMensaje("Dato modificado: " + nuevoDato);
        } else {
            System.out.println("Índice fuera de rango.");
        }
    }
    // Método para eliminar dato según índice
    public void eliminarDato(int indice) {
        if (indice >= 0 && indice < datos.size()) {
            String borrado = datos.remove(indice); // Eliminar dato de la lista en memoria
            elementosBorrados.push(borrado); // Almacenar el dato eliminado temporalmente
            guardarDatosEnArchivo(); // Actualizar el archivo sin el dato eliminado
            enviarMensaje("Dato eliminado: " + borrado);
        } else {
            System.out.println("Índice fuera de rango.");
        }
    }
    // Método de recuperación de último elemento eliminado
    public void recuperarUltimoBorrado() {
        if (!elementosBorrados.isEmpty()) {
            String ultimoBorrado = elementosBorrados.pop(); // Recuperar el último dato eliminado
            datos.add(ultimoBorrado); // Agregar el dato de nuevo a la lista en memoria
            guardarDatosEnArchivo(); // Actualizar el archivo con el dato recuperado
            enviarMensaje("Último elemento borrado recuperado: " + ultimoBorrado);
        } else {
            System.out.println("No hay elementos borrados para recuperar.");
        }
    }
    // Método público de búsqueda de datos según dato de búsqueda
    public void buscarDato(String busqueda) {
        System.out.println("Resultados de la búsqueda:");
        for (String dato : datos) {
            if (dato.contains(busqueda)) {
                System.out.println(dato);
            }
        }
    }
    // Método público para eliminar todos los datos de la lista en memoria
    public void eliminarTodosLosDatos() {
        datos.clear();
        elementosBorrados.clear(); // Limpiar la pila de elementos borrados
        guardarDatosEnArchivo(); // Actualizar el archivo vacío
        enviarMensaje("Todos los datos han sido eliminados."); // Enviar mensaje sobre la operación
    }

    /* Método para recuperar todos los datos borrados, tengo que seguir viendo
      *porque no me recupera los elementos eliminados
    public void recuperarTodosLosBorrados() {
        if (!elementosBorrados.isEmpty()) {
            while (!elementosBorrados.isEmpty()) {
                String borrado = elementosBorrados.pop();
                datos.add(borrado);
                enviarMensaje("Elemento borrado recuperado: " + borrado);
            }
            guardarDatosEnArchivo();
        } else {
            System.out.println("No hay elementos borrados para recuperar.");
        }
    }
     */

    // Mostrar los datos actuales con índices
    public void mostrarDatos() {
        System.out.println("Datos actuales:");
        for (int i = 0; i < datos.size(); i++) {
            System.out.println(i + ": " + datos.get(i));
        }
    }

    // Método privado para guardar los datos en el archivo
    private void guardarDatosEnArchivo() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaDeAlmacenamiento))) {
            for (String dato : datos) {
                writer.println(dato); // Escribir cada dato en una línea del archivo
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método privado para cargar datos desde el archivo
    private void cargarDatosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaDeAlmacenamiento))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                datos.add(linea); // Leer cada línea del archivo y agregarla a la lista en memoria
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método privado para enviar un mensaje a través del flujo de tubería
    private void enviarMensaje(String mensaje) {
        try {
            pipedOutputStream.write(mensaje.getBytes()); // Escribir el mensaje en el flujo de tubería
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Obtener el flujo de entrada
    public PipedInputStream getPipedInputStream() {
        return pipedInputStream;
    }
}
