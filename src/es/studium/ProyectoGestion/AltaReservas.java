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

public class AltaReservas implements WindowListener, ActionListener {
    Frame ventana = new Frame("Alta Pista");

    Label FechaReservalbl = new Label("Fecha");
    TextField fechaReservaTXT = new TextField("2024-10-15", 20);
    Label PrecioReservalbl = new Label("Precio");
    TextField PrecioReservaTXT = new TextField("100", 20);

    Choice FKSocioCh = new Choice();
    Choice FKPistaCh = new Choice();

    Button aceptarbtn = new Button("Aceptar");
    Button limpiarbtn = new Button("Limpiar");

    Datos datos = new Datos();
    Dialog Mensaje = new Dialog(ventana, "Mensaje", true);
    Label lblMensaje = new Label("Reserva creada correctamente");

    AltaReservas() {
        ventana.setLayout(new GridLayout(0, 2, 10, 10));
        ventana.setSize(400, 300);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);

        aceptarbtn.addActionListener(this);
        limpiarbtn.addActionListener(this);

        ventana.addWindowListener(this);

        ventana.add(FechaReservalbl);
        ventana.add(fechaReservaTXT);
        ventana.add(PrecioReservalbl);
        ventana.add(PrecioReservaTXT);
        ventana.add(FKSocioCh);
        ventana.add(FKPistaCh);

        ventana.add(aceptarbtn);
        ventana.add(limpiarbtn);

        Mensaje.setLayout(new FlowLayout());
        Mensaje.setSize(250, 110);
        Mensaje.setResizable(false);
        Mensaje.setLocationRelativeTo(null);
        Mensaje.add(lblMensaje);
        
        cargarSocios();
        cargarPistas();

        ventana.setVisible(true);

        Mensaje.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Mensaje.dispose();
            }
        });
    }

    private void cargarSocios() {
        datos.conectar();
        ResultSet rs = datos.obtenerSocios();
        try {
            while (rs.next()) {
                String socio = rs.getString("dniSocio") + " - " + rs.getString("nombreSocio");
                FKSocioCh.add(socio);
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar los socios: " + e.getMessage());
        }
        datos.desconectar();
    }

    private void cargarPistas() {
        datos.conectar();
        ResultSet rs = datos.obtenerPistas();
        try {
            while (rs.next()) {
                String pista = rs.getString("nombrePista") + " - " + rs.getString("tipoPista");
                FKPistaCh.add(pista);
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar las pistas: " + e.getMessage());
        }
        datos.desconectar();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == aceptarbtn) {
            datos.conectar();
            try {
                boolean altaCorrecta = datos.altaReservas(FKSocioCh.getSelectedItem(), fechaReservaTXT.getText(),
                                                          PrecioReservaTXT.getText(), FKPistaCh.getSelectedItem());

                if (altaCorrecta) {
                    lblMensaje.setText("Reserva creada correctamente");
                } else {
                    lblMensaje.setText("Se ha producido un error");
                }
                Mensaje.setVisible(true);
            } catch (SQLException ex) {
                lblMensaje.setText("Error SQL: " + ex.getMessage());
                Mensaje.setVisible(true);
            } finally {
                datos.desconectar();
            }
        } else if (e.getSource() == limpiarbtn) {
            fechaReservaTXT.setText("");
            PrecioReservaTXT.setText("");
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        ventana.dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}

    public static void main(String[] args) {
        new AltaReservas();
    }
}
