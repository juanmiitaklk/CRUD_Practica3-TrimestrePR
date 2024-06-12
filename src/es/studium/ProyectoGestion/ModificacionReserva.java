package es.studium.ProyectoGestion;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModificacionReserva implements WindowListener, ActionListener, ItemListener {
    Frame ventana = new Frame("Modificación");

    Label dniReservalbl = new Label("DNI Socio");
    TextField dniReservaTXT = new TextField("1", 20);

    Label FechaReservalbl = new Label("Fecha");
    TextField fechaReservaTXT = new TextField("21-06-2024", 20);

    Label PrecioReservalbl = new Label("Precio");
    TextField PrecioReservaTXT = new TextField("100€", 20);

    Label PistaReservalbl = new Label("Pista");
    TextField PistaReservaTXT = new TextField("2");

    Choice ReservaCh = new Choice();

    Button aceptarbtn = new Button("Aceptar");

    Datos datos = new Datos();
    Dialog dlgMensaje = new Dialog(ventana, "Diálogo con mensaje", true);
    Label lblMensaje = new Label("          ");

    ModificacionReserva() {
        ventana.setLayout(new GridLayout(0, 2, 10, 10));
        ventana.setSize(400, 300);
        ventana.setResizable(false);
        ventana.setBackground(Color.white);
        ventana.setLocationRelativeTo(null);

        aceptarbtn.addActionListener(this);
        ReservaCh.addItemListener(this);

        ventana.add(dniReservalbl);
        ventana.add(dniReservaTXT);
        ventana.add(FechaReservalbl);
        ventana.add(fechaReservaTXT);
        ventana.add(PrecioReservalbl);
        ventana.add(PrecioReservaTXT);
        ventana.add(PistaReservalbl);
        ventana.add(PistaReservaTXT);

        ventana.add(ReservaCh);

        ventana.add(aceptarbtn);

        dlgMensaje.setSize(250, 200);
        dlgMensaje.setResizable(false);
        dlgMensaje.setBackground(Color.white);
        dlgMensaje.setLocationRelativeTo(null);
        dlgMensaje.add(lblMensaje);

        ventana.addWindowListener(this);
        dlgMensaje.addWindowListener(this);

        // Método para cargar las reservas en el choice
        cargarReservas();

        ventana.setVisible(true);

        // Correcciones para cerrar la ventana correctamente
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dlgMensaje.dispose();
            }
        });

        // Correcciones para cerrar el dialog correctamente
        dlgMensaje.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dlgMensaje.dispose();
            }
        });
    }

    // Creación del método para cargar las reservas en el Choice
    private void cargarReservas() {
        datos.conectar();
        ResultSet rs = datos.obtenerReservas(); // Obtenemos los datos de la BBDD
        try {
            while (rs.next()) {
                // Añade cada reserva al Choice
                String reserva = rs.getString("dniSocio") + " - " + rs.getString("fechaReserva");
                ReservaCh.add(reserva);
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar las reservas: " + e.getMessage());
        }
        datos.desconectar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == aceptarbtn) { // si se pulsa el botón de aceptar
            datos.conectar(); // conecta a la BBDD
            // Modifica la reserva con los datos ingresados en los campos de texto:
            boolean modificacionCorrecta = datos.modificarReservas(
                    dniReservaTXT.getText(),
                    fechaReservaTXT.getText(),
                    PrecioReservaTXT.getText(),
                    PistaReservaTXT.getText()
            );

            if (modificacionCorrecta) {
                lblMensaje.setText("Reserva modificada correctamente");
            } else {
                lblMensaje.setText("Se ha producido un error");
            }
            dlgMensaje.setVisible(true);
            datos.desconectar();
        }
    }

    // Utilizamos el ItemStateChanged para manejar los eventos de cambio del choice
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == ReservaCh) { // Si se cambia la selección en el Choice
            int indiceSeleccionado = ReservaCh.getSelectedIndex(); // Pillamos el índice seleccionado
            if (indiceSeleccionado != -1) {
                datos.conectar(); // Nos conectamos a la BBDD
                ResultSet rs = datos.obtenerReservas(); // Y obtenemos las reservas desde la BBDD
                try {
                    int count = 0; // Utilizamos esta variable para contar las filas procesadas
                    while (rs.next()) { // Avanza a la siguiente fila y devuelve true o false
                        if (count == indiceSeleccionado) { // Comprobamos si el count es igual al índice seleccionado del choice
                            // Si es igual significa que hemos encontrado la reserva seleccionada en el choice
                            // Establece los campos de texto con los datos de la reserva seleccionada
                            dniReservaTXT.setText(rs.getString("dniSocio"));
                            fechaReservaTXT.setText(rs.getString("fechaReserva"));
                            PrecioReservaTXT.setText(rs.getString("precioReserva"));
                            PistaReservaTXT.setText(rs.getString("idPistasFK"));
                            break;
                        }
                        // Incrementamos el contador en +1 para pasar en la siguiente fila en el Rs
                        count++;
                    }
                } catch (SQLException ex) {
                    System.out.println("Error al cargar los datos de la reserva: " + ex.getMessage());
                }
                datos.desconectar();
            }
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
        new ModificacionReserva();
    }
}
