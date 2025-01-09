package Facturacion;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDesktopPane;

public class FormularioPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JDesktopPane desktopPane;
    
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FormularioPrincipal frame = new FormularioPrincipal();
                    
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public FormularioPrincipal() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 700);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuArchivo = new JMenu("Archivo");
        menuArchivo.setForeground(new Color(0, 0, 0));
        menuArchivo.setBackground(new Color(255, 255, 255));
        menuBar.add(menuArchivo);

        JMenuItem itemSalir = new JMenuItem("Salir");
        menuArchivo.add(itemSalir);
        
        JMenu menuClientes = new JMenu("Clientes");
        menuBar.add(menuClientes);
        
        JMenuItem itemListaClientes = new JMenuItem("Lista Clientes");
        menuClientes.add(itemListaClientes);
        
        JMenu menuProductos = new JMenu("Productos");
        menuBar.add(menuProductos);
        
        JMenuItem itemListaProductos = new JMenuItem("Lista Productos");
        menuProductos.add(itemListaProductos);
        
        JMenu menuFacturas = new JMenu("Facturas");
        menuBar.add(menuFacturas);
        
        JMenuItem itemListaFacturas = new JMenuItem("Lista Facturas");
        menuFacturas.add(itemListaFacturas);
        
        itemListaClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirVentana(new TablaDeClientes());
            }
        });

        itemListaProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirVentana(new TablaDeProductos());
            }
        });
        
        itemListaFacturas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirVentana(new ModuloFacturacion());
            }
        });

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        desktopPane = new JDesktopPane();
        desktopPane.setBounds(0, 0, 1013, 641);
        contentPane.add(desktopPane);

        itemSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void abrirVentana(JInternalFrame ventana) {
        boolean ventanaAbierta = false;

        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (ventana.getClass().isInstance(frame)) {
                try {
                    frame.setSelected(true);
                    frame.toFront();
                } catch (java.beans.PropertyVetoException ex) {
                    ex.printStackTrace();
                }
                ventanaAbierta = true;
                break;
            }
        }

        if (!ventanaAbierta) {
            desktopPane.add(ventana);
            ventana.setVisible(true);
        }
    }
}
