package net.itinajero.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.itinajero.iservice.ISolicitudesService;
import net.itinajero.iservice.IVacantesService;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudesController {
	
	@Value("${empleosapp.ruta.cv}")
	private String ruta;
	
	@Autowired
	private ISolicitudesService serviceSolicitud;
	
	@Autowired
	private IVacantesService serviceVacante;
	
	@GetMapping("/index") 
	public String mostrarIndex() {
		return "solicitudes/listSolicitudes";
	}
	
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		model.addAttribute("solicitudes", serviceSolicitud.buscarTodas(page));
		return "solicitudes/listSolicitudes";
	}
	
	@GetMapping("/create/{idVacante}")
	public String crear(@PathVariable("idVacante") int idVacante, Model model) {
		model.addAttribute("vacante", serviceVacante.buscarPorId(idVacante));
		return "solicitudes/formSolicitud";
	}
	
	@PostMapping("/save")
	public String guardar() {
		return "redirect:/";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar() {
		return "redirect:/solicitudes/indexPaginate";
	}
	
	@ModelAttribute
	public void modelAttribute(Model model) {
		model.addAttribute("solicitudes", serviceSolicitud.buscarTodas());
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
