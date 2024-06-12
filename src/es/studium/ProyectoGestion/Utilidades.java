package es.studium.ProyectoGestion;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilidades {

    public String guardarLog(int tipoUsuario, String Mensaje, String SentenciasSql) {
        String usuario = meTienesLoco(tipoUsuario);
        
        // Obtiene la fecha y hora actual
        String fecha = obtenerFechaHoraActual();
        // Nombre del archivo de log
        String archivoLog = "fichero.log";

        try (FileWriter fw = new FileWriter(archivoLog, true);
             PrintWriter pw = new PrintWriter(fw)) {
            // Escribe una línea en el archivo de log con la fecha, tipo de usuario, mensaje y sentencia SQL
            pw.println("[" + fecha + "] [" + usuario + "] [" + Mensaje + "] [" + SentenciasSql + "]");
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
            case 2:
                return "Administrador";
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
