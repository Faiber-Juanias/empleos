package net.itinajero.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.itinajero.iservice.IUsuariosService;
import net.itinajero.model.Usuario;
import net.itinajero.repository.UsuariosRepository;

@Service
public class UsuariosServiceJpa implements IUsuariosService {

	@Autowired
	private UsuariosRepository repoUsuario;
	
	@Override
	public void guardar(Usuario usuario) {
		repoUsuario.save(usuario);
	}

	@Override
	public void eliminar(Integer idUsuario) {
		repoUsuario.deleteById(idUsuario);
	}

	@Override
	public List<Usuario> buscarTodos() {
		List<Usuario> listUsuarios = repoUsuario.findAll();
		return listUsuarios;
	}

	@Override
	public Usuario buscarPorUsername(String username) {
		return repoUsuario.findByUsername(username);
	}

}
