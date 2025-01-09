package Facturacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistorialFacturas extends JFrame {

    private static final long serialVersionUID = 1L;
    private DefaultTableModel modeloTabla;
    private JTable tablaFacturas;
    private List<Factura> facturas;

    public HistorialFacturas(List<Factura> facturas) {
        this.facturas = facturas;
        setTitle("Historial de Facturas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());

        modeloTabla = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Cliente", "Total", "Estado"}
        );
        tablaFacturas = new JTable(modeloTabla);
        cargarFacturas();

        JScrollPane scrollPane = new JScrollPane(tablaFacturas);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnAnular = new JButton("Anular Factura");
        btnAnular.addActionListener(e -> anularFacturaSeleccionada());
        add(btnAnular, BorderLayout.SOUTH);
    }

    private void cargarFacturas() {
        modeloTabla.setRowCount(0);
        for (Factura factura : facturas) {
            modeloTabla.addRow(new Object[]{
                factura.getId(),
                factura.getCliente().getNombres(),
                String.format("$%.2f", factura.getTotal()),
                factura.isAnulada() ? "Anulada" : "Activa"
            });
        }
    }

    private void anularFacturaSeleccionada() {
        int row = tablaFacturas.getSelectedRow();
        if (row != -1) {
            String idFactura = (String) modeloTabla.getValueAt(row, 0);
            Factura factura = facturas.stream()
                .filter(f -> f.getId().equals(idFactura))
                .findFirst()
                .orElse(null);

            if (factura != null && !factura.isAnulada()) {
                factura.setAnulada(true);
                JOptionPane.showMessageDialog(this, "Factura anulada correctamente.");
                cargarFacturas();
            } else {
                JOptionPane.showMessageDialog(this, "La factura ya está anulada o no es válida.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una factura para anular.");
        }
    }
}