package es.studium.ProyectoGestion;

import java.awt.*;
import java.awt.event.*;

public class Login implements WindowListener, ActionListener {

    Frame ventana = new Frame("Inicio de Sesión");

    Label lblUsuario = new Label("Usuario:");
    Label lblContraseña = new Label("Contraseña:");
    Label lblError = new Label("Credenciales Incorrectas");
    TextField txtUsuario = new TextField(20);
    TextField txtContraseña = new TextField(20);
    Button btnAceptar = new Button("Aceptar");
    Button btnLimpiar = new Button("Limpiar");
    Dialog dlgError = new Dialog(ventana, "Error", true);

    Login() {
        ventana.setLayout(new FlowLayout());
        ventana.setSize(220, 190);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        ventana.add(lblUsuario);
        ventana.add(txtUsuario);
        ventana.add(lblContraseña);
        txtContraseña.setEchoChar('*');
        ventana.add(txtContraseña);
        btnAceptar.addActionListener(this);
        ventana.add(btnAceptar);
        btnLimpiar.addActionListener(this);
        ventana.add(btnLimpiar);
        ventana.addWindowListener(this);
        
        txtUsuario.setText("JuanmaRoot");
        txtContraseña.setText("Studium2023;");

        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        new Login();
    }

    public void windowActivated(WindowEvent windowEvent) {}

    public void windowClosed(WindowEvent windowEvent) {}

    public void windowClosing(WindowEvent windowEvent) {
        if (dlgError.isActive()) {
            dlgError.setVisible(false);
        } else {
            System.exit(0);
        }
    }

    public void windowDeactivated(WindowEvent windowEvent) {}

    public void windowDeiconified(WindowEvent windowEvent) {}

    public void windowIconified(WindowEvent windowEvent) {}

    public void windowOpened(WindowEvent windowEvent) {}

    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnLimpiar)) {
            txtUsuario.setText("");
            txtContraseña.setText("");
            txtUsuario.requestFocus();
        } else if (actionEvent.getSource().equals(btnAceptar)) {
            Datos datos = new Datos();
            if (datos.conectar()) {
                String usuario = txtUsuario.getText();
                String contraseña = txtContraseña.getText();
                if (datos.comprobarCredenciales(usuario, contraseña, 0)) {
                    int tipo = datos.dameTipo(usuario);
                    new MenuPrincipal(tipo, contraseña);
                    ventana.setVisible(false);
                } else {
                    dlgError.setLayout(new FlowLayout());
                    dlgError.setSize(220, 190);
                    dlgError.setResizable(false);
                    dlgError.setLocationRelativeTo(null);
                    dlgError.add(lblError);
                    dlgError.addWindowListener(this);
                    dlgError.setVisible(true);
                }
            } else {
                System.out.println("Conexión rechazada");
            }
        }
    }
}
