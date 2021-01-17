package mx.edu.uttt.SpringMVC.interfaceService;

import java.util.List;
import java.util.Optional;

import mx.edu.uttt.SpringMVC.modelo.Producto;

public interface IProductoServices {
	
	//Metodos
	
	//Listar productos
	public List<Producto>listar();
	
	//Listar por ID
	public Optional<Producto>listarId(int id);
	
	//Guardar producto
	public int save(Producto p);
	
	//Eliminar producto
	public void delete(int id);

}
