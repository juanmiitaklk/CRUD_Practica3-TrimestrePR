package es.studium.ProyectoGestion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GuardarLog {

    public void guardarLog(int tipoUsuario, String mensaje) {
        String usuario;

        // Determina el tipo de usuario basado en el valor de tipoUsuario
        if (tipoUsuario == 1) {
            usuario = "Básico";
        } else {
            usuario = "Administrador";
        }

     // Crea un objeto Date con la fecha y hora actuales
        Date fecha = new Date();
     // Define el formato de fecha y hora
        String pattern = "dd/MM/yyyy HH:mm:ss";
     // Crea un formateador de fecha con el patrón especificado
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        try {
            FileWriter fw = new FileWriter("fichero.log", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter salida = new PrintWriter(bw);

            // Escribe una línea en el archivo de log con la fecha, tipo de usuario y mensaje
            salida.println("[" + simpleDateFormat.format(fecha) + "][" + usuario + "][" + mensaje + "]");

            // Cierra los escritores en el orden inverso al que fueron abiertos
            salida.close();
            bw.close();
            fw.close();
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe.getMessage());
        }
    }
}
