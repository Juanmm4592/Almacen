package mx.edu.uttt.SpringMVC.interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.edu.uttt.SpringMVC.modelo.Producto;

@Repository
public interface IProducto extends CrudRepository<Producto, Integer>{

	Producto findFirstByCodigo(String codigo);
}
