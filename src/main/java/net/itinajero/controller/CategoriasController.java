package net.itinajero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.itinajero.iservice.ICategoriasService;
import net.itinajero.model.Categoria;

@Controller
@RequestMapping(value="/categorias")
public class CategoriasController {
	
	@Autowired
	@Qualifier("categoriasServiceJpa")
	public ICategoriasService serviceCategoria;
	
	@RequestMapping(value = "/indexPaginate", method = RequestMethod.GET)
	public String mostrarIndexPaginado(Model model, Pageable page) {
		model.addAttribute("categorias", serviceCategoria.buscarTodas(page));
		return "categorias/listCategorias";
	}
	
	// @GetMapping("/create")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String crear(Categoria categoria) {
		return "categorias/formCategoria";
	}
	// @PostMapping("/save")
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String guardar(Categoria categoria, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			for (ObjectError error: result.getAllErrors()){
				System.out.println("Ocurrio un error: "+ error.getDefaultMessage());
			}
			return "categorias/formCategoria";
		}
		serviceCategoria.guardar(categoria);
		attributes.addFlashAttribute("msg", "Registro guardado");
		return "redirect:/categorias/indexPaginate";
	}
	
	@RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
	public String editar(@PathVariable("id") int idCategoria, Model model) {
		model.addAttribute("categoria", serviceCategoria.buscarPorId(idCategoria));
		return "categorias/formCategoria";
	}
	
	@RequestMapping(value = "/eliminar/{id}", method = RequestMethod.GET)
	public String eliminar(@PathVariable("id") int idCategoria, RedirectAttributes attributes) {
		serviceCategoria.eliminar(idCategoria);
		attributes.addFlashAttribute("msg", "Registro eliminado");
		return "redirect:/categorias/indexPaginate";
	}
	
	
	@ModelAttribute
	public void setGenericos(Model model) {
		
	}
	
}
