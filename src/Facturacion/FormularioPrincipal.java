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
        setBounds(100, 100, 891, 583);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuArchivo = new JMenu("Archivo");
        menuArchivo.setForeground(new Color(0, 0, 0));
        menuArchivo.setBackground(new Color(255, 255, 255));
        menuBar.add(menuArchivo);

        JMenuItem itemListaClientes = new JMenuItem("ListaClientes");
        menuArchivo.add(itemListaClientes);

        JMenuItem itemSalir = new JMenuItem("Salir");
        menuArchivo.add(itemSalir);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        desktopPane = new JDesktopPane();
        desktopPane.setBounds(0, 0, 877, 514);
        contentPane.add(desktopPane);

        itemListaClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean ventanaAbierta = false;

                for (JInternalFrame frame : desktopPane.getAllFrames()) {
                    if (frame instanceof TablaDeClientes) {
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
                    TablaDeClientes tablaDeClientes = new TablaDeClientes();
                    desktopPane.add(tablaDeClientes);
                    tablaDeClientes.setVisible(true);
                }
            }
        });

        itemSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
