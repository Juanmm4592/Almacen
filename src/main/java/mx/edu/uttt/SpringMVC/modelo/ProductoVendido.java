package mx.edu.uttt.SpringMVC.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ProductoVendido {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	private String nombre;
	private String codigo;
    private Float cantidad;
    private Float precio;
    // Relacion que tiene con la entidad Venta, pues una venta tendra productos vendidos
    @ManyToOne
    @JoinColumn
    private Venta venta;
    
	public ProductoVendido() {
	}
	
	public ProductoVendido(Float cantidad, Float precio, String nombre, String codigo, Venta venta) {
		this.cantidad = cantidad;
		this.precio = precio;
		this.nombre = nombre;
		this.codigo = codigo;
		this.venta = venta;
	}
    
	public Float getTotal() {
        return this.cantidad * this.precio;
    }

	public Float getCantidad() {
		return cantidad;
	}

	public void setCantidad(Float cantidad) {
		this.cantidad = cantidad;
	}

	public Float getPrecio() {
		return precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}
	
	
}
