package net.itinajero.iservice;

import java.util.List;

import org.springframework.data.domain.Example;

import net.itinajero.model.Vacante;

public interface IVacantesService {

	List<Vacante> buscarTodas();
	Vacante buscarPorId(Integer id);
	void guardar(Vacante vacante);
	List<Vacante> buscarDestacadas();
	void eliminar(Integer id);
	List<Vacante> buscarByExample(Example<Vacante> example);
	
}
