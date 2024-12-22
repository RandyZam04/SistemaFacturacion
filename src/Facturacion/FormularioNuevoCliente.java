package Facturacion;

import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class FormularioNuevoCliente extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtCedula, txtNombres, txtApellidos, txtDireccion, txtTelefono, txtEmail;
    private TablaDeClientes tablaDeClientes;
    private int rowIndex; 

    /**
     * @wbp.parser.constructor
     */
    public FormularioNuevoCliente(TablaDeClientes tablaDeClientes) {
        this.tablaDeClientes = tablaDeClientes;
        this.rowIndex = -1; 

        configurarInterfaz("Guardar Cliente", e -> guardarCliente());
    }

    public FormularioNuevoCliente(TablaDeClientes tablaDeClientes, String cedula, String nombres, String apellidos, String direccion, String telefono, String email, int rowIndex) {
        this.tablaDeClientes = tablaDeClientes;
        this.rowIndex = rowIndex;

        configurarInterfaz("Actualizar Cliente", e -> actualizarCliente());

        txtCedula.setText(cedula);
        txtNombres.setText(nombres);
        txtApellidos.setText(apellidos);
        txtDireccion.setText(direccion);
        txtTelefono.setText(telefono);
        txtEmail.setText(email);
    }

    private void configurarInterfaz(String textoBoton, ActionListener accionBoton) {
        setBounds(100, 100, 450, 342);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblCedula = new JLabel("Cédula");
        lblCedula.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCedula.setBounds(10, 14, 70, 28);
        contentPane.add(lblCedula);

        txtCedula = new JTextField();
        txtCedula.setBounds(88, 14, 325, 28);
        contentPane.add(txtCedula);

        JLabel lblNombres = new JLabel("Nombres");
        lblNombres.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNombres.setBounds(10, 52, 70, 28);
        contentPane.add(lblNombres);

        txtNombres = new JTextField();
        txtNombres.setBounds(88, 52, 325, 28);
        contentPane.add(txtNombres);

        JLabel lblApellidos = new JLabel("Apellido");
        lblApellidos.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblApellidos.setBounds(10, 90, 70, 28);
        contentPane.add(lblApellidos);

        txtApellidos = new JTextField();
        txtApellidos.setBounds(88, 90, 325, 28);
        contentPane.add(txtApellidos);

        JLabel lblDireccion = new JLabel("Dirección");
        lblDireccion.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblDireccion.setBounds(10, 130, 70, 28);
        contentPane.add(lblDireccion);

        txtDireccion = new JTextField();
        txtDireccion.setBounds(88, 130, 325, 28);
        contentPane.add(txtDireccion);

        JLabel lblTelefono = new JLabel("Teléfono");
        lblTelefono.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblTelefono.setBounds(10, 168, 70, 28);
        contentPane.add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(88, 168, 325, 28);
        contentPane.add(txtTelefono);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblEmail.setBounds(10, 206, 70, 28);
        contentPane.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(88, 206, 325, 28);
        contentPane.add(txtEmail);

        JButton botonPrincipal = new JButton(textoBoton);
        botonPrincipal.setFont(new Font("Tahoma", Font.PLAIN, 20));
        botonPrincipal.setBounds(88, 257, 325, 45);
        botonPrincipal.addActionListener(accionBoton);
        contentPane.add(botonPrincipal);

        JButton botonCerrar = new JButton("x");
        botonCerrar.setBounds(10, 257, 51, 45);
        botonCerrar.addActionListener(e -> dispose());
        contentPane.add(botonCerrar);
    }

    private void guardarCliente() {
        String cedula = txtCedula.getText();
        String nombres = txtNombres.getText();
        String apellidos = txtApellidos.getText();
        String direccion = txtDireccion.getText();
        String telefono = txtTelefono.getText();
        String email = txtEmail.getText();

        Cliente cliente = new Cliente(cedula, nombres, apellidos, direccion, telefono, email);
        tablaDeClientes.agregarClienteATabla(cliente);
        dispose();
    }

    private void actualizarCliente() {
        String cedula = txtCedula.getText();
        String nombres = txtNombres.getText();
        String apellidos = txtApellidos.getText();
        String direccion = txtDireccion.getText();
        String telefono = txtTelefono.getText();
        String email = txtEmail.getText();

        Cliente cliente = new Cliente(cedula, nombres, apellidos, direccion, telefono, email);
        tablaDeClientes.actualizarClienteEnTabla(cliente, rowIndex);
        dispose();
    }
}