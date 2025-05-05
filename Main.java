package cine;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

// Clase principal para gestionar el menú y las operaciones del cine
public class Main {
    // Scanner para leer la entrada del usuario
    private static final Scanner scanner = new Scanner(System.in);
    // Instancia de DatabaseConnection para conectar con la base de datos
    private static final DatabaseConnection db = new DatabaseConnection();

    // Método principal que inicia la aplicación
    public static void main(String[] args) {
        boolean salir = false;

        // Bucle que muestra el menú hasta que el usuario elige salir
        while (!salir) {
            mostrarMenu();
            try {
                // Lee la opción del usuario y la convierte a entero
                int opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        // Muestra la lista de películas
                        verPeliculas();
                        break;
                    case 2:
                        // Sale de la aplicación
                        System.out.println("Saliendo de la aplicación...");
                        salir = true;
                        break;
                    default:
                        // Mensaje para opciones no válidas
                        System.out.println("Opción no válida. Por favor, selecciona 1 o 2.");
                }
            } catch (NumberFormatException e) {
                // Maneja entradas no numéricas
                System.out.println("Error: Ingresa un número válido.");
            }
        }
        // Cierra el scanner
        scanner.close();
    }

    // Muestra el menú de opciones
    private static void mostrarMenu() {
        System.out.println("\n=== Menú Cine ===");
        System.out.println("1 - Ver películas");
        System.out.println("2 - Salir");
        System.out.print("Selecciona una opción: ");
    }

    // Muestra la lista de películas de la base de datos
    private static void verPeliculas() {
        try {
            // Obtiene la lista de películas desde la base de datos
            List<Pelicula> peliculas = db.getPeliculas();
            if (peliculas.isEmpty()) {
                // Mensaje si no hay películas
                System.out.println("No hay películas disponibles.");
                return;
            }

            // Imprime el encabezado de la tabla
            System.out.println("\n=== Lista de Películas ===");
            System.out.println("ID        Título                        Director                 Año   Duración   Género");
            System.out.println("----------------------------------------------------------------------------------------------------");

            // Itera sobre las películas para mostrar sus datos
            for (Pelicula p : peliculas) {
                // Ajusta los campos con espacios para alinear la salida
                String id = p.getIdPelicula() + "         ".substring(p.getIdPelicula().length());
                String titulo = p.getTitulo() + "                             ".substring(p.getTitulo().length());
                String director = p.getDirector() + "                        ".substring(p.getDirector().length());
                String anio = p.getAnio() + "      ".substring(String.valueOf(p.getAnio()).length());
                String duracion = p.getDuracionMinutos() + "          ".substring(String.valueOf(p.getDuracionMinutos()).length());
                String genero = p.getGenero();

                // Imprime la fila de la película
                System.out.println(id + titulo + director + anio + duracion + genero);
            }
        } catch (SQLException e) {
            // Maneja errores al consultar la base de datos
            System.err.println("Error al consultar las películas: " + e.getMessage());
        }
    }
}
