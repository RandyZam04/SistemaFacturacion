package Facturacion;

import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
        agregarValidacionCedula();
        contentPane.add(txtCedula);

        JLabel lblNombres = new JLabel("Nombres");
        lblNombres.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNombres.setBounds(10, 52, 70, 28);
        contentPane.add(lblNombres);

        txtNombres = new JTextField();
        txtNombres.setBounds(88, 52, 325, 28);
        agregarValidacionTexto(txtNombres);
        contentPane.add(txtNombres);

        JLabel lblApellidos = new JLabel("Apellido");
        lblApellidos.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblApellidos.setBounds(10, 90, 70, 28);
        contentPane.add(lblApellidos);

        txtApellidos = new JTextField();
        txtApellidos.setBounds(88, 90, 325, 28);
        agregarValidacionTexto(txtApellidos);
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
        agregarValidacionTelefono();
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

    private void agregarValidacionCedula() {
        txtCedula.getDocument().addDocumentListener(new DocumentListener() {
            private void verificarCedula() {
                String texto = txtCedula.getText();
                if (!texto.matches("\\d*")) {
                    txtCedula.setText(texto.replaceAll("[^\\d]", ""));
                    JOptionPane.showMessageDialog(FormularioNuevoCliente.this, "Cédula solo puede contener números.", "Entrada inválida", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                verificarCedula();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                verificarCedula();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                verificarCedula();
            }
        });
    }

    private void agregarValidacionTexto(JTextField campoTexto) {
        campoTexto.getDocument().addDocumentListener(new DocumentListener() {
            private void verificarTexto() {
                String texto = campoTexto.getText();
                if (!texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                    campoTexto.setText(texto.replaceAll("[^a-zA-ZáéíóúÁÉÍÓÚñÑ ]", ""));
                    JOptionPane.showMessageDialog(FormularioNuevoCliente.this, "Solo se permiten letras en este campo.", "Entrada inválida", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                verificarTexto();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                verificarTexto();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                verificarTexto();
            }
        });
    }

    private void agregarValidacionTelefono() {
        txtTelefono.getDocument().addDocumentListener(new DocumentListener() {
            private void verificarTelefono() {
                String texto = txtTelefono.getText();
                if (!texto.matches("\\d*")) {
                    txtTelefono.setText(texto.replaceAll("[^\\d]", ""));
                    JOptionPane.showMessageDialog(FormularioNuevoCliente.this, "Teléfono solo puede contener números.", "Entrada inválida", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                verificarTelefono();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                verificarTelefono();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                verificarTelefono();
            }
        });
    }

    private void guardarCliente() {
        String cedula = txtCedula.getText().trim();
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();

        // Verificar que todos los campos estén llenos
        if (cedula.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos antes de guardar.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar formato de cada campo
        if (!cedula.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "El campo 'Cédula' debe contener solo números.", "Formato incorrecto", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!nombres.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(this, "El campo 'Nombres' debe contener solo letras.", "Formato incorrecto", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!apellidos.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(this, "El campo 'Apellidos' debe contener solo letras.", "Formato incorrecto", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!telefono.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "El campo 'Teléfono' debe contener solo números.", "Formato incorrecto", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Si todos los campos son válidos, guardar el cliente
        Cliente cliente = new Cliente(cedula, nombres, apellidos, direccion, telefono, email);
        tablaDeClientes.agregarClienteATabla(cliente);
        dispose();
    }

    private void actualizarCliente() {
        // Obtener los valores modificados del formulario
        String cedula = txtCedula.getText().trim();
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();

        // Verificar que todos los campos estén llenos
        if (cedula.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos antes de actualizar.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar formato de cada campo
        if (!cedula.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "El campo 'Cédula' debe contener solo números.", "Formato incorrecto", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!nombres.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(this, "El campo 'Nombres' debe contener solo letras.", "Formato incorrecto", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!apellidos.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(this, "El campo 'Apellidos' debe contener solo letras.", "Formato incorrecto", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!telefono.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "El campo 'Teléfono' debe contener solo números.", "Formato incorrecto", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente cliente = new Cliente(cedula, nombres, apellidos, direccion, telefono, email);

        tablaDeClientes.actualizarClienteEnTabla(cliente, rowIndex);

        dispose();
    }
}