package net.itinajero.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.itinajero.iservice.IUsuariosService;
import net.itinajero.model.Perfil;
import net.itinajero.model.Usuario;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private IUsuariosService serviceUsuario;
	
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		model.addAttribute("usuarios", serviceUsuario.buscarTodos(page));
		return "usuarios/listUsuarios";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {
		serviceUsuario.eliminar(idUsuario);
		attributes.addFlashAttribute("msg", "Usuario eliminado");
		return "redirect:/usuarios/indexPaginate";
	}
	
	@GetMapping("/create")
	public String crear(Usuario usuario) {
		return "usuarios/formRegistro";
	}
	
	@PostMapping("/save")
	public String guardar(Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			return "usuarios/formRegistro";
		}
		Perfil perfil = new Perfil();
		perfil.setId(3);
		usuario.agregar(perfil);
		usuario.setEstatus(1);
		usuario.setFechaRegistro(new Date());
		serviceUsuario.guardar(usuario);
		attributes.addFlashAttribute("msg", "Registro guardado");
		return "redirect:/usuarios/indexPaginate";
	}
	
}
