package net.itinajero.iservice;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.itinajero.model.Categoria;

public interface ICategoriasService {

	void guardar(Categoria categoria);
	Categoria buscarPorId(Integer idCategoria);
	void eliminar(Integer idCategoria);
	Page<Categoria> buscarTodas(Pageable page);
	List<Categoria> buscarTodas();
	
}
