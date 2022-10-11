package net.itinajero.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.itinajero.iservice.IVacantesService;
import net.itinajero.model.Vacante;
import net.itinajero.repository.VacantesRepository;

@Service
@Primary
public class VacantesServiceJpa implements IVacantesService {
	
	@Autowired
	private VacantesRepository vacantesRepo;

	@Override
	public List<Vacante> buscarTodas() {
		return vacantesRepo.findAll();
	}

	@Override
	public Vacante buscarPorId(Integer id) {
		Optional<Vacante> optional = vacantesRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public void guardar(Vacante vacante) {
		vacantesRepo.save(vacante);
	}

	@Override
	public List<Vacante> buscarDestacadas() {
		return vacantesRepo.findByDestacado(1);
	}

	@Override
	public void eliminar(Integer id) {
		vacantesRepo.deleteById(id);
	}

	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example) {
		return vacantesRepo.findAll(example);
	}

	@Override
	public Page<Vacante> buscarTodas(Pageable page) {
		return vacantesRepo.findAll(page);
	}

}
