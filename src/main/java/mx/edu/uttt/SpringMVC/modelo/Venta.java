package mx.edu.uttt.SpringMVC.modelo;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import mx.edu.uttt.SpringMVC.extra.Utiles;

@Entity
public class Venta {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String fechaYHora;
    
    // Se relaciona la venta con los productos vendidos
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private Set<ProductoVendido> productos;
    
    public Venta() {
        this.fechaYHora = Utiles.obtenerFechaYHoraActual();
    }
    
    // Recorre todo el conjunto de productos para obtener el total de venta
    public Float getTotal() {
        Float total = 0f;
        for (ProductoVendido productoVendido : this.productos) {
            total += productoVendido.getTotal();
        }
        return total;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFechaYHora() {
		return fechaYHora;
	}

	public void setFechaYHora(String fechaYHora) {
		this.fechaYHora = fechaYHora;
	}

	public Set<ProductoVendido> getProductos() {
		return productos;
	}

	public void setProductos(Set<ProductoVendido> productos) {
		this.productos = productos;
	}
}
