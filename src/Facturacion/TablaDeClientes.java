package Facturacion;

import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TablaDeClientes extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private FormularioNuevoCliente nuevoCliente;

    public TablaDeClientes() {
        setBounds(100, 100, 576, 403);
        getContentPane().setLayout(null);

        JButton botonAgregarClientes = new JButton("Agregar Clientes");
        botonAgregarClientes.setBounds(20, 10, 513, 33);
        getContentPane().add(botonAgregarClientes);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 53, 513, 275);
        getContentPane().add(scrollPane);

        modeloTabla = new DefaultTableModel(
            new Object[][] {},
            new String[] { "Cédula", "Nombres", "Apellidos", "Dirección", "Teléfono", "Email", "Acción" }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Solo la columna "Acción" es interactiva
            }
        };

        tablaClientes = new JTable(modeloTabla);
        scrollPane.setViewportView(tablaClientes);

        TableColumn columnaAccion = tablaClientes.getColumnModel().getColumn(6);
        columnaAccion.setCellRenderer(new ButtonRenderer());
        columnaAccion.setCellEditor(new ButtonEditor(new JButton()));

        JButton botonCerrar = new JButton("Cerrar");
        botonCerrar.addActionListener(e -> dispose());
        botonCerrar.setBounds(432, 338, 118, 25);
        getContentPane().add(botonCerrar);

        botonAgregarClientes.addActionListener(e -> {
            if (nuevoCliente == null || !nuevoCliente.isVisible()) {
                nuevoCliente = new FormularioNuevoCliente(this);
                getParent().add(nuevoCliente);
                nuevoCliente.setVisible(true);

                nuevoCliente.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
                    @Override
                    public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                        nuevoCliente = null;
                    }
                });
            } else {
                nuevoCliente.toFront();
            }
        });
    }

    public void agregarClienteATabla(Cliente cliente) {
        modeloTabla.addRow(new Object[] {
            cliente.getCedula(),
            cliente.getNombres(),
            cliente.getApellidos(),
            cliente.getDireccion(),
            cliente.getTelefono(),
            cliente.getEmail(),
            "Editar"
        });
    }

    public void editarCliente(int rowIndex) {
        String cedula = modeloTabla.getValueAt(rowIndex, 0).toString();
        String nombres = modeloTabla.getValueAt(rowIndex, 1).toString();
        String apellidos = modeloTabla.getValueAt(rowIndex, 2).toString();
        String direccion = modeloTabla.getValueAt(rowIndex, 3).toString();
        String telefono = modeloTabla.getValueAt(rowIndex, 4).toString();
        String email = modeloTabla.getValueAt(rowIndex, 5).toString();

        if (nuevoCliente == null || !nuevoCliente.isVisible()) {
            nuevoCliente = new FormularioNuevoCliente(
                this,
                cedula,
                nombres,
                apellidos,
                direccion,
                telefono,
                email,
                rowIndex
            );
            getParent().add(nuevoCliente);
            nuevoCliente.setVisible(true);

            nuevoCliente.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
                @Override
                public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                    nuevoCliente = null;
                }
            });
        } else {
            nuevoCliente.toFront();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TablaDeClientes frame = new TablaDeClientes();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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
            editarCliente(row);
            fireEditingStopped();
        }
    }
    
    public void actualizarClienteEnTabla(Cliente cliente, int rowIndex) {
        modeloTabla.setValueAt(cliente.getCedula(), rowIndex, 0);
        modeloTabla.setValueAt(cliente.getNombres(), rowIndex, 1);
        modeloTabla.setValueAt(cliente.getApellidos(), rowIndex, 2);
        modeloTabla.setValueAt(cliente.getDireccion(), rowIndex, 3);
        modeloTabla.setValueAt(cliente.getTelefono(), rowIndex, 4);
        modeloTabla.setValueAt(cliente.getEmail(), rowIndex, 5);
    }
}