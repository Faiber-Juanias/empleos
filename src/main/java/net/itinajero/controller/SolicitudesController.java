package net.itinajero.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

import net.itinajero.iservice.ISolicitudesService;
import net.itinajero.iservice.IVacantesService;
import net.itinajero.model.Solicitud;
import net.itinajero.model.Usuario;
import net.itinajero.model.Vacante;
import net.itinajero.util.Utileria;

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
	public String crear(Solicitud solicitud, @PathVariable("idVacante") int idVacante, Model model, HttpSession session) {
		session.setAttribute("id_vacante_ap_solicitud", idVacante);
		model.addAttribute("vacante", serviceVacante.buscarPorId(idVacante));
		return "solicitudes/formSolicitud";
	}
	
	@PostMapping("/save")
	public String guardar(Solicitud solicitud, BindingResult result, @RequestParam("archivoCV") MultipartFile multiPart, RedirectAttributes attributes, HttpSession session) {
		if (result.hasErrors()) {
			return "solicitudes/formSolicitud";
		}
		if (!multiPart.isEmpty()) {
			String nameFile = Utileria.guardarArchivo(multiPart, ruta);
			if (nameFile != null) {
				solicitud.setArchivo(nameFile);
			}
		} else {
			solicitud.setArchivo("");
		}
		Vacante v = new Vacante();
		v.setId((int)session.getAttribute("id_vacante_ap_solicitud"));
		Usuario u = new Usuario();
		u.setId(((Usuario)session.getAttribute("usuario")).getId());
		solicitud.setVacante(v);
		solicitud.setUsuario(u);
		solicitud.setFecha(new Date());
		System.out.println("Solicitud: " + solicitud);
		serviceSolicitud.guardar(solicitud);
		attributes.addFlashAttribute("msg", "Vacante aplicada correctamente.");
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
