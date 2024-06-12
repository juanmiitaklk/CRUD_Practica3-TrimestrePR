package es.studium.ProyectoGestion;

import java.awt.*;
import java.awt.event.*;

public class MenuPrincipal implements WindowListener, ActionListener {

    Frame ventana = new Frame("Principal");
    MenuBar barraMenu = new MenuBar();
    Menu mnuSocios = new Menu("Socios");
    Menu mnuReservas = new Menu("Reservas");
    Menu mnuPistas = new Menu("Pistas");
    Menu mnuAyuda = new Menu("Ayuda");

    MenuItem mniConsultaReservas = new MenuItem("Consultar Reservas");
    MenuItem mniAltaReservas = new MenuItem("Crear una Reserva");
    MenuItem mniBajaReservas = new MenuItem("Dar de baja una Reserva");
    MenuItem mniModificacionReservas = new MenuItem("Modificar Reserva");

    MenuItem mniModificacionPistas = new MenuItem("Modificar Pista");
    MenuItem mniConsultaPistas = new MenuItem("Consultar Pistas");
    MenuItem mniBajaPistas = new MenuItem("Dar de baja una Pista");
    MenuItem mniAltaPistas = new MenuItem("Crear una Pista");

    MenuItem mniSocioNuevo = new MenuItem("Nuevo Socio");
    MenuItem mniSocioEliminar = new MenuItem("Eliminar Socio");
    MenuItem mniSocioConsultar = new MenuItem("Consultar Socio");
    MenuItem mniSocioModificar = new MenuItem("Modificar Socio");

    MenuItem mniAyuda = new MenuItem("Ayuda");

    Dialog dlgMensaje = new Dialog(ventana, "Diálogo con mensaje", true);
    Label lblMensaje = new Label("          ");

    Datos datos = new Datos();
    GuardarLog guardarLog = new GuardarLog();
    int tipoUsuario;

    public MenuPrincipal(int t, String contraseñaa) {
        this.tipoUsuario = t;

        ventana.setLayout(new FlowLayout());
        dlgMensaje.setLayout(new FlowLayout());
        ventana.addWindowListener(this);
        dlgMensaje.addWindowListener(this);

        MenuTipoU(t);

        ventana.setMenuBar(barraMenu);
        ventana.setSize(650, 260);
        ventana.setResizable(false);
        ventana.setBackground(Color.gray);
        ventana.setLocationRelativeTo(null);
        dlgMensaje.setSize(200, 150);
        dlgMensaje.setResizable(false);
        dlgMensaje.setBackground(Color.gray);
        dlgMensaje.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    private void MenuTipoU(int t) {
        mniAltaReservas.addActionListener(this);
        mniConsultaReservas.addActionListener(this);
        mniBajaReservas.addActionListener(this);
        mniModificacionReservas.addActionListener(this);

        mniModificacionPistas.addActionListener(this);
        mniConsultaPistas.addActionListener(this);
        mniBajaPistas.addActionListener(this);
        mniAltaPistas.addActionListener(this);

        mniSocioNuevo.addActionListener(this);
        mniSocioEliminar.addActionListener(this);
        mniSocioConsultar.addActionListener(this);
        mniSocioModificar.addActionListener(this);

        mniAyuda.addActionListener(this);

        mnuReservas.add(mniAltaReservas);
        mnuPistas.add(mniAltaPistas);

        if (t == 0) { // juanmaRoot Studium2023;
            mnuReservas.add(mniConsultaReservas);
            mnuReservas.add(mniBajaReservas);
            mnuReservas.add(mniModificacionReservas);

            mnuPistas.add(mniConsultaPistas);
            mnuPistas.add(mniBajaPistas);
            mnuPistas.add(mniModificacionPistas);

            mnuSocios.add(mniSocioModificar);
            mnuSocios.add(mniSocioConsultar);
            mnuSocios.add(mniSocioNuevo);
            mnuSocios.add(mniSocioEliminar);

            mnuAyuda.add(mniAyuda);

            barraMenu.add(mnuSocios);
            barraMenu.add(mnuReservas);
            barraMenu.add(mnuPistas);
            barraMenu.add(mnuAyuda);

        } else if (t == 1) { // juanma2 Studium2023;2 
            mnuReservas.add(mniConsultaReservas);
            mnuReservas.add(mniBajaReservas);
            mnuReservas.add(mniModificacionReservas);

            mnuSocios.add(mniSocioNuevo);

            mnuAyuda.add(mniAyuda);

            barraMenu.add(mnuSocios);
            barraMenu.add(mnuReservas);
            barraMenu.add(mnuAyuda);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Socios
        if (e.getSource().equals(mniSocioConsultar)) {
            new ConsultarSocio();
            guardarLog.guardarLog(tipoUsuario, "Consultar Socio");
        } else if (e.getSource().equals(mniSocioNuevo)) {
            new AltaSocio();
            guardarLog.guardarLog(tipoUsuario, "Nuevo Socio");
        } else if (e.getSource().equals(mniSocioEliminar)) {
            new BajaSocio();
            guardarLog.guardarLog(tipoUsuario, "Eliminar Socio");
        } else if (e.getSource().equals(mniSocioModificar)) {
            new ModificacionSocio();
            guardarLog.guardarLog(tipoUsuario, "Modificar Socio");
        }
        // Reservas
        else if (e.getSource().equals(mniModificacionReservas)) {
            new ModificacionReserva();
            guardarLog.guardarLog(tipoUsuario, "Modificar Reserva");
        } else if (e.getSource().equals(mniAltaReservas)) {
            new AltaReservas();
            guardarLog.guardarLog(tipoUsuario, "Crear una Reserva");
        } else if (e.getSource().equals(mniBajaReservas)) {
            new BajaReservas();
            guardarLog.guardarLog(tipoUsuario, "Dar de baja una Reserva");
        } else if (e.getSource().equals(mniConsultaReservas)) {
            new ConsultaReserva();
            guardarLog.guardarLog(tipoUsuario, "Consultar Reservas");
        }
        // Pistas
        else if (e.getSource().equals(mniModificacionPistas)) {
            new ModificacionPistas();
            guardarLog.guardarLog(tipoUsuario, "Modificar Pista");
        } else if (e.getSource().equals(mniAltaPistas)) {
            new AltaPistas();
            guardarLog.guardarLog(tipoUsuario, "Crear una Pista");
        } else if (e.getSource().equals(mniBajaPistas)) {
            new BajaPistas();
            guardarLog.guardarLog(tipoUsuario, "Dar de baja una Pista");
        } else if (e.getSource().equals(mniConsultaPistas)) {
            new ConsultaPistas();
            guardarLog.guardarLog(tipoUsuario, "Consultar Pistas");
        }
        // Ayuda
        else if (e.getSource().equals(mniAyuda)) {
            new Ayuda();
            guardarLog.guardarLog(tipoUsuario, "Ayuda");
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        if (dlgMensaje.isActive()) {
            dlgMensaje.setVisible(false);
        } else {
            System.exit(0);
        }
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
        
    }
}
