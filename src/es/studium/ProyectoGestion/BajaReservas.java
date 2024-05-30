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

public class BajaReservas implements ActionListener, WindowListener {

    Frame ventana = new Frame("Baja Reservas");
    Choice reservasChoice = new Choice();
    Button eliminarbtn = new Button("Eliminar");
    Label Tlbl = new Label("Selecciona uno para eliminar");

    Datos datos = new Datos();
    Dialog mensaje = new Dialog(ventana, "Mensaje", true);
    Label lblMensaje = new Label("");

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

        // Correciones para que el dialog se cierre correctamente
        mensaje.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mensaje.dispose();
            }
        });
        
        // Correciones para que la ventana se cierre correctamente
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ventana.dispose();
            }
        });

        ventana.setVisible(true);
    }

    private void cargarReservas() {
        datos.conectar();
        //Llamamos a obtenerReservas de datos, que devuelve un Rs con las reservas de la BBDD
        ResultSet reservas = datos.obtenerReservas(); 
        try {
        	// Itera sobre el Rs de reservas
            while (reservas.next()) { //Se mueve a la siguiente fila, si hay otra devuelve true si no false
                String precio = reservas.getString("precioReserva");//Coge el valor del Rs
                String fecha = reservas.getString("fechaReserva");//Coge el valor del Rs
                String idSocio = reservas.getString("idSociosFK");//Coge el valor del Rs
                //Creamos una descripcion usando los valores
                String descripcion = "Precio: " + precio + ", Fecha: " + fecha + ", ID Socio: " + idSocio;
                //Metemos los valores de la descripcion en el choice
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
        	//Obtiene el item que hemos seleccionado y lo almacenamos en la variable reservaSeleccionada
            String reservaSeleccionada = reservasChoice.getSelectedItem();
            //Con el split dividimos la cadena en partes y ponemos comas
            String[] partes = reservaSeleccionada.split(", ");
            // Extraemos los valores de precio, fecha y idSocio
            String precio = partes[0].split(": ")[1];
            String fecha = partes[1].split(": ")[1];
            String idSocio = partes[2].split(": ")[1];

            datos.conectar();
            //Llamamos al metodo eliminarReserva de datos y le pasamos los valores obtenidos almacenamos los resultados en un booleano
            boolean eliminacionCorrecta = datos.eliminarReserva(precio, fecha, idSocio);

            if (eliminacionCorrecta) {
            	//Si eel booleano de eliminacionCorrecta es true muestra el dialog de que se ha eliminado
                lblMensaje.setText("Reserva eliminada correctamente");
                reservasChoice.remove(reservaSeleccionada); // Y lo elimina 
            } else {
                lblMensaje.setText("No se pudo eliminar la Reserva");
            }
            mensaje.setVisible(true);
            datos.desconectar();
        }
    }

    public static void main(String[] args) {
        new BajaReservas();
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
