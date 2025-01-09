package Facturacion;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
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
    private static List<Cliente> listaClientes = new ArrayList<>();
    private boolean modoSeleccion;

    public TablaDeClientes() {
        this(false); // Constructor por defecto, modo estándar
    }

    public TablaDeClientes(boolean modoSeleccion) {
        this.modoSeleccion = modoSeleccion;

        setTitle("Tabla de Clientes");
        setBounds(100, 100, 608, 482);
        getContentPane().setLayout(null);

        modeloTabla = new DefaultTableModel(
            new Object[][] {},
            modoSeleccion
                ? new String[] { "Cédula", "Nombres", "Apellidos", "Teléfono" }
                : new String[] { "Cédula", "Nombres", "Apellidos", "Dirección", "Teléfono", "Email", "Acción" }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return !modoSeleccion && column == 6; // Solo editable en modo estándar para columna "Acción"
            }
        };

        tablaClientes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        scrollPane.setBounds(10, 10, 560, 340);
        getContentPane().add(scrollPane);
        
        JButton btnCerrar = new JButton("X");
        btnCerrar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
        btnCerrar.setBounds(505, 375, 65, 54);
        getContentPane().add(btnCerrar);

        if (!modoSeleccion) {
            JButton botonAgregarClientes = new JButton("Agregar Clientes");
            botonAgregarClientes.setBounds(10, 360, 150, 30);
            getContentPane().add(botonAgregarClientes);

            JButton botonEliminarClientes = new JButton("Eliminar Cliente");
            botonEliminarClientes.setBounds(180, 360, 150, 30);
            botonEliminarClientes.addActionListener(e -> {
                int rowIndex = tablaClientes.getSelectedRow();
                if (rowIndex == -1) {
                    JOptionPane.showMessageDialog(
                        TablaDeClientes.this,
                        "Por favor, seleccione un cliente para eliminar.",
                        "Error",
                        JOptionPane.WARNING_MESSAGE
                    );
                } else {
                    mostrarDialogoEliminar(rowIndex);
                }
            });
            getContentPane().add(botonEliminarClientes);

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

        if (!modoSeleccion) {
            TableColumn columnaAccion = tablaClientes.getColumnModel().getColumn(6);
            columnaAccion.setCellRenderer(new ButtonRenderer());
            columnaAccion.setCellEditor(new ButtonEditor(new JButton()));
        }

        tablaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { 
                    int row = tablaClientes.getSelectedRow();
                    if (row != -1) {
                        String cedula = modeloTabla.getValueAt(row, 0).toString();
                        String nombres = modeloTabla.getValueAt(row, 1).toString();
                        String apellidos = modeloTabla.getValueAt(row, 2).toString();
                        
                        JOptionPane.showMessageDialog(
                            TablaDeClientes.this,
                            "Cliente seleccionado:\n" +
                            "Cédula: " + cedula + "\n" +
                            "Nombres: " + nombres + "\n" +
                            "Apellidos: " + apellidos,
                            "Cliente Seleccionado",
                            JOptionPane.INFORMATION_MESSAGE
                        );

                        dispose();

                        JInternalFrame parentFrame = (JInternalFrame) getDesktopPane().getSelectedFrame();
                        if (parentFrame instanceof ModuloFacturacion) {
                            ModuloFacturacion modulo = (ModuloFacturacion) parentFrame;
                            modulo.setCedulaClienteSeleccionada(cedula);
                        }
                    }
                }
            }
        });
        actualizarTablaDesdeLista();
    }

    public void agregarClienteATabla(Cliente cliente) {
        listaClientes.add(cliente); 
        actualizarTablaDesdeLista();
    }

    public void actualizarClienteEnTabla(Cliente cliente, int rowIndex) {
        if (rowIndex >= 0 && rowIndex < modeloTabla.getRowCount()) {
            listaClientes.set(rowIndex, cliente);
            actualizarTablaDesdeLista();
        }
    }

    private void actualizarTablaDesdeLista() {
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de cargar datos
        for (Cliente cliente : listaClientes) {
            modeloTabla.addRow(modoSeleccion
                ? new Object[] {
                    cliente.getCedula(),
                    cliente.getNombres(),
                    cliente.getApellidos(),
                    cliente.getTelefono()
                }
                : new Object[] {
                    cliente.getCedula(),
                    cliente.getNombres(),
                    cliente.getApellidos(),
                    cliente.getDireccion(),
                    cliente.getTelefono(),
                    cliente.getEmail(),
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
            editarCliente(row);
            fireEditingStopped();
        }
    }

    public void editarCliente(int rowIndex) {
        Cliente cliente = listaClientes.get(rowIndex);
        if (nuevoCliente == null || !nuevoCliente.isVisible()) {
            nuevoCliente = new FormularioNuevoCliente(
                this,
                cliente.getCedula(),
                cliente.getNombres(),
                cliente.getApellidos(),
                cliente.getDireccion(),
                cliente.getTelefono(),
                cliente.getEmail(),
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

    public static List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public JTable getTablaClientes() {
        return tablaClientes;
    }
    
    private void mostrarDialogoEliminar(int rowIndex) {
        Cliente cliente = listaClientes.get(rowIndex);
        
        JDialog dialogoEliminar = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Confirmar Eliminación", true);
        dialogoEliminar.setSize(400, 200);
        dialogoEliminar.getContentPane().setLayout(null);
        dialogoEliminar.setLocationRelativeTo(this);

        JLabel label = new JLabel("<html>¿Está seguro de que desea eliminar al cliente?<br>" +
                                   "Cédula: " + cliente.getCedula() + "<br>" +
                                   "Nombres: " + cliente.getNombres() + " " + cliente.getApellidos() + "</html>");
        label.setBounds(20, 20, 360, 80);
        dialogoEliminar.getContentPane().add(label);

        JButton botonConfirmar = new JButton("Confirmar");
        botonConfirmar.setBounds(70, 120, 100, 30);
        dialogoEliminar.getContentPane().add(botonConfirmar);

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setBounds(200, 120, 100, 30);
        dialogoEliminar.getContentPane().add(botonCancelar);

        botonConfirmar.addActionListener(e -> {
            listaClientes.remove(rowIndex);
            actualizarTablaDesdeLista();
            dialogoEliminar.dispose();
            JOptionPane.showMessageDialog(
                TablaDeClientes.this,
                "Cliente eliminado exitosamente.",
                "Eliminación Exitosa",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        botonCancelar.addActionListener(e -> dialogoEliminar.dispose());

        dialogoEliminar.setVisible(true);
    }
}