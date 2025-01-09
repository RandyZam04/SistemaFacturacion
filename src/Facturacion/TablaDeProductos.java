package Facturacion;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TablaDeProductos extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private FormularioNuevoProducto nuevoProducto;
    private static List<Producto> listaProductos = new ArrayList<>();

    public TablaDeProductos() {
        setBounds(100, 100, 576, 403);
        getContentPane().setLayout(null);

        JButton botonAgregarProductos = new JButton("Agregar Productos");
        botonAgregarProductos.setBounds(20, 10, 513, 33);
        getContentPane().add(botonAgregarProductos);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 53, 513, 275);
        getContentPane().add(scrollPane);

        modeloTabla = new DefaultTableModel(
            new Object[][] {},
            new String[] { "Código", "Nombre", "Precio", "Stock", "Acción" }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        tablaProductos = new JTable(modeloTabla);
        scrollPane.setViewportView(tablaProductos);

        TableColumn columnaAccion = tablaProductos.getColumnModel().getColumn(4);
        columnaAccion.setCellRenderer(new ButtonRenderer());
        columnaAccion.setCellEditor(new ButtonEditor(new JButton()));

        JButton botonCerrar = new JButton("Cerrar");
        botonCerrar.addActionListener(e -> dispose());
        botonCerrar.setBounds(415, 338, 118, 25);
        getContentPane().add(botonCerrar);
        
        JButton btnBorrar = new JButton("Eliminar Boton");
        btnBorrar.addActionListener(e -> {
            int rowIndex = tablaProductos.getSelectedRow();
            if (rowIndex == -1) {
                JOptionPane.showMessageDialog(
                	TablaDeProductos.this,
                    "Por favor, seleccione un cliente para eliminar.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE
                );
            } else {
                mostrarDialogoEliminar(rowIndex);
            }
        });
        btnBorrar.setBounds(20, 338, 112, 21);
        getContentPane().add(btnBorrar);

        botonAgregarProductos.addActionListener(e -> {
            if (nuevoProducto == null || !nuevoProducto.isVisible()) {
                nuevoProducto = new FormularioNuevoProducto(this);
                getParent().add(nuevoProducto);
                nuevoProducto.setVisible(true);

                nuevoProducto.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
                    @Override
                    public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                        nuevoProducto = null;
                    }
                });
            } else {
                nuevoProducto.toFront();
            }
        });

        actualizarTablaDesdeLista(); // Llenar la tabla al abrir el JInternalFrame
    }

    public void agregarProductoATabla(Producto producto) {
        listaProductos.add(producto); // Agregar producto a la lista estática
        modeloTabla.addRow(new Object[] {
            producto.getCodigo(),
            producto.getNombre(),
            producto.getPrecio(),
            producto.getStock(),
            "Editar"
        });
    }

    public void actualizarProductoEnTabla(Producto producto, int rowIndex) {
        if (rowIndex >= 0 && rowIndex < modeloTabla.getRowCount()) {
            modeloTabla.setValueAt(producto.getCodigo(), rowIndex, 0);
            modeloTabla.setValueAt(producto.getNombre(), rowIndex, 1);
            modeloTabla.setValueAt(producto.getPrecio(), rowIndex, 2);
            modeloTabla.setValueAt(producto.getStock(), rowIndex, 3);

            listaProductos.set(rowIndex, producto);
        }
    }

    private void actualizarTablaDesdeLista() {
        modeloTabla.setRowCount(0);
        for (Producto producto : listaProductos) {
            modeloTabla.addRow(new Object[] {
                producto.getCodigo(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getStock(),
                "Editar"
            });
        }
    }

    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setText("Editar");
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    private class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private JButton button;
        private int row;

        public ButtonEditor(JButton button) {
            this.button = button;
            this.button.addActionListener(this);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "Editar";
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            editarProducto(row);
            fireEditingStopped();
        }
    }

    public void editarProducto(int rowIndex) {
        Producto producto = listaProductos.get(rowIndex);
        if (nuevoProducto == null || !nuevoProducto.isVisible()) {
            nuevoProducto = new FormularioNuevoProducto(
                this,
                producto.getCodigo(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getStock(),
                rowIndex
            );
            getParent().add(nuevoProducto);
            nuevoProducto.setVisible(true);

            nuevoProducto.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
                @Override
                public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                    nuevoProducto = null;
                }
            });
        } else {
            nuevoProducto.toFront();
        }
    }
    
    public static List<Producto> getListaProductos() {
        return listaProductos;
    }
    
    private void mostrarDialogoEliminar(int rowIndex) {
        Producto producto = listaProductos.get(rowIndex);
        
        JDialog dialogoEliminar = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Confirmar Eliminación", true);
        dialogoEliminar.setSize(400, 200);
        dialogoEliminar.getContentPane().setLayout(null);
        dialogoEliminar.setLocationRelativeTo(this);

        JLabel label = new JLabel("<html>¿Está seguro de que desea eliminar al cliente?<br>" +
                                   "Cédula: " + producto.getCodigo() + "<br>" +
                                   "Nombres: " + producto.getNombre() + " " + producto.getPrecio() + "</html>");
        label.setBounds(20, 20, 360, 80);
        dialogoEliminar.getContentPane().add(label);

        JButton botonConfirmar = new JButton("Confirmar");
        botonConfirmar.setBounds(70, 120, 100, 30);
        dialogoEliminar.getContentPane().add(botonConfirmar);

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setBounds(200, 120, 100, 30);
        dialogoEliminar.getContentPane().add(botonCancelar);

        botonConfirmar.addActionListener(e -> {
            listaProductos.remove(rowIndex);
            actualizarTablaDesdeLista();
            dialogoEliminar.dispose();
            JOptionPane.showMessageDialog(
                TablaDeProductos.this,
                "Cliente eliminado exitosamente.",
                "Eliminación Exitosa",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        botonCancelar.addActionListener(e -> dialogoEliminar.dispose());

        dialogoEliminar.setVisible(true);
    }
    
}
