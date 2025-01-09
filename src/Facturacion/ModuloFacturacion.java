package Facturacion;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ModuloFacturacion extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtCedulaCliente;
    private JTextField txtCodigoProducto;
    private JTextField txtCantidad;
    private JTable tablaProductosFactura;
    private DefaultTableModel modeloTabla;
    JLabel labelSubtotal, labelIVA, labelTotal;
    private List<Factura> listaFacturas = new ArrayList<>();


    public ModuloFacturacion() {
        setTitle("Modulo de Facturación");
        setBounds(100, 100, 800, 600);
        getContentPane().setLayout(null);

        JLabel lblCedulaCliente = new JLabel("Cédula Cliente:");
        lblCedulaCliente.setBounds(20, 20, 120, 25);
        getContentPane().add(lblCedulaCliente);

        txtCedulaCliente = new JTextField();
        txtCedulaCliente.setBounds(140, 20, 150, 25);
        getContentPane().add(txtCedulaCliente);
        txtCedulaCliente.setColumns(10);

        JButton btnBuscarCliente = new JButton("Seleccionar Cliente");
        btnBuscarCliente.setBounds(300, 20, 150, 25);
        getContentPane().add(btnBuscarCliente);

        JLabel lblCodigoProducto = new JLabel("Código Producto:");
        lblCodigoProducto.setBounds(20, 60, 120, 25);
        getContentPane().add(lblCodigoProducto);

        txtCodigoProducto = new JTextField();
        txtCodigoProducto.setBounds(140, 60, 150, 25);
        getContentPane().add(txtCodigoProducto);
        txtCodigoProducto.setColumns(10);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(300, 60, 120, 25);
        getContentPane().add(lblCantidad);

        txtCantidad = new JTextField();
        txtCantidad.setBounds(370, 60, 80, 25);
        getContentPane().add(txtCantidad);

        JButton btnAgregarProducto = new JButton("Agregar Producto");
        btnAgregarProducto.setBounds(460, 60, 150, 25);
        getContentPane().add(btnAgregarProducto);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 100, 750, 300);
        getContentPane().add(scrollPane);

        modeloTabla = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Código", "Nombre", "Precio", "Cantidad", "Subtotal"}
        );
        tablaProductosFactura = new JTable(modeloTabla);
        scrollPane.setViewportView(tablaProductosFactura);

        labelSubtotal = new JLabel("Subtotal: $0.00");
        labelSubtotal.setBounds(20, 420, 150, 25);
        getContentPane().add(labelSubtotal);

        labelIVA = new JLabel("IVA (12%): $0.00");
        labelIVA.setBounds(20, 450, 150, 25);
        getContentPane().add(labelIVA);

        labelTotal = new JLabel("Total: $0.00");
        labelTotal.setBounds(20, 480, 150, 25);
        getContentPane().add(labelTotal);

        JButton btnCalcularTotales = new JButton("Calcular Totales");
        btnCalcularTotales.setBounds(200, 420, 150, 25);
        btnCalcularTotales.addActionListener(e -> calcularTotales());
        getContentPane().add(btnCalcularTotales);

        JButton btnGuardarFactura = new JButton("Guardar Factura");
        btnGuardarFactura.setBounds(400, 420, 150, 25);
        getContentPane().add(btnGuardarFactura);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(600, 420, 150, 25);
        getContentPane().add(btnCancelar);
        
        JButton btnHistorialFacturas = new JButton("Ver Historial de Facturas");
        btnHistorialFacturas.setBounds(20, 520, 200, 25);
        btnHistorialFacturas.addActionListener(e -> {
            HistorialFacturas historial = new HistorialFacturas(listaFacturas);
            historial.setVisible(true);
        });
        getContentPane().add(btnHistorialFacturas);
        

        txtCedulaCliente.addActionListener(e -> cargarDatosCliente());
        btnBuscarCliente.addActionListener(e -> seleccionarCliente());
        txtCodigoProducto.addActionListener(e -> cargarDatosProducto());
        btnAgregarProducto.addActionListener(e -> agregarProductoAFactura());
        btnGuardarFactura.addActionListener(e -> guardarFactura());
        btnCancelar.addActionListener(e -> dispose());
        
        
        
    }

    private void cargarDatosCliente() {
        String cedula = txtCedulaCliente.getText();
        Cliente cliente = buscarClientePorCedula(cedula);
        if (cliente != null) {
            JOptionPane.showMessageDialog(this, "Cliente: " + cliente.getNombres() + " " + cliente.getApellidos());
        } else {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado");
        }
    }

    private Cliente buscarClientePorCedula(String cedula) {
        for (Cliente cliente : TablaDeClientes.getListaClientes()) {
            if (cliente.getCedula().equals(cedula)) {
                return cliente;
            }
        }
        return null;
    }

    private void seleccionarCliente() {
        TablaDeClientes tablaClientes = new TablaDeClientes(true);
        JDialog dialog = new JDialog();
        dialog.setTitle("Seleccionar Cliente");
        dialog.setSize(600, 400);
        dialog.setModal(true);
        dialog.getContentPane().add(tablaClientes.getContentPane());
        dialog.setVisible(true);

        JTable tabla = tablaClientes.getTablaClientes();
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = tabla.getSelectedRow();
                    if (row != -1) {
                        String cedula = (String) tabla.getValueAt(row, 0);
                        txtCedulaCliente.setText(cedula);
                        dialog.dispose();
                    }
                }
            }
        });
    }

    private void cargarDatosProducto() {
        String codigo = txtCodigoProducto.getText();
        Producto producto = buscarProductoPorCodigo(codigo);
        if (producto != null) {
            JOptionPane.showMessageDialog(this, "Producto: " + producto.getNombre());
            txtCantidad.requestFocus();
        } else {
            JOptionPane.showMessageDialog(this, "Producto no encontrado");
        }
    }

    private Producto buscarProductoPorCodigo(String codigo) {
        for (Producto producto : TablaDeProductos.getListaProductos()) {
            if (producto.getCodigo().equals(codigo)) {
                return producto;
            }
        }
        return null;
    }

    private void agregarProductoAFactura() {
        String codigo = txtCodigoProducto.getText();
        String cantidadStr = txtCantidad.getText();
        Producto producto = buscarProductoPorCodigo(codigo);
        if (producto != null) {
            try {
                int cantidad = Integer.parseInt(cantidadStr);
                double precio = Double.parseDouble(producto.getPrecio());
                double subtotal = precio * cantidad;

                modeloTabla.addRow(new Object[]{
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    cantidad,
                    subtotal
                });

                txtCodigoProducto.setText("");
                txtCantidad.setText("");
                txtCodigoProducto.requestFocus();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Cantidad inválida");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Producto no encontrado");
        }
    }

    private void calcularTotales() {
        double subtotal = 0.0;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            subtotal += (double) modeloTabla.getValueAt(i, 4);
        }
        double iva = subtotal * 0.12;
        double total = subtotal + iva;

        labelSubtotal.setText(String.format("Subtotal: $%.2f", subtotal));
        labelIVA.setText(String.format("IVA (12%%): $%.2f", iva));
        labelTotal.setText(String.format("Total: $%.2f", total));
    }

    private void guardarFactura() {
        String idFactura = "FAC-" + (listaFacturas.size() + 1);
        Cliente cliente = buscarClientePorCedula(txtCedulaCliente.getText());
        List<Producto> productos = obtenerProductosFactura();
        double subtotal = calcularSubtotal();
        double iva = subtotal * 0.12;
        double total = subtotal + iva;

        Factura nuevaFactura = new Factura(idFactura, cliente, productos, subtotal, iva, total);
        listaFacturas.add(nuevaFactura);

        JOptionPane.showMessageDialog(this, "Factura guardada con éxito.");
        modeloTabla.setRowCount(0);
        labelSubtotal.setText("Subtotal: $0.00");
        labelIVA.setText("IVA (12%): $0.00");
        labelTotal.setText("Total: $0.00");
    }
    
    public void setCedulaClienteSeleccionada(String cedula) {
        txtCedulaCliente.setText(cedula);
    }
    
    private List<Producto> obtenerProductosFactura() {
        List<Producto> productos = new ArrayList<>();
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            String codigo = (String) modeloTabla.getValueAt(i, 0);
            String nombre = (String) modeloTabla.getValueAt(i, 1);
            String precio = (String) modeloTabla.getValueAt(i, 2);
            String cantidad = String.valueOf(modeloTabla.getValueAt(i, 3));
            Producto producto = new Producto(codigo, nombre, precio, cantidad);
            productos.add(producto);
        }
        return productos;
    }
    
    private double calcularSubtotal() {
        double subtotal = 0.0;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            double precio = Double.parseDouble((String) modeloTabla.getValueAt(i, 2));
            int cantidad = Integer.parseInt(String.valueOf(modeloTabla.getValueAt(i, 3)));
            subtotal += precio * cantidad;
        }
        return subtotal;
    }

}


