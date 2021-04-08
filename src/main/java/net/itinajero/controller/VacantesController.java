package net.itinajero.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.itinajero.iservice.ICategoriasService;
import net.itinajero.iservice.IVacantesService;
import net.itinajero.model.Vacante;
import net.itinajero.repository.VacantesRepository;
import net.itinajero.util.Utileria;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {
	
	@Value("${empleosapp.ruta.imagenes}")
	private String ruta;

	@Autowired
	private IVacantesService serviceVacante;
	@Autowired
	@Qualifier("categoriasServiceJpa")
	private ICategoriasService serviceCategoria;

	@GetMapping("/view/{id}")
	public String verDetalle(@PathVariable("id") int idVacante, Model model) {
		Vacante vacante = serviceVacante.buscarPorId(idVacante);
		model.addAttribute("vacante", vacante);
		return "detalle";
	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idVacante, RedirectAttributes attributes) {
		System.out.println("Vacante eliminada con ID: " + idVacante);
		serviceVacante.eliminar(idVacante);
		
		attributes.addFlashAttribute("msg", "La vacante fué eliminada");
		return "redirect:/vacantes/index";
	}
	
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idVacante, Model model) {
		Vacante vacante = serviceVacante.buscarPorId(idVacante);
		model.addAttribute("vacante", vacante);
		return "vacantes/formVacante";
	}

	@GetMapping("/create")
	public String crear(Vacante vacante) {
		return "vacantes/formVacante";
	}

	/*
	 * @PostMapping("/save") public String guardar(@RequestParam("nombre") String
	 * nombre, @RequestParam("descripcion") String descripcion,
	 * 
	 * @RequestParam("categoria") String categoria,@RequestParam("estatus") String
	 * estatus,
	 * 
	 * @RequestParam("fecha") String fecha, @RequestParam("destacado") int
	 * destacado,
	 * 
	 * @RequestParam("salario") double salario, @RequestParam("detalles") String
	 * detalles) { System.out.println("Nombre: " + nombre);
	 * System.out.println("Descripcion: " + descripcion);
	 * System.out.println("Categoria: " + categoria); System.out.println("Estatus: "
	 * + estatus); System.out.println("Fecha: " + fecha);
	 * System.out.println("Destacado: " + destacado); System.out.println("Salario: "
	 * + salario); System.out.println("Detalles: " + detalles); return
	 * "vacantes/listVacantes"; }
	 */

	@PostMapping("/save")
	public String guardar(Vacante vacante, BindingResult result, RedirectAttributes attributes,
			@RequestParam("archivoImagen") MultipartFile multiPart) {
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			return "vacantes/formVacante";
		}
		if (!multiPart.isEmpty()) {
			//String ruta = "/empleos/img-vacantes/"; // Linux/MAC
			//String ruta = "F:/java-projects/spring-tools/img-empleos/"; // Windows
			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreImagen != null) { // La imagen si se subio
				// Procesamos la variable nombreImagen
				vacante.setImagen(nombreImagen);
			}
		}
		System.out.println(vacante);
		serviceVacante.guardar(vacante);
		attributes.addFlashAttribute("msg", "Registro guardado");
		return "redirect:/vacantes/index";
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Vacante> vacantes = serviceVacante.buscarTodas();
		model.addAttribute("vacantes", vacantes);
		return "vacantes/listVacantes";
	}
	
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Vacante> lista = serviceVacante.buscarTodas(page);
		model.addAttribute("vacantes", lista);
		return "vacantes/listVacantes";
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("categorias", serviceCategoria.buscarTodas());
		model.addAttribute("vacan", new Vacante());
	}

}
