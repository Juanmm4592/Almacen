package mx.edu.uttt.SpringMVC.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import mx.edu.uttt.SpringMVC.interfaces.IVentas;

@Controller
@RequestMapping(path = "/ventas")
public class VentasController {

	@Autowired
	IVentas ventas;
	
	// Metodo que muestra todas las ventas
	@GetMapping(value = "/")
    public String mostrarVentas(Model model) {
        model.addAttribute("ventas", ventas.findAll());
        return "listarVentas";
    }
}
