package es.studium.ProyectoGestion;

import java.awt.Desktop;
import java.net.URI;

public class Ayuda {
    public Ayuda() {
        try {
            // URL del archivo de Google Drive
            URI uri = new URI("https://docs.google.com/document/d/1lSTgnDWZm2GMOQy0Hfb3Mgd2tEk7Ke0mrokQeihxQ_s/edit?usp=sharing");
            //Abre el navegador
            Desktop.getDesktop().browse(uri);
            //Excepcion para el try
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}