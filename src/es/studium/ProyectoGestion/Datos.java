package es.studium.ProyectoGestion;

import java.awt.Choice;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Datos {
    // Variables para conectar la BBDD
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/Proyecto2TrimestreProgramacion";
    String login = "juanmaRoot";
    String password = "Studium2023;";
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    private int tipoUsuario;

    // Objeto para la clase Utilidades
    Utilidades utilidades = new Utilidades();   

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    // Método para conectar a la base de datos
    public boolean conectar() {
        boolean conexionCorrecta = true;
        try {
            Class.forName(driver); // Carga el driver de la base de datos
            // Establece la conexión
            connection = DriverManager.getConnection(url, login, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Se ha producido un error al cargar el Driver");
            conexionCorrecta = false;
        } catch (SQLException e) {
            System.out.println("Se produjo un error al conectar a la Base de Datos");
            conexionCorrecta = false;
        }
        return conexionCorrecta;
    }

    // Método para comprobar las credenciales del usuario
    public boolean comprobarCredenciales(String usuario, String contrasena) {
        boolean credencialesCorrectas = true;
        String sentencia = "SELECT * FROM usuarios "
                + "WHERE nombreUsuario='" + usuario + "' "
                + "AND contrasenaUsuario = SHA2('" + contrasena + "', 256);";

        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(sentencia);
            if (rs.next()) {
                // Credenciales correctas
                tipoUsuario = rs.getInt("tipoUsuario"); // Obtiene y establece el tipo de usuario
                Utilidades.setUltimoTipoUsuario(tipoUsuario); // Actualiza el último tipo de usuario
                utilidades.guardarLog("Iniciar Sesión", sentencia);
            } else {
                // Credenciales incorrectas
                credencialesCorrectas = false;
            }
        } catch (SQLException e) {
            System.out.println("Error en la sentencia SQL:" + e.toString());
        }
        return credencialesCorrectas;
    }

    // Método para desconectar de la BBDD
    public void desconectar() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar " + e.toString());
        }
    }

    // Método para obtener los nombres de los socios
    public String dameSocios() {
        String contenido = "";
        String sentencia = "SELECT * FROM Socios;";
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(sentencia);
            while (rs.next()) {
                contenido = contenido + rs.getString("nombreSocios") + "\n";
            }
            utilidades.guardarLog("Consulta Socio", sentencia);

        } catch (SQLException e) {
            System.out.println("Error en la sentencia SQL:" + e.toString());
        }
        return contenido;
    }

    // Método para dar de alta un socio
    public boolean AltaSocio(String dni, String nombre, String primerApellido, String segundoApellido, 
            String correoElectronico, String contrasena) {
        boolean altaCorrecta = true;
        String sentencia = "INSERT INTO Socios (dniSocio, nombreSocio, primerApellidoSocio, segundoApellidoSocio, "
                + "correoElectronicoSocio, contrasenaSocio, idReservasFK)  " +
                "VALUES ('" + dni + "', '" + nombre + "', '" + primerApellido + "', '" + segundoApellido + "', '" 
                + correoElectronico + "', '" + contrasena + "', 1);";
        utilidades.guardarLog("Alta Socio", sentencia);
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.executeUpdate(sentencia);
        } catch (SQLException e) {
            System.out.println("Error en la sentencia SQL:" + e.toString());
            altaCorrecta = false;
        }
        return altaCorrecta;
    }

    // Método para obtener el tipo de usuario
    public int dameTipo(String usuario) {
        String sentencia = "SELECT tipoUsuario FROM Usuarios where nombreUsuario = '" + usuario + "';";
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(sentencia);

            if (rs.next()) {
                tipoUsuario = rs.getInt("tipoUsuario");
            }
        } catch (SQLException e) {
            System.out.println("Error en la sentencia SQL:" + e.toString());
        }
        return tipoUsuario;
    }

    // Método para eliminar un socio
    public boolean eliminarSocio(String dni, String contrasena) {
        boolean eliminacionCorrecta = false;
        String sentencia = "DELETE FROM Socios WHERE dniSocio = '" + dni + "' AND contrasenaSocio = '" + contrasena + "';";
        utilidades.guardarLog("Eliminar Socio", sentencia);

        try {
            statement = connection.createStatement();
            int filasAfectadas = statement.executeUpdate(sentencia);

            if (filasAfectadas > 0) {
                eliminacionCorrecta = true;
            }

        } catch (SQLException e) {
            System.out.println("Error en la sentencia SQL: " + e.toString());
        }

        return eliminacionCorrecta;
    }

    // Método para obtener todos los socios
    public ResultSet obtenerSocios() {
        ResultSet resultado = null;
        String sentencia = "SELECT idSocios, dniSocio, nombreSocio, primerApellidoSocio, segundoApellidoSocio,"
                + " correoElectronicoSocio, contrasenaSocio from Socios;";
        utilidades.guardarLog("Consulta Socio", sentencia);

        try {
            statement = connection.createStatement();
            resultado = statement.executeQuery(sentencia);
        } catch (SQLException e) {
            System.out.println("Error en la sentencia SQL: " + e.toString());
        }
        return resultado;
    }

    // Método para obtener los socios para modificar
    public ResultSet ModificacionSocios()  {
        ResultSet resultado = null;
        String sentencia = "SELECT dniSocio, nombreSocio, "
                + "primerApellidoSocio, segundoApellidoSocio, correoElectronicoSocio, contrasenaSocio from Socios;";

        try {
            statement = connection.createStatement();
            resultado = statement.executeQuery(sentencia);
        } catch (SQLException e) {
            System.out.println("Error en la sentencia SQL: " + e.toString());
        }
        return resultado;
    }

    // Método para modificar un socio
    public boolean modificarSocio(String dni, String nombre, String primerApellido, String segundoApellido, String correoElectronico, String contrasena) {
        boolean modificacionCorrecta = false;
        // Sentencia SQL para actualizar la información del socio
        String sentencia = "UPDATE Socios SET nombreSocio = '" + nombre + "', primerApellidoSocio = '" + primerApellido + "', segundoApellidoSocio = '" + segundoApellido + "', correoElectronicoSocio = '" + correoElectronico + "', contrasenaSocio = '" + contrasena + "' WHERE dniSocio = '" + dni + "';";
        utilidades.guardarLog("Modificación Socio", sentencia); // Script para guardar las acciones en el fichero log
        try { // Esto se utiliza para comprobar si la sentencia sql esta bien
            statement = connection.createStatement(); // Crea un objeto Statement para procesar la sentencia y guardar el resultado
            int filasAfectadas = statement.executeUpdate(sentencia); // Ejecuta la sentencia SQL y obtiene el número de filas afectadas
            if (filasAfectadas > 0) {
                modificacionCorrecta = true; // Si se afectaron las filas, la modificación es correcta
            }
        } catch (SQLException e) {
            System.out.println("Error en la sentencia SQL: " + e.toString());
        }

        return modificacionCorrecta;
    }

    public void cargarSocios(Choice choice) {
        choice.removeAll(); // Elimina todos los elementos del Choice
        ResultSet rs = obtenerSocios(); // Obtiene el ResultSet con los socios
        try {
            while (rs.next()) {  // Itera sobre cada fila del ResultSet
                 // Crea una cadena con el DNI y el nombre del socio
                String socio = rs.getString("dniSocio") + " - " + rs.getString("nombreSocio");
                choice.add(socio); // Añade la cadena al Choice
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar los socios: " + e.getMessage());
        }
    }

    // Método para dar de alta una reserva
    public boolean altaReservas(String socio, String fecha, String precio, String pista) throws SQLException {
        String[] socioParts = socio.split(" - "); // Con el split dividimos la cadena en partes 
        String[] pistaParts = pista.split(" - "); // Y repetimos con pista
        // Creamos la sentencia SQL
        String sentencia = "INSERT INTO Reservas (idSociosFK, fechaReserva, precioReserva, idPistasFK) " +
                "SELECT s.idSocios, '" + fecha + "', " + precio.replace("€", "").trim() + ", p.idPistas " +
                "FROM Socios s JOIN Pistas p " +
                "ON s.dniSocio = '" + socioParts[0] + "' AND p.nombrePista = '" + pistaParts[0] + "' AND p.tipoPista = '" + pistaParts[1] + "'";
        utilidades.guardarLog("Alta Reserva", sentencia); // Script para guardar las acciones en el fichero log

        try {
            statement = connection.createStatement(); // Crea un objeto Statement
            int filasAfectadas = statement.executeUpdate(sentencia); // Ejecuta la sentencia y obtiene el número de filas afectadas
            return filasAfectadas > 0; // Devuelve true si han sido afectadas y false si no
        } catch (SQLException e) {
            System.out.println("Error en la sentencia SQL: " + e.toString());
            return false;
        }
    }

    // Método para mostrar las pistas
    public ResultSet mostrarPistas() {
        ResultSet resultado = null;
        String sentencia = "SELECT idPistas, nombrePista, zonaPista, tipoPista from Pistas;";
        utilidades.guardarLog("Consulta Pistas", sentencia); // Script para guardar las acciones en el fichero log

        try {
            statement = connection.createStatement(); // Crea un objeto Statement
            resultado = statement.executeQuery(sentencia); // Ejecuta la sentencia SQL y obtiene el ResultSet
        } catch (SQLException e) {
            System.out.println("Error en la sentencia SQL: " + e.toString());
        }
        return resultado; // Devuelve el ResultSet
    }

    // Método para mostrar las reservas
    public ResultSet mostrarReservas(Connection connection) {
        ResultSet resultado = null;
        String sentencia = "SELECT idReservas, precioReserva, fechaReserva, idSociosFK, idPistasFK FROM Reservas;";
        try {
            Statement statement = connection.createStatement(); // Crea un objeto Statement
            resultado = statement.executeQuery(sentencia); // Ejecuta la sentencia SQL y obtiene el ResultSet
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta SQL: " + e.getMessage());
        }
        return resultado;
    }

    // Método para obtener todas las pistas
    public ResultSet obtenerPistas() {
        ResultSet resultado = null;
        String sentencia = "SELECT idPistas, nombrePista, zonaPista, tipoPista from Pistas;";
        utilidades.guardarLog("Consulta Pistas", sentencia); // Script para guardar las acciones en el fichero log

        try {
            statement = connection.createStatement();
            resultado = statement.executeQuery(sentencia);
        } catch (SQLException e) {
            System.out.println("Error en la sentencia SQL: " + e.toString());
        }
        return resultado;
    }

    public ResultSet obtenerReservas() {
        ResultSet resultado = null;
        String sentencia = "SELECT Reservas.idReservas, Reservas.precioReserva, Reservas.fechaReserva, Reservas.idSociosFK, Socios.dniSocio, Reservas.idPistasFK " +
                "FROM Reservas " +
                "JOIN Socios ON Reservas.idSociosFK = Socios.idSocios;";
        utilidades.guardarLog("Consulta Reserva", sentencia); // Script para guardar las acciones en el fichero log

        try {
            statement = connection.createStatement();
            resultado = statement.executeQuery(sentencia);
        } catch (SQLException e) {
            System.out.println("Error en la sentencia SQL: " + e.toString());
        }
        return resultado;
    }

    public boolean altaPista(String nombre, String zona, String tipo, String idReserva) {
        boolean altaCorrecta = false;
        String sentencia = "INSERT INTO Pistas (nombrePista, zonaPista, tipoPista, idReservasFK) VALUES ('" + nombre + "', '" + zona + "', '" + tipo + "', " + idReserva + ");";
        utilidades.guardarLog("Alta Pista", sentencia); // Script para guardar las acciones en el fichero log

        try {
            statement = connection.createStatement();
            int filasAfectadas = statement.executeUpdate(sentencia);

            if (filasAfectadas > 0) {
                altaCorrecta = true;
            }

        } catch (SQLException e) {
            System.out.println("Error en la sentencia SQL: " + e.toString());
        }

        return altaCorrecta;
    }

    public boolean eliminarPistaPorId(String idPista) {
        boolean eliminacionCorrecta = false;
        // Sentencia SQL para eliminar una pista por ID
        String sentencia = "DELETE FROM Pistas WHERE idPistas = " + idPista + ";";
        utilidades.guardarLog("Eliminar Pista", sentencia); // Script para guardar las acciones en el fichero log

        try {
            statement = connection.createStatement();
            int filasAfectadas = statement.executeUpdate(sentencia);

            if (filasAfectadas > 0) {
                eliminacionCorrecta = true;
            }

        } catch (SQLException e) {
            System.out.println("Error en la sentencia SQL: " + e.toString());
        }

        return eliminacionCorrecta; // Devuelve si la eliminación fue correcta o no
    }

    // Método para eliminar una reserva
    public boolean eliminarReserva(String precio, String fecha, String idSocio) {
        boolean eliminacionCorrecta = false;
        String sentencia = "DELETE FROM Reservas WHERE precioReserva = '" + precio + "' AND fechaReserva = '" + fecha + "' AND idSociosFK = '" + idSocio + "';";
        utilidades.guardarLog("Eliminar Reserva", sentencia); // Script para guardar las acciones en el fichero log

        try {
            statement = connection.createStatement();
            int filasAfectadas = statement.executeUpdate(sentencia);

            if (filasAfectadas > 0) {
                eliminacionCorrecta = true;
            }

        } catch (SQLException e) {
            System.out.println("Error en la sentencia SQL: " + e.toString());
        }

        return eliminacionCorrecta;
    }

    // Método para modificar una reserva
    public boolean modificarReservas(String dni, String fecha, String precio, String pista) {
        boolean modificacionCorrecta = false;
        String sentencia = "UPDATE Reservas SET fechaReserva = '" + fecha + "', precioReserva = '" + precio + "', idPistasFK = '" + pista + "' WHERE idSociosFK = (SELECT idSocios FROM Socios WHERE dniSocio = '" + dni + "');";
        utilidades.guardarLog("Modificación Reserva", sentencia); // Script para guardar las acciones en el fichero log
        
        try {
            statement = connection.createStatement();
            int filasAfectadas = statement.executeUpdate(sentencia);
            if (filasAfectadas > 0) {
                modificacionCorrecta = true;
            }
        } catch (SQLException e) {
            System.out.println("Error en la sentencia SQL: " + e.toString());
        }

        return modificacionCorrecta;
    }

    public boolean modificarPista(String idPista, String nombre, String zona, String tipo) {
        boolean modificacionCorrecta = false;
        String sentencia = "UPDATE Pistas SET nombrePista = '" + nombre + "', zonaPista = '" + zona + "', tipoPista = '" + tipo + "' WHERE idPistas = " + idPista + ";";
        utilidades.guardarLog("Modificar Pista", sentencia); // Script para guardar las acciones en el fichero log

        try {
            statement = connection.createStatement();
            int filasAfectadas = statement.executeUpdate(sentencia);

            if (filasAfectadas > 0) {
                modificacionCorrecta = true;
            }

        } catch (SQLException e) {
            System.out.println("Error en la sentencia SQL: " + e.toString());
        }

        return modificacionCorrecta;
    }

    public ResultSet obtenerPistaPorId(String idPista) {
        ResultSet resultado = null;
        String sentencia = "SELECT nombrePista, zonaPista, tipoPista FROM Pistas WHERE idPistas = " + idPista + ";";
        utilidades.guardarLog("Consulta Pista por ID", sentencia); // Script para guardar las acciones en el fichero log

        try {
            statement = connection.createStatement();
            resultado = statement.executeQuery(sentencia);
        } catch (SQLException e) {
            System.out.println("Error en la sentencia SQL: " + e.toString());
        }
        return resultado;
    }

    // Metodo para convertir una fecha
    public String convertirFechaAEuropea(String fecha) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date fechaDate = formatoEntrada.parse(fecha);
            return formatoSalida.format(fechaDate);
        } catch (ParseException e) {
            System.out.println("Error al convertir la fecha: " + e.getMessage());
            return null;
        }
    }
}
