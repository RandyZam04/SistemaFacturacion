package Facturacion;

import java.util.List;

public class Factura {
    private String id;
    private Cliente cliente;
    private List<Producto> productos;
    private double subtotal;
    private double IVA;
    private double total;
    private boolean anulada;

    public Factura(String id, Cliente cliente, List<Producto> productos, double subtotal, double IVA, double total) {
        this.id = id;
        this.cliente = cliente;
        this.productos = productos;
        this.subtotal = subtotal;
        this.IVA = IVA;
        this.total = total;
        this.anulada = false;
    }

    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getIVA() {
        return IVA;
    }

    public double getTotal() {
        return total;
    }

    public boolean isAnulada() {
        return anulada;
    }

    public void setAnulada(boolean anulada) {
        this.anulada = anulada;
    }
}