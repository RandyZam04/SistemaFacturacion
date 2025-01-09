package Facturacion;

import java.util.ArrayList;
import java.util.List;

public class GestorProductos {
    private static List<Producto> productos = new ArrayList<>();

    public static void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public static List<Producto> obtenerProductos() {
        return productos;
    }
}