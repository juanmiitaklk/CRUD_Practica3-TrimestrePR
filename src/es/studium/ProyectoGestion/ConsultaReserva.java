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
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ConsultaReserva implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Consulta Reservas");
    TextArea areaTexto = new TextArea(10, 40);
    Button ExportarBtn = new Button("Exportar a Excel");
    
    Datos datos = new Datos();
    //Objeto para exportar los datos a excel
    ExportarExcel excel = new ExportarExcel();
    
    public ConsultaReserva() 
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
                 
         mostrarReservas(); //Metodo para mostrar las reservas
         
         //Correcciones para cerra la ventana correctamente
         ventana.addWindowListener(new WindowAdapter() {
             @Override
             public void windowClosing(WindowEvent e) {
                 ventana.dispose();
             }
         });
    }
    private void mostrarReservas() {
        datos.conectar();
        //Llamamos al  metodo obtenerReservas de datos y lo guardamos todo en el Rs
        ResultSet rs = datos.obtenerReservas();
        try {
        	//Con el while y el next vamos moviendonos por las lineas para recoger los datos
            while (rs.next()) {
            	//Dividimos la información de cada reserva y la guardamos segun su tipo
            	String idReservas = rs.getString("idReservas");
                BigDecimal precioReserva = rs.getBigDecimal("precioReserva");
                Date fechaReserva = rs.getDate("fechaReserva");
                String idSociosFK = rs.getString("idSociosFK");
                String idPistasFK = rs.getString("idPistasFK");

                //Con el append mostramos cada linea de dato de cada reserva en el text area
                areaTexto.append("Id: " + idReservas + "\n");
                areaTexto.append("Precio: " + precioReserva + "\n");
                areaTexto.append("Fecha: " + fechaReserva + "\n");
                areaTexto.append("Socio: " + idSociosFK + "\n" );
                areaTexto.append("Pista: " + idPistasFK + "\n" );

                areaTexto.append("\n");
            }
        } catch (SQLException ex) {
            System.out.println("Error al mostrar Reservas: " + ex.getMessage());
        } finally {
            datos.desconectar();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ExportarBtn) { //Si pulsa el boton de exportar:
            excel.exportarReservasExcel(); //LLamamos al metodo para exportar a un archivo Excel
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

}
