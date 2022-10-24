package net.itinajero.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import net.itinajero.iservice.ICategoriasService;
import net.itinajero.iservice.IUsuariosService;
import net.itinajero.iservice.IVacantesService;
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
	
	@GetMapping("/index")
	public String mostrarIndex(Authentication auth, HttpSession session) {
		String username = auth.getName();
		if (session.getAttribute("usuario") == null) {
			Usuario usuario = serviceUsuario.buscarPorUsername(username);
			usuario.setPassword(null);
			session.setAttribute("usuario", usuario);			
		}
		return "redirect:/";
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
		Vacante vacante = new Vacante();
		vacante.reseteaImagen();
		model.addAttribute("vacantes", serviceVacante.buscarDestacadas());
		model.addAttribute("categorias", serviceCategoria.buscarTodas());
		model.addAttribute("search", vacante);
	}
	
	@GetMapping("/search")
	public String buscar(@ModelAttribute("search") Vacante vacante, Model model) {
		
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());
		
		Example<Vacante> example = Example.of(vacante, matcher);
		List<Vacante> lista = serviceVacante.buscarByExample(example);
		
		model.addAttribute("vacantes", lista);
		return "home";
	}
	
	/**
	 * Si detecta valores vacios en el modelo, los convierte a NULL
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
}
