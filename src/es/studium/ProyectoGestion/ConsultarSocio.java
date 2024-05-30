package es.studium.ProyectoGestion;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultarSocio implements WindowListener, ActionListener 
{
	
    Frame ventana = new Frame("Consulta de Socios");
    TextArea areaTexto = new TextArea(10, 40);
    Button ExportarBtn = new Button("Exportar a Excel");


    Datos datos = new Datos();
    ExportarExcel excel = new ExportarExcel();
    
    public ConsultarSocio() 
    {
        ventana.setLayout(new BorderLayout());
        ventana.addWindowListener(this);
        
        ventana.add(areaTexto, BorderLayout.CENTER);
        ventana.add(ExportarBtn, BorderLayout.SOUTH);

        ExportarBtn.addActionListener(this);


        ventana.setSize(450, 300);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
                
        mostrarSocios(); 
        
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ventana.dispose();
            }
        });

    }

    private void mostrarSocios() {
        datos.conectar();
        ResultSet rs = datos.obtenerSocios();
        try {
            while (rs.next()) {
            	String idSocios = rs.getString("idSocios");
                String dni = rs.getString("dniSocio");
                String nombre = rs.getString("nombreSocio");
                String primerApellido = rs.getString("primerApellidoSocio");
                String segundoApellido = rs.getString("segundoApellidoSocio");
                String correoElectronico = rs.getString("correoElectronicoSocio");
                areaTexto.append("Id: " + idSocios + "\n");
                areaTexto.append("DNI: " + dni + "\n");
                areaTexto.append("Nombre: " + nombre + "\n");
                areaTexto.append("Primer Apellido: " + primerApellido + "\n" );
                areaTexto.append("Segundo Apellido: " + segundoApellido + "\n");
                areaTexto.append("Correo Electrónico: " + correoElectronico + "\n");
                areaTexto.append("\n");
            }
        } catch (SQLException ex) {
            System.out.println("Error al mostrar socios: " + ex.getMessage());
        } finally {
            datos.desconectar();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ExportarBtn) {
            excel.exportarSociosExcel();
        }
    }
    

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) 
    {
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
