package net.itinajero.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.itinajero.iservice.ICategoriasService;
import net.itinajero.iservice.IUsuariosService;
import net.itinajero.iservice.IVacantesService;
import net.itinajero.model.Categoria;
import net.itinajero.model.Perfil;
import net.itinajero.model.Usuario;
import net.itinajero.model.Vacante;

@Controller
public class HomeController {
	
	@Autowired
	private IVacantesService serviceVacante;
	@Autowired
	private IUsuariosService serviceUsuario;
	@Autowired
	private ICategoriasService serviceCategoria;

	@GetMapping("/")
	public String mostrarHome() {
		return "home";
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
		Vacante vacante = new Vacante();
		vacante.reseteaImagen();
		model.addAttribute("vacantes", serviceVacante.buscarDestacadas());
		model.addAttribute("categorias", serviceCategoria.buscarTodas());
		model.addAttribute("search", vacante);
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
	public String mostrarTabla(Model model) {
		List<Vacante> lista = serviceVacante.buscarTodas();
		model.addAttribute("vacantes", lista);
		return "tabla"; 
	}
	
	@GetMapping("/signup")
	public String registrarse(Usuario usuario) {
		return "usuarios/formRegistro";
	}
	
	@PostMapping("/signup")
	public String guardarRegistro(Usuario usuario, RedirectAttributes attributes) {
		System.out.println("Usuario: " + usuario);
		Perfil perfil = new Perfil();
		perfil.setId(3);
		
		usuario.setEstatus(1);
		usuario.setFechaRegistro(new Date());
		usuario.agregar(perfil);
		
		serviceUsuario.guardar(usuario);
		attributes.addFlashAttribute("msg", "Usuario creado");
		return "redirect:/usuarios/index";
	}
	
	@GetMapping("/search")
	public String buscar(@ModelAttribute("search") Vacante vacante, Model model) {
		System.out.println("Buscando por: " + vacante);
		
		Example<Vacante> example = Example.of(vacante);
		List<Vacante> lista = serviceVacante.buscarByExample(example);
		model.addAttribute("vacantes", lista);
		
		return "home";
	}
	
}
