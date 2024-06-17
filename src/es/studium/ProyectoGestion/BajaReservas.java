package es.studium.ProyectoGestion;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BajaReservas implements ActionListener, WindowListener {

    Frame ventana = new Frame("Baja Reservas");
    Choice reservasChoice = new Choice();
    Button eliminarbtn = new Button("Eliminar");
    Label Tlbl = new Label("Selecciona una reserva para eliminar");

    Datos datos = new Datos();
    Dialog mensaje = new Dialog(ventana, "Mensaje", true);
    Label lblMensaje = new Label("");

    Dialog confirmacion = new Dialog(ventana, "Confirmación", true);
    Label lblConfirmacion = new Label("¿Estás seguro de que quieres eliminar esta reserva?");
    Button btnSi = new Button("Sí");
    Button btnNo = new Button("No");

    BajaReservas() {
        ventana.setLayout(new GridLayout(3, 1, 10, 10)); 
        ventana.setSize(400, 200); 
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);

        cargarReservas();

        eliminarbtn.addActionListener(this);

        ventana.add(Tlbl);
        ventana.add(reservasChoice);
        ventana.add(eliminarbtn);

        mensaje.setLayout(new FlowLayout());
        mensaje.setSize(250, 100);
        mensaje.setResizable(false);
        mensaje.setLocationRelativeTo(null);
        mensaje.add(lblMensaje);

        confirmacion.setLayout(new GridLayout(2, 1));
        confirmacion.setSize(300, 150);
        confirmacion.setResizable(false);
        confirmacion.setLocationRelativeTo(null);

        Panel panelConfirmacion = new Panel();
        panelConfirmacion.setLayout(new FlowLayout());
        panelConfirmacion.add(btnSi);
        panelConfirmacion.add(btnNo);

        confirmacion.add(lblConfirmacion);
        confirmacion.add(panelConfirmacion);

        btnSi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarReserva();
                confirmacion.setVisible(false);
            }
        });

        btnNo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmacion.setVisible(false);
            }
        });

        // Correcciones para que el diálogo se cierre correctamente
        mensaje.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mensaje.dispose();
            }
        });

        confirmacion.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmacion.dispose();
            }
        });

        // Correcciones para que la ventana se cierre correctamente
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ventana.dispose();
            }
        });

        ventana.setVisible(true);
    }
    //Metodo para cargar las reservas desde la BBDD y meterlos en un choice
    private void cargarReservas() {
        datos.conectar();
        ResultSet reservas = datos.obtenerReservas(); 
        try {
            while (reservas.next()) { 
                String precio = reservas.getString("precioReserva");
                String fecha = reservas.getString("fechaReserva");
                String idSocio = reservas.getString("idSociosFK");
                String descripcion = "Precio: " + precio + ", Fecha: " + fecha + ", ID Socio: " + idSocio;
                reservasChoice.add(descripcion);
            }
        } catch (SQLException e) {
            System.out.println("Error al procesar el ResultSet: " + e.toString());
        }
        datos.desconectar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == eliminarbtn) {
            confirmacion.setVisible(true);
        }
    }

    //Metodo para eliminar las reservas
    private void eliminarReserva() {
    	//Creamos una variable para almacenar el valor, y llamaos al elemeto seleccionado de "reservasChoice"
        String reservaSeleccionada = reservasChoice.getSelectedItem();
        //Dividimos la cadena en partes separada por comas y extraemos los valores individuales
        String[] partes = reservaSeleccionada.split(", ");
        String precio = partes[0].split(": ")[1];
        String fecha = partes[1].split(": ")[1];
        String idSocio = partes[2].split(": ")[1];

        datos.conectar();
        boolean eliminacionCorrecta = datos.eliminarReserva(precio, fecha, idSocio);

        if (eliminacionCorrecta) {
            lblMensaje.setText("Reserva eliminada correctamente");
            reservasChoice.remove(reservaSeleccionada);
        } else {
            lblMensaje.setText("No se pudo eliminar la reserva");
        }
        mensaje.setVisible(true);
        datos.desconectar();
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

}
