package cine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Clase para gestionar la conexión a la base de datos MySQL y las consultas
public class DatabaseConnection {
    // Constantes para la URL, usuario y contraseña de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/cine_AlejandraTenaMuñoz";
    private static final String USER = "root";
    private static final String PASSWORD = "curso"; 

    // Obtiene una conexión a la base de datos
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Obtiene todas las películas con sus géneros desde la base de datos
    public List<Pelicula> getPeliculas() throws SQLException {
        // Lista para almacenar las películas
        List<Pelicula> peliculas = new ArrayList<>();
        // Consulta SQL para unir las tablas peliculas y generos
        String query = "SELECT p.id_pelicula, p.titulo, p.director, p.anio, p.duracion_minutos, " +
                      "g.nombre AS nombre_genero " +
                      "FROM peliculas p JOIN generos g ON p.id_genero = g.id_genero";

        // Usa try-with-resources para cerrar automáticamente la conexión, el statement y el resultset
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            // Itera sobre los resultados de la consulta
            while (rs.next()) {
                // Crea un objeto Pelicula con los datos de cada fila
                Pelicula pelicula = new Pelicula(
                    rs.getString("id_pelicula"),
                    rs.getString("titulo"),
                    rs.getString("director"),
                    rs.getInt("anio"),
                    rs.getInt("duracion_minutos"),
                    rs.getString("nombre_genero")
                );
                // Añade la película a la lista
                peliculas.add(pelicula);
            }
        }
        // Devuelve la lista de películas
        return peliculas;
    }
}
