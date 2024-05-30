package es.studium.ProyectoGestion;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModificacionSocio implements WindowListener, ActionListener, ItemListener {
	

	Frame ventana = new Frame("Modificación");

	Label lblDni = new Label("Dni: ");
	TextField dniSocioTXT = new TextField(20);

	Label lblNombre = new Label("Nombre: ");
	TextField nombreSocioTXT = new TextField(20);

	Label lblPrimerApellido = new Label("Primer apellido: ");
	TextField primerApellidoSocioTXT = new TextField(20);

	Label lblSegundoApellido = new Label("Segundo Apellido: ");
	TextField segundoApellidoSocioTXT = new TextField(20);

	Label lblCorreoElectronico = new Label("Correo Electronico: ");
	TextField correoElectronicoSocioTXT = new TextField(20);

	Label contrasenaSociolbl = new Label("Contraseña");
	TextField contrasenaSocioTXT = new TextField(20);

	Choice SocioCh = new Choice();

	Button aceptarbtn = new Button("Aceptar");

	Datos datos = new Datos();
	Dialog dlgMensaje = new Dialog(ventana, "Diálogo con mensaje", true);
	Label lblMensaje = new Label("          ");

	public ModificacionSocio() {
		ventana.setLayout(new GridLayout(0, 2, 10, 10));
		ventana.setSize(400, 300);
		ventana.setResizable(false);
		ventana.setBackground(Color.white);
		ventana.setLocationRelativeTo(null);

		aceptarbtn.addActionListener(this);
		SocioCh.addItemListener(this); 

		ventana.add(lblDni);
		ventana.add(dniSocioTXT);
		ventana.add(lblNombre);
		ventana.add(nombreSocioTXT);
		ventana.add(lblPrimerApellido);
		ventana.add(primerApellidoSocioTXT);
		ventana.add(lblSegundoApellido);
		ventana.add(segundoApellidoSocioTXT);
		ventana.add(lblCorreoElectronico);
		ventana.add(correoElectronicoSocioTXT);
		ventana.add(contrasenaSociolbl);
		ventana.add(contrasenaSocioTXT);
		ventana.add(aceptarbtn);
		ventana.add(SocioCh);

		dlgMensaje.setSize(250, 200);
		dlgMensaje.setResizable(false);
		dlgMensaje.setBackground(Color.white);
		dlgMensaje.setLocationRelativeTo(null);
		dlgMensaje.add(lblMensaje);

		ventana.addWindowListener(this);
		dlgMensaje.addWindowListener(this);

		cargarSocios();

		ventana.setVisible(true);
		
		
		dlgMensaje.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosing(WindowEvent e) {
	            	dlgMensaje.dispose();
	            }
	        });
	}

	

	private void cargarSocios() {
		datos.conectar();
		ResultSet rs = datos.obtenerSocios();
		try {
			while (rs.next()) {
				String socio = rs.getString("dniSocio") + " - " + rs.getString("nombreSocio");
				SocioCh.add(socio);
			}
		} catch (SQLException e) {
			System.out.println("Error al cargar los socios: " + e.getMessage());
		}
		datos.desconectar();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == aceptarbtn) {
			datos.conectar();
			boolean altaCorrecta = datos.modificarSocio(dniSocioTXT.getText(), nombreSocioTXT.getText(),
					primerApellidoSocioTXT.getText(), segundoApellidoSocioTXT.getText(),
					correoElectronicoSocioTXT.getText(), contrasenaSocioTXT.getText());

			if (altaCorrecta) {
				lblMensaje.setText("Socio modificado correctamente");
			} else {
				lblMensaje.setText("Se ha producido un error");
			}
			dlgMensaje.setVisible(true);
			datos.desconectar();
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {}

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
	@Override
	public void windowClosing(WindowEvent e) {
        ventana.dispose();
	}
	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		if (e.getSource() == SocioCh) {
			int indiceSeleccionado = SocioCh.getSelectedIndex();
			if (indiceSeleccionado != -1) {
				datos.conectar();
				ResultSet rs = datos.obtenerSocios();
				try {
					int count = 0;
					while (rs.next()) {
						if (count == indiceSeleccionado) {
							dniSocioTXT.setText(rs.getString("dniSocio"));
							nombreSocioTXT.setText(rs.getString("nombreSocio"));
							primerApellidoSocioTXT.setText(rs.getString("primerApellidoSocio"));
							segundoApellidoSocioTXT.setText(rs.getString("segundoApellidoSocio"));
							correoElectronicoSocioTXT.setText(rs.getString("correoElectronicoSocio"));
							contrasenaSocioTXT.setText(rs.getString("contrasenaSocio"));
							break;
						}
						count++;
					}
				} catch (SQLException ex) {
					System.out.println("Error al cargar los datos del socio: " + ex.getMessage());
				}
				datos.desconectar();
			}
		}
	}
	
	public static void main(String[] args) {
        new ModificacionSocio();
    }
}
