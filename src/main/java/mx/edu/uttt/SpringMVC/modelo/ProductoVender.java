package mx.edu.uttt.SpringMVC.modelo;


// No es una entidad, ya que no se guarda los datos en la bd, 
// es simplemente un ayudante para la lista de compras.
public class ProductoVender extends Producto {
	
	private Float cantidad;
	
	public ProductoVender(String nombre, String codigo, Float precio, int existencia, Integer id, Float cantidad) {
		super(id,nombre,codigo,existencia,precio);
        this.cantidad = cantidad;
    }

    public ProductoVender(String nombre, String codigo, Float precio, int existencia, Float cantidad) {
    	super(nombre, codigo, existencia, precio);
        this.cantidad = cantidad;
    }

    public void aumentarCantidad() {
        this.cantidad++;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public Float getTotal() {
        return this.getPrecio() * this.cantidad;
    }

}
