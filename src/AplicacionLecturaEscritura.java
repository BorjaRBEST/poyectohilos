import java.io.*;
import java.util.*;

public class AplicacionLecturaEscritura {
    private final List<String> datos = new ArrayList<>();
    private final Stack<String> elementosBorrados = new Stack<>();
    private final String rutaDeAlmacenamiento;
    private final PipedOutputStream pipedOutputStream;
    private PipedInputStream pipedInputStream;
    private final Thread escrituraThread;
    private final Thread lecturaThread;

    public AplicacionLecturaEscritura(String rutaDeAlmacenamiento) {
        this.rutaDeAlmacenamiento = rutaDeAlmacenamiento;
        cargarDatosDesdeArchivo();
        pipedOutputStream = new PipedOutputStream();
        try {
            pipedInputStream = new PipedInputStream(pipedOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        lecturaThread = new Thread(this::cargarDatosDesdeArchivo);
        escrituraThread = new Thread(this::guardarDatosEnArchivo);

        lecturaThread.start();
        escrituraThread.start();
    }

    public void altaDato(String dato) {
        datos.add(dato);
        guardarDatosEnArchivo();
        enviarMensaje("Dato agregado: " + dato);
    }

    public void modificarDato(int indice, String nuevoDato) {
        if (indice >= 0 && indice < datos.size()) {
            datos.set(indice, nuevoDato);
            guardarDatosEnArchivo();
            enviarMensaje("Dato modificado: " + nuevoDato);
        } else {
            System.out.println("Índice fuera de rango.");
        }
    }

    public void eliminarDato(int indice) {
        if (indice >= 0 && indice < datos.size()) {
            String borrado = datos.remove(indice);
            elementosBorrados.push(borrado);
            guardarDatosEnArchivo();
            enviarMensaje("Dato eliminado: " + borrado);
        } else {
            System.out.println("Índice fuera de rango.");
        }
    }
    public void recuperarUltimoBorrado() {
        if (!elementosBorrados.isEmpty()) {
            String ultimoBorrado = elementosBorrados.pop();
            datos.add(ultimoBorrado);
            guardarDatosEnArchivo();
            enviarMensaje("Último elemento borrado recuperado: " + ultimoBorrado);
        } else {
            System.out.println("No hay elementos borrados para recuperar.");
        }
    }

    public void buscarDato(String busqueda) {
        System.out.println("Resultados de la búsqueda:");
        for (String dato : datos) {
            if (dato.contains(busqueda)) {
                System.out.println(dato);
            }
        }
    }

    public void eliminarTodosLosDatos() {
        datos.clear();
        elementosBorrados.clear();
        guardarDatosEnArchivo();
        enviarMensaje("Todos los datos han sido eliminados.");
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

    public void mostrarDatos() {
        System.out.println("Datos actuales:");
        for (int i = 0; i < datos.size(); i++) {
            System.out.println(i + ": " + datos.get(i));
        }
    }
    private void guardarDatosEnArchivo() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaDeAlmacenamiento))) {
            for (String dato : datos) {
                writer.println(dato);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarDatosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaDeAlmacenamiento))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                datos.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enviarMensaje(String mensaje) {
        try {
            pipedOutputStream.write(mensaje.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PipedInputStream getPipedInputStream() {
        return pipedInputStream;
    }
}
