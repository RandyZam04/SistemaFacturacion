package Facturacion;

import javax.swing.*;
import java.awt.*;

public class FormularioNuevoProducto extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtStock;
    private JButton btnGuardar;
    private int rowIndex = -1;

    /**
     * @wbp.parser.constructor
     */
    public FormularioNuevoProducto(TablaDeProductos tablaProductos) {
        this(tablaProductos, "", "", "", "", -1);
    }

    public FormularioNuevoProducto(TablaDeProductos tablaProductos, String codigo, String nombre, String precio, String stock, int rowIndex) {
        this.rowIndex = rowIndex;
        setTitle(rowIndex >= 0 ? "Editar Producto" : "Nuevo Producto");
        setBounds(100, 100, 350, 250);
        getContentPane().setLayout(new GridLayout(5, 2, 5, 5));

        JLabel lblCodigo = new JLabel("Código:");
        getContentPane().add(lblCodigo);

        txtCodigo = new JTextField(codigo);
        getContentPane().add(txtCodigo);

        JLabel lblNombre = new JLabel("Nombre:");
        getContentPane().add(lblNombre);

        txtNombre = new JTextField(nombre);
        getContentPane().add(txtNombre);

        JLabel lblPrecio = new JLabel("Precio:");
        getContentPane().add(lblPrecio);

        txtPrecio = new JTextField(precio);
        getContentPane().add(txtPrecio);

        JLabel lblStock = new JLabel("Stock:");
        getContentPane().add(lblStock);

        txtStock = new JTextField(stock);
        getContentPane().add(txtStock);

        btnGuardar = new JButton(rowIndex >= 0 ? "Actualizar" : "Guardar");
        btnGuardar.addActionListener(e -> {
            String nuevoCodigo = txtCodigo.getText();
            String nuevoNombre = txtNombre.getText();
            String nuevoPrecio = txtPrecio.getText();
            String nuevoStock = txtStock.getText();

            Producto producto = new Producto(nuevoCodigo, nuevoNombre, nuevoPrecio, nuevoStock);

            if (rowIndex >= 0) {
                tablaProductos.actualizarProductoEnTabla(producto, rowIndex);
            } else {
                tablaProductos.agregarProductoATabla(producto);
            }
            dispose();
        });
        getContentPane().add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        getContentPane().add(btnCancelar);

        setClosable(true);
        setIconifiable(true);
        setResizable(false);
    }
}

