package mx.edu.uttt.SpringMVC.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
// import javax.validation.constraints.NotNull;


@Entity
@Table(name = "producto")
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	// @NotNull(message = "Debes especificar el nombre")
	private String nombre;
	// @NotNull(message = "Debes especificar el codigo")
	private String codigo;
	// @NotNull(message = "Debes especificar el stock")
	private int stock;
	// @NotNull(message = "Debes especificar el precio")
	private float precio;
	
	public Producto() {
	}

	public Producto(int id, String nombre, String codigo, int stock, float precio) {
		this.id = id;
		this.nombre = nombre;
		this.codigo = codigo;
		this.stock = stock;
		this.precio = precio;
	}
	
	public Producto(String nombre, String codigo, int stock, float precio) {
		this.nombre = nombre;
		this.codigo = codigo;
		this.stock = stock;
		this.precio = precio;
	}

	public boolean sinExistencia() {
        return this.stock <= 0;
    }
	
	public void restarExistencia(Float existencia) {
        this.stock -= existencia;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}
	
}
