package net.itinajero.iservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.itinajero.model.Usuario;

public interface IUsuariosService {

	void guardar(Usuario usuario);
	void eliminar(Integer idUsuario);
	Page<Usuario> buscarTodos(Pageable page);
	Usuario buscarPorUsername(String username);
	
}
