package mx.edu.uttt.SpringMVC.controlador;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.uttt.SpringMVC.interfaces.IProducto;
import mx.edu.uttt.SpringMVC.interfaces.IProductoVendido;
import mx.edu.uttt.SpringMVC.interfaces.IVentas;
import mx.edu.uttt.SpringMVC.modelo.Producto;
import mx.edu.uttt.SpringMVC.modelo.ProductoVender;
import mx.edu.uttt.SpringMVC.modelo.ProductoVendido;
import mx.edu.uttt.SpringMVC.modelo.Venta;

@Controller
@RequestMapping(path = "/vender")
public class VenderController {

	@Autowired
	IProducto productoRepository;

	@Autowired
	IProductoVendido productoVendidoRepository;

	@Autowired
	IVentas ventasRepository;

	// Metodo que muestra la vista pasandole la variable del total del carrito
	@GetMapping(value = "/")
	public String interfazVender(Model model, HttpServletRequest request) {
		model.addAttribute("producto", new Producto());
		float total = 0;
		ArrayList<ProductoVender> carrito = this.obtenerCarrito(request);
		for (ProductoVender p : carrito)
			total += p.getTotal();
		model.addAttribute("total", total);
		return "vender";
	}

	// Se obtiene el carrito y si existe y tiene elementos se invoca al metodo
	// remove del ArrayList
	// que elimina el elemento usando su indice
	// Finalmente se llama al método guardarCarrito y se hace una redirección a
	// vender
	@PostMapping(value = "/quitar/{indice}")
	public String quitarDelCarrito(@PathVariable int indice, HttpServletRequest request) {
		ArrayList<ProductoVender> carrito = this.obtenerCarrito(request);
		if (carrito != null && carrito.size() > 0 && carrito.get(indice) != null) {
			carrito.remove(indice);
			this.guardarCarrito(carrito, request);
		}
		return "redirect:/vender/";
	}

	// Metodos que ayudan a guardar y a obtener el carrito
	// Ambos se guardan en la sesión, por eso los metodos necesitan el parámetro de HttpServletRequest
	private ArrayList<ProductoVender> obtenerCarrito(HttpServletRequest request) {
		ArrayList<ProductoVender> carrito = (ArrayList<ProductoVender>) request.getSession().getAttribute("carrito");
		if (carrito == null) {
			carrito = new ArrayList<>();
		}
		return carrito;
	}

	private void guardarCarrito(ArrayList<ProductoVender> carrito, HttpServletRequest request) {
		request.getSession().setAttribute("carrito", carrito);
	}

	// Para limpar o eliminar el carrito se guarda un ArrayList vacio
	private void limpiarCarrito(HttpServletRequest request) {
		this.guardarCarrito(new ArrayList<>(), request);
	}

	@GetMapping(value = "/limpiar")
	public String cancelarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs) {
		this.limpiarCarrito(request);
		redirectAttrs.addFlashAttribute("mensaje", "Venta cancelada").addFlashAttribute("clase", "info");
		return "redirect:/vender/";
	}

	// Se obtiene el producto por codigo de barras, despues se valida que el
	// producto exista por codigo de barras y que tenga existencia
	// Luego se busca dentro del ArrayList y si ya existe simplemente se le aumenta
	// la cantidad
	@PostMapping(value = "/agregar")
	public String agregarAlCarrito(@ModelAttribute Producto producto, HttpServletRequest request,
			RedirectAttributes redirectAttrs) {
		ArrayList<ProductoVender> carrito = this.obtenerCarrito(request);
		Producto productoBuscadoPorCodigo = productoRepository.findFirstByCodigo(producto.getCodigo());
		if (productoBuscadoPorCodigo == null) {
			redirectAttrs
					.addFlashAttribute("mensaje", "El producto con el codigo " + producto.getCodigo() + " no existe")
					.addFlashAttribute("clase", "warning");
			return "redirect:/vender/";
		}
		if (productoBuscadoPorCodigo.sinExistencia()) {
			redirectAttrs.addFlashAttribute("mensaje", "El producto esta agotado").addFlashAttribute("clase",
					"warning");
			return "redirect:/vender/";
		}
		boolean encontrado = false;
		for (ProductoVender productoParaVenderActual : carrito) {
			if (productoParaVenderActual.getCodigo().equals(productoBuscadoPorCodigo.getCodigo())) {
				productoParaVenderActual.aumentarCantidad();
				encontrado = true;
				break;
			}
		}
		if (!encontrado) {
			carrito.add(new ProductoVender(productoBuscadoPorCodigo.getNombre(), productoBuscadoPorCodigo.getCodigo(),
					productoBuscadoPorCodigo.getPrecio(), productoBuscadoPorCodigo.getStock(),
					productoBuscadoPorCodigo.getId(), 1f));
		}
		this.guardarCarrito(carrito, request);
		return "redirect:/vender/";
	}

	// Este metodo se encarga de terminar la venta
	// crear una nueva venta, restar las existencias de los productos vendidos
	// y agregar los nuevos que se venden junto con la venta
	@PostMapping(value = "/terminar")
	public String terminarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs) {
		ArrayList<ProductoVender> carrito = this.obtenerCarrito(request);
		// Si no hay carrito o está vacío, regresamos inmediatamente a vender
		if (carrito == null || carrito.size() <= 0) {
			return "redirect:/vender/";
		}
		// Recorre el carrito
		for (ProductoVender productoParaVender : carrito) {
			// Se obtiene el producto
			Producto p = productoRepository.findById(productoParaVender.getId()).orElse(null);
			// Se valida si la cantidad a vender no supera el stock, si supera el stock
			// manda el mensaje y lo redirecciona a vender
			if (productoParaVender.getCantidad() > productoParaVender.getStock()) {
				this.limpiarCarrito(request);
				redirectAttrs.addFlashAttribute("mensaje", "Venta no realizada").addFlashAttribute("clase", "danger");
				return "redirect:/vender/";
			}
			Venta v = ventasRepository.save(new Venta());
			// Se le resta al stock o existencia
			p.restarExistencia(productoParaVender.getCantidad());
			// Se guarda con la existencia ya restada
			productoRepository.save(p);
			// Se crea el un nuevo producto que sera el que se guarda junto con la venta
			ProductoVendido productoVendido = new ProductoVendido(productoParaVender.getCantidad(),
					productoParaVender.getPrecio(), productoParaVender.getNombre(), productoParaVender.getCodigo(), v);
			// Y se guarda en la bd
			productoVendidoRepository.save(productoVendido);
		}
		// Se limpia el carrito
		this.limpiarCarrito(request);
		// Se indica una venta exitosa
		redirectAttrs.addFlashAttribute("mensaje", "Venta realizada correctamente").addFlashAttribute("clase",
				"success");
		return "redirect:/vender/";
	}
}
