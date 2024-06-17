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

public class BajaPistas implements ActionListener, WindowListener {
    Frame ventana = new Frame("Baja Pistas");

    Label pistaLabel = new Label("Selecciona una pista para eliminar:");
    Choice pistasChoice = new Choice();
    Button eliminarbtn = new Button("Eliminar");

    Datos datos = new Datos();
    Dialog mensaje = new Dialog(ventana, "Mensaje", true);
    Label lblMensaje = new Label("");

    Dialog confirmacion = new Dialog(ventana, "Confirmación", true);
    Label lblConfirmacion = new Label("¿Estás seguro de que quieres eliminar esta pista?");
    Button btnSi = new Button("Sí");
    Button btnNo = new Button("No");

    BajaPistas() {
        ventana.setLayout(new GridLayout(3, 1, 10, 10));
        ventana.setSize(400, 200);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);

        eliminarbtn.addActionListener(this);

        ventana.add(pistaLabel);
        ventana.add(pistasChoice);
        ventana.add(eliminarbtn);

        // Llamamos al método para cargar las pistas desde la BBDD
        cargarPistas();

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
                eliminarPista();
                confirmacion.setVisible(false);
            }
        });

        btnNo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmacion.setVisible(false);
            }
        });

        // Correcciones para que no haya problemas al cerrar el dialogo sin botones
        mensaje.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mensaje.dispose();
            }
        });
        // Correcciones para que no haya problemas al cerrar el dialogo de si/no
        confirmacion.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmacion.dispose();
            }
        });

        // Correcciones para que no haya problemas al cerrar la ventana
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ventana.dispose();
            }
        });

        ventana.setVisible(true);
    }

    // Método para cargar las pistas en el Choice desde la BBDD
    private void cargarPistas() {
        datos.conectar();
        ResultSet pistas = datos.obtenerPistas(); // Obtiene las pistas desde la BBDD
        try {
            // Iteramos sobre cada fila del ResultSet
            while (pistas.next()) { // Se mueve sobre las filas del ResultSet y devuelve un booleano
                String idPista = pistas.getString("idPistas");
                String nombre = pistas.getString("nombrePista");
                // Creamos una descripción usando el idPista y el nombre
                String descripcion = "ID: " + idPista + " - Nombre: " + nombre;
                // Y lo añadimos al choice
                pistasChoice.add(descripcion);
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

    private void eliminarPista() {
        String pistaSeleccionada = pistasChoice.getSelectedItem();
        String[] partes = pistaSeleccionada.split(" - ");
        String idPista = partes[0].split(": ")[1];

        datos.conectar();
        boolean eliminacionCorrecta = datos.eliminarPistaPorId(idPista);

        if (eliminacionCorrecta) {
            lblMensaje.setText("Pista eliminada correctamente");
            pistasChoice.remove(pistaSeleccionada);
        } else {
            lblMensaje.setText("No se pudo eliminar la pista");
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
