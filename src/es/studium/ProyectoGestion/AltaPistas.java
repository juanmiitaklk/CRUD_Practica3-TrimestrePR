package es.studium.ProyectoGestion;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;




public class AltaPistas implements WindowListener, ActionListener {
    Frame ventana = new Frame("Alta Pista");

    Label nombrePistalbl = new Label("Nombre");
    TextField nombrePistaTXT = new TextField(" ");
    Label zonaPistalbl = new Label("Zona");
    TextField zonaPistaTXT = new TextField(" ");
    Label tipoPistalbl = new Label("Tipo");
    TextField tipoPistaTXT = new TextField(" ");
    Label reservaPistalbl = new Label("Reserva");
    Choice reservasChoice = new Choice();

    Button aceptarbtn = new Button("Aceptar");
    Button limpiarbtn = new Button("Limpiar");

    Datos datos = new Datos();
    Dialog mensaje = new Dialog(ventana, "Mensaje", true);
    Label lblMensaje = new Label("");

    AltaPistas() {
        ventana.setLayout(new GridLayout(5, 2, 10, 10)); 
        ventana.setSize(400, 300);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);

        aceptarbtn.addActionListener(this);
        limpiarbtn.addActionListener(this);

        ventana.addWindowListener(this);

        ventana.add(nombrePistalbl);
        ventana.add(nombrePistaTXT);
        ventana.add(zonaPistalbl);
        ventana.add(zonaPistaTXT);
        ventana.add(tipoPistalbl);
        ventana.add(tipoPistaTXT);
        ventana.add(reservaPistalbl);
        ventana.add(reservasChoice);

        ventana.add(aceptarbtn);
        ventana.add(limpiarbtn);

        //Llamamos al metodo para hacer la consulta a la BBDD
        cargarReservas();

        mensaje.setLayout(new FlowLayout());
        mensaje.setSize(250, 110);
        mensaje.setResizable(false);
        mensaje.setLocationRelativeTo(null);
        mensaje.add(lblMensaje);

        //Correciones para que no haya problemas a la hora de cerrar el dialog
        mensaje.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mensaje.dispose();
            }
        });
        
        //Correciones para que no haya problemas a la hora de cerrar el dialog
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ventana.dispose();
            }
        });

        ventana.setVisible(true);
    }

    //Metodo para cargar las Reservas en el choice desde la BBDD
    private void cargarReservas() {
        datos.conectar();
        ResultSet reservas = datos.obtenerReservas();
        try {
        	// Itera sobre cada fila del Rs
        	//Mueve el cursor a la siguiente fila del ResultSet. Devuelve true si hay otra fila si no false
            while (reservas.next()) {
            	//Obtiene el valor de la columna idReservas de la fila actual como un String
                String idReserva = reservas.getString("idReservas");
                //Crea una " descripcion " usando el idReserva
                String descripcion = "ID Reserva: " + idReserva;
                //Añadimos la descripcion creada anteriormente al choice
                reservasChoice.add(descripcion);
            }
        } catch (SQLException e) {
            System.out.println("Error al procesar el ResultSet: " + e.toString());
        }
        datos.desconectar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == aceptarbtn) {
            datos.conectar();
            // Obtiene el item de reservasChoice y lo almacena en reservaSeleccionada
            String reservaSeleccionada = reservasChoice.getSelectedItem();
            //Con el split extraemos el id de la cadena que hemos seleccionado y lo guaradamos en idReserva
            String idReserva = reservaSeleccionada.split(": ")[1]; 

            // Llamamos al método altaCorrecta de datos y le pasamos los valores del campo de texto 
            //y el idReserva que hemos guardado antes, guardamos el resultado booleano en altaCorrecta
            boolean altaCorrecta = datos.altaPista(nombrePistaTXT.getText(), zonaPistaTXT.getText(),
                    tipoPistaTXT.getText(), idReserva);

            if (altaCorrecta) {
            	//Si es true:
                lblMensaje.setText("Pista creada correctamente");
            } else {
            	// si es false:
                lblMensaje.setText("Se ha producido un error");
            }
            mensaje.setVisible(true);
            datos.desconectar();
            
            //Configuramos el boton limpiara para que nos borre los valores que tenga el text area
        } else if (e.getSource() == limpiarbtn) { 
            nombrePistaTXT.setText("");
            zonaPistaTXT.setText("");
            tipoPistaTXT.setText("");
            reservasChoice.select(0); // Seleccionar la primera opción del Choice
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void windowClosing(WindowEvent e) {
        ventana.dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    public static void main(String[] args) {
        new AltaPistas();
    }
}
