package net.itinajero.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import net.itinajero.iservice.IVacantesService;
import net.itinajero.model.Vacante;

@Service
public class VacantesServiceImpl implements IVacantesService {

	private List<Vacante> lista = null;
	
	public VacantesServiceImpl() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		lista = new LinkedList<>();
		
		try {
			Vacante v1 = new Vacante();
			v1.setId(1);
			v1.setNombre("Ingeniero de Comunicaciones");
			v1.setDescripcion("Se solicita ingeniero para dar soporte a Intranet");
			v1.setFecha(sdf.parse("12-05-2010"));
			v1.setSalario(9900.0);
			v1.setDestacado(1);
			v1.setImagen("empresa1.png");
			v1.setEstatus("Creada");
			
			Vacante v2 = new Vacante();
			v2.setId(2);
			v2.setNombre("Ingeniero de Sistemas");
			v2.setDescripcion("Se solicita ingeniero para crear aplicación web");
			v2.setFecha(sdf.parse("09-03-2013"));
			v2.setSalario(10000.00);
			v2.setDestacado(0);
			v2.setImagen("empresa2.png");
			v2.setEstatus("Aprobada");
			
			Vacante v3 = new Vacante();
			v3.setId(3);
			v3.setNombre("Ingeniero Ambiental");
			v3.setDescripcion("Se solicita ingeniero para validar temas ambientales");
			v3.setFecha(sdf.parse("12-08-2015"));
			v3.setSalario(5000.00);
			v3.setDestacado(0);
			v3.setEstatus("Eliminada");
			
			lista.add(v1);
			lista.add(v2);
			lista.add(v3);
		} catch (ParseException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	@Override
	public List<Vacante> buscarTodas() {
		return lista;
	}

	@Override
	public Vacante buscarPorId(Integer idVacante) {
		for (Vacante v : lista) {
			if (v.getId() == idVacante) {
				return v;
			}
		}
		return null;
	}

	@Override
	public void guardar(Vacante vacante) {
		lista.add(vacante);
	}

	@Override
	public List<Vacante> buscarDestacadas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example) {
		// TODO Auto-generated method stub
		return null;
	}

}
