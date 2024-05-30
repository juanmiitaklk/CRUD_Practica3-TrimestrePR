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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModificacionPistas implements ActionListener, ItemListener {

    Frame ventana = new Frame("Modificación de Pistas");

    Label nombrePistalbl = new Label("Nombre");
    TextField nombrePistaTXT = new TextField();
    Label zonaPistalbl = new Label("Zona");
    TextField zonaPistaTXT = new TextField();
    Label tipoPistalbl = new Label("Tipo");
    TextField tipoPistaTXT = new TextField();

    Choice pistasChoice = new Choice();
    Button modificarbtn = new Button("Modificar");

    Datos datos = new Datos();
    Dialog mensaje = new Dialog(ventana, "Mensaje", true);
    Label lblMensaje = new Label("");

    ModificacionPistas() {
        ventana.setLayout(new GridLayout(5, 2, 10, 10));
        ventana.setSize(400, 300);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);

        modificarbtn.addActionListener(this);
        pistasChoice.addItemListener(this);

        ventana.add(new Label("Selecciona una pista:"));
        ventana.add(pistasChoice);
        ventana.add(nombrePistalbl);
        ventana.add(nombrePistaTXT);
        ventana.add(zonaPistalbl);
        ventana.add(zonaPistaTXT);
        ventana.add(tipoPistalbl);
        ventana.add(tipoPistaTXT);

        ventana.add(modificarbtn);

        //Llamamos al metodo para cargar las pistas en la BBDD
        cargarPistas();

        mensaje.setLayout(new FlowLayout());
        mensaje.setSize(250, 100);
        mensaje.setResizable(false);
        mensaje.setLocationRelativeTo(null);
        mensaje.add(lblMensaje);

        //Correciones para que no haya problemas a la hora de cerrar el dialog
        mensaje.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mensaje.dispose();
            }
        });
        
        //Correciones para que no haya problemas a la hora de cerrar la ventana
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
        ResultSet pistas = datos.obtenerPistas(); //Obtenemos las pistas desde la BBDD
        try {
            while (pistas.next()) {//Iteramos sobre cada fila del Rs
                String idPista = pistas.getString("idPistas");
                String nombre = pistas.getString("nombrePista");
                //Creamos una descripcion usando el idPista y nombre
                String descripcion = "ID: " + idPista + " - Nombre: " + nombre;
                pistasChoice.add(descripcion); // Y lo añadimos al choice
            }
        } catch (SQLException e) {
            System.out.println("Error al procesar el ResultSet: " + e.toString());
        }
        datos.desconectar();
    }
//Metodo para manejar los eventos de cambio del choice
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == pistasChoice) { // Si cambiamos la seleccion en el choice
        	//Obtiene el item seleccionado del choice y los almacena en pistaSeleccionada
            String pistaSeleccionada = pistasChoice.getSelectedItem();
            //Dividimos la cadena en partes usando el split como delimitador
            String[] partes = pistaSeleccionada.split(" - ");
            //Extraemos el idPista de la primera parte de la cadena
            String idPista = partes[0].split(": ")[1];

            datos.conectar();
            ResultSet pista = datos.obtenerPistaPorId(idPista); //Obtenemos la pista con el idPista desde la BBDD
            try {
                if (pista.next()) { //Si hay una fila en el Rs:
                	// Establece el campo de texto nombrePistaTXT con el valor de nombrePista
                    nombrePistaTXT.setText(pista.getString("nombrePista"));
                 // Establece el campo de texto zonaPistaTXT con el valor de zonaPista
                    zonaPistaTXT.setText(pista.getString("zonaPista"));
                 // Establece el campo de texto tipoPistaTXT con el valor de tipoPista
                    tipoPistaTXT.setText(pista.getString("tipoPista"));
                }
            } catch (SQLException e1) {
                System.out.println("Error al procesar el ResultSet: " + e1.toString());
            }
            datos.desconectar();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == modificarbtn) { //Si presionamos el boton de Modificar:
            String pistaSeleccionada = pistasChoice.getSelectedItem(); //Seleccionamos el item del choice y lo almacenamos en pistaSeleccionada
            //Dividimos en partes la cadena con el split
            String[] partes = pistaSeleccionada.split(" - ");
            //Cogemos el idPista de la primera parte de la cadena
            String idPista = partes[0].split(": ")[1];

            datos.conectar();
         // Llama al método modificarPista de datos con los valores de los campos de texto y el idPista
            boolean modificacionCorrecta = datos.modificarPista(idPista, nombrePistaTXT.getText(), zonaPistaTXT.getText(), tipoPistaTXT.getText());

            if (modificacionCorrecta) { 
                lblMensaje.setText("Pista modificada correctamente");
            } else {
                lblMensaje.setText("No se pudo modificar la pista");
            }
            mensaje.setVisible(true);
            datos.desconectar();
        }
    }

    public static void main(String[] args) {
        new ModificacionPistas();
    }
}
