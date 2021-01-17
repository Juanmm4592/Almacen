package mx.edu.uttt.SpringMVC.controlador;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import mx.edu.uttt.SpringMVC.interfaceService.IProductoServices;
import mx.edu.uttt.SpringMVC.modelo.Producto;

@Controller
@RequestMapping(path = "/producto")
public class ProductoControlador {

	@Autowired
	private IProductoServices service;
	
	@GetMapping("/listar")
	public String listar(Model model) {
		List<Producto>productos = service.listar();
		model.addAttribute("productos", productos);
		return "listarProducto";
	}
	
	@GetMapping("/nuevo")
	public String agregar(Model model) {
		model.addAttribute("producto", new Producto());
		return "registroProducto";
	}
	
	@PostMapping("/guardar")
	public String save(@Valid Producto p, Model model) {
		service.save(p);
		return "redirect:/producto/listar";
	}
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable int id, Model model) {
		Optional<Producto>producto = service.listarId(id);
		model.addAttribute("producto", producto);
		return "registroProducto";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable int id, Model model) {
		service.delete(id);
		return "redirect:/producto/listar";
	}
}
