package es.studium.ProyectoGestion;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilidades {

    private static int ultimoTipoUsuario = -1;  // Significa que no hay ningun usuario logueado

    public static void setUltimoTipoUsuario(int tipoUsuario) {
        ultimoTipoUsuario = tipoUsuario;
    }

    public static int getUltimoTipoUsuario() {
        return ultimoTipoUsuario;
    }

    public String guardarLog(String mensaje, String sentenciaSql) {
        String usuario = meTienesLoco(ultimoTipoUsuario);

        // Obtiene la fecha y hora actual
        String fecha = obtenerFechaHoraActual();
        // Nombre del archivo de log
        String archivoLog = "fichero.log";

        try (FileWriter fw = new FileWriter(archivoLog, true);
             PrintWriter pw = new PrintWriter(fw)) {
            // Escribe una línea en el archivo de log con la fecha, tipo de usuario, mensaje y sentencia SQL
            pw.println("[" + fecha + "] [" + usuario + "] [" + mensaje + "] [" + sentenciaSql + "]");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return archivoLog;
    }

    // Método para determinar el tipo de usuario
    private String meTienesLoco(int tipoUsuario) {
        switch (tipoUsuario) {
            case 1:
                return "Básico";
            default:
                return "Administrador";
        }
    }

    // Método para obtener la fecha y hora actual formateada
    private String obtenerFechaHoraActual() {
        // Define el formato de fecha y hora
        SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        // Crea un objeto Date con la fecha y hora actuales
        Date fechaHoraActual = new Date();
        // Devuelve la fecha y hora formateada
        return formatoFechaHora.format(fechaHoraActual);
    }
}
