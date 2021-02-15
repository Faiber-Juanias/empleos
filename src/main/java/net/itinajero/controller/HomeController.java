package net.itinajero.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.itinajero.model.Vacante;

@Controller
public class HomeController {

	@GetMapping("/")
	public String mostrarHome(Model model) {
		model.addAttribute("mensaje", "Bienvenidos a Empleos App");
		model.addAttribute("fecha", new Date());
		return "home";
	}
	
	@GetMapping("/listado")
	public String mostrarListado(Model model) {
		List<String> lista = new LinkedList<>();
		lista.add("Ingeniero de Sistemas");
		lista.add("Auxiliar de contabilidad");
		lista.add("Vendedor");
		lista.add("Arquitecto");

		model.addAttribute("empleos", lista);
		return "listado";
	}
	
	@GetMapping("/detalle")
	public String mostrarDetalle(Model model) {
		Vacante vacante = new Vacante();
		vacante.setNombre("Ingeniero de Comunicaciones");
		vacante.setDescripcion("Se solicita ingeniero para dar soporte a Intranet");
		vacante.setFecha(new Date());
		vacante.setSalario(9700.0);
		
		model.addAttribute("vacante", vacante);
		return "detalle";
	}
	
	@GetMapping("/tabla")
	public String mostrarTable(Model model) {
		List<Vacante> lista = getVacantes();
		model.addAttribute("vacantes", lista);
		return "tabla"; 
	}
	
	/**
	 * Método que regresa una lista de objetos de tipo vacante
	 * @return
	 */
	private List<Vacante> getVacantes() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		List<Vacante> lista = new LinkedList<>();
		
		try {
			Vacante v1 = new Vacante();
			v1.setId(1);
			v1.setNombre("Ingeniero de Comunicaciones");
			v1.setDescripcion("Se solicita ingeniero para dar soporte a Intranet");
			v1.setFecha(sdf.parse("12-05-2010"));
			v1.setSalario(9700.0);
			v1.setDestacado(1);
			v1.setImagen("empresa1.png");
			
			Vacante v2 = new Vacante();
			v2.setId(2);
			v2.setNombre("Ingeniero de Sistemas");
			v2.setDescripcion("Se solicita ingeniero para crear aplicación web");
			v2.setFecha(sdf.parse("09-03-2013"));
			v2.setSalario(10000.00);
			v2.setDestacado(0);
			v2.setImagen("empresa2.png");
			
			Vacante v3 = new Vacante();
			v3.setId(3);
			v3.setNombre("Ingeniero Ambiental");
			v3.setDescripcion("Se solicita ingeniero para validar temas ambientales");
			v3.setFecha(sdf.parse("12-08-2015"));
			v3.setSalario(5000.00);
			v3.setDestacado(0);
			
			lista.add(v1);
			lista.add(v2);
			lista.add(v3);
		} catch (ParseException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return lista;		
	}
	
}
