package es.studium.ProyectoGestion;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
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

    BajaPistas() {
        ventana.setLayout(new GridLayout(3, 1, 10, 10));
        ventana.setSize(400, 200);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);

        eliminarbtn.addActionListener(this);

        ventana.add(pistaLabel);
        ventana.add(pistasChoice);
        ventana.add(eliminarbtn);

        //Llamamos al metodo para cargar las pistas desde la BBDD
        cargarPistas();

        mensaje.setLayout(new FlowLayout());
        mensaje.setSize(250, 100);
        mensaje.setResizable(false);
        mensaje.setLocationRelativeTo(null);
        mensaje.add(lblMensaje);

        // Correcciones para que no haya problemas al cerrar el diálogo
        mensaje.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mensaje.dispose();
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
        ResultSet pistas = datos.obtenerPistas(); //Obtiene las pistas desde la BBDD
        try {
        	//Iteramos sobre cada fila del Rs
            while (pistas.next()) { //Se mueve sobre las filas del Rs y devuelve un booleano
                String idPista = pistas.getString("idPistas");
                String nombre = pistas.getString("nombrePista");
                //Creamos una descripción usando el idPista y el nombre
                String descripcion = "ID: " + idPista + " - Nombre: " + nombre;
                //Y lo añadimos al choice
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
        	//Obtenemos el item seleccionado del choice y lo almacena en pistaSeleccionada
            String pistaSeleccionada = pistasChoice.getSelectedItem();
            //Usamos el split como delimitador y dividimos la cadena en partes
            String[] partes = pistaSeleccionada.split(" - ");
            // Extraemos el idPista de la primera parte de la cadena
            String idPista = partes[0].split(": ")[1];

            datos.conectar();
            // Llama al método eliminarPistaPorId de datos con el idPista y guarda el resultado en eliminacionCorrecta
            boolean eliminacionCorrecta = datos.eliminarPistaPorId(idPista);

            if (eliminacionCorrecta) {
                lblMensaje.setText("Pista eliminada correctamente");
                pistasChoice.remove(pistaSeleccionada); // Eliminamos la pista que hemos seleccionado del choice
            } else {
                lblMensaje.setText("No se pudo eliminar la pista");
            }
            mensaje.setVisible(true);
            datos.desconectar();
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
        new BajaPistas();
    }
}
