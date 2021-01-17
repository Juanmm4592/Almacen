package mx.edu.uttt.SpringMVC.interfaces;

import org.springframework.data.repository.CrudRepository;

import mx.edu.uttt.SpringMVC.modelo.ProductoVendido;

public interface IProductoVendido extends CrudRepository<ProductoVendido, Integer>{

}
