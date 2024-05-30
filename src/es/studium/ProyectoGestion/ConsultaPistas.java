package es.studium.ProyectoGestion;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaPistas implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Consulta Pistas");
    TextArea areaTexto = new TextArea(10, 40);
    Button ExportarBtn = new Button("Exportar a Excel");
    
    Datos datos = new Datos();
    ExportarExcel excel = new ExportarExcel();
    
    public ConsultaPistas() 
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
             
        //Llamamos al metodo para mostrar las pistas
        mostrarPistas(); 
        
        // Correcciones para que no haya problemas al cerrar la ventana
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ventana.dispose();
            }
        });

    }
    // Método para mostrar las pistas en el text area
    private void mostrarPistas() {
        datos.conectar();
        ResultSet rs = datos.mostrarPistas(); // Obtiene las pistas desde la BBDD
        try {
            while (rs.next()) { // Itera sobre cada fila del ResultSet
            	String idPistas = rs.getString("idPistas");
                String nombrePista = rs.getString("nombrePista");
                int zonaPista = rs.getInt("zonaPista");
                String tipoPista = rs.getString("tipoPista");
                
                //Con el append metemos los datos en el text area
                areaTexto.append("Id: " + idPistas + "\n");
                areaTexto.append("Nombre: " + nombrePista + "\n");
                areaTexto.append("Zona: " + zonaPista + "\n");
                areaTexto.append("Tipo: " + tipoPista + "\n" );
                
                areaTexto.append("\n");
            }
        } catch (SQLException ex) {
            System.out.println("Error al mostrar Pistas: " + ex.getMessage());
        } finally {
            datos.desconectar();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ExportarBtn) {// Si se presiona el botón de exportar:
            excel.exportarPistasExcel();//Se exportan todos los datos de todas las pista a un excel
        }
    }
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent e) 
	{
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
