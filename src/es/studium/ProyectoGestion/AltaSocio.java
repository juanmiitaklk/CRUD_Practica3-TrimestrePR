package es.studium.ProyectoGestion;

import java.awt.*;
import java.awt.event.*;

public class AltaSocio implements WindowListener, ActionListener {

    Frame ventana = new Frame("Alta Socio");
    Label dniSociolbl = new Label("DNI");
    TextField dniSocioTXT = new TextField("12345678T");
    Label nombreSociolbl = new Label("Nombre");
    TextField nombreSocioTXT = new TextField("Juan");
    Label primerApellidoSociolbl = new Label("Primer Apellido");
    TextField primerApellidoSocioTXT = new TextField("Rodriguez");
    Label segundoApellidoSociolbl = new Label("Segundo Apellido");
    TextField segundoApellidoSocioTXT = new TextField("Fuentes");
    Label correoElectronicoSociolbl = new Label("Correo Electronico");
    TextField correoElectronicoSocioTXT = new TextField("Socios@socios.com");
    Label contrasenaSociolbl = new Label("Contraseña");
    TextField contrasenaSocioTXT = new TextField("Socios123");
    
    Button aceptarbtn = new Button("Aceptar");
    Button limpiarbtn = new Button("Limpiar");
    
    Datos datos = new Datos();
    Dialog Mensaje = new Dialog(ventana, "Mensaje", true);
    Label lblMensaje = new Label("Socio creado correctamente");


    AltaSocio() {
        ventana.setLayout(new GridLayout(0,2,10,10));
        ventana.setSize(400, 300);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        
        aceptarbtn.addActionListener(this);
        limpiarbtn.addActionListener(this);
        
        ventana.addWindowListener(this);

        ventana.add(dniSociolbl);
        ventana.add(dniSocioTXT);
        ventana.add(nombreSociolbl);
        ventana.add(nombreSocioTXT);
        ventana.add(primerApellidoSociolbl);
        ventana.add(primerApellidoSocioTXT);
        ventana.add(segundoApellidoSociolbl);
        ventana.add(segundoApellidoSocioTXT);
        ventana.add(correoElectronicoSociolbl);
        ventana.add(correoElectronicoSocioTXT);
        ventana.add(contrasenaSociolbl);
        ventana.add(contrasenaSocioTXT);
        ventana.add(aceptarbtn);
        ventana.add(limpiarbtn);

        
        
        Mensaje.setLayout(new FlowLayout());
        Mensaje.setSize(250, 110);
        Mensaje.setResizable(false);
        Mensaje.setLocationRelativeTo(null);
        Mensaje.add(lblMensaje);
        
        
        ventana.setVisible(true);
        
        Mensaje.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Mensaje.dispose();
            }
        });
        
        
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ventana.dispose();
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == aceptarbtn) 
        {
            datos.conectar();
            
            boolean altaCorrecta = datos.AltaSocio(dniSocioTXT.getText(), nombreSocioTXT.getText(),
                    primerApellidoSocioTXT.getText(), segundoApellidoSocioTXT.getText(),
                    correoElectronicoSocioTXT.getText(), contrasenaSocioTXT.getText());
            
            if (altaCorrecta) 
            {
                lblMensaje.setText("Socio creado correctamente");
            }
            
            else 
            {
                lblMensaje.setText("Se ha producido un error");
            }
            Mensaje.setVisible(true);
            datos.desconectar();
        } 
        else if (e.getSource() == limpiarbtn) 
        {
            dniSocioTXT.setText("");
            nombreSocioTXT.setText("");
            primerApellidoSocioTXT.setText("");
            segundoApellidoSocioTXT.setText("");
            correoElectronicoSocioTXT.setText("");
            contrasenaSocioTXT.setText("");
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
}
