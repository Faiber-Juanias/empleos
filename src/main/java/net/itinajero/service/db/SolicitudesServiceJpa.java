package net.itinajero.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.itinajero.iservice.ISolicitudesService;
import net.itinajero.model.Solicitud;
import net.itinajero.repository.SolicitudesRepository;

@Service
public class SolicitudesServiceJpa implements ISolicitudesService {
	
	@Autowired
	private SolicitudesRepository solicitudesRepository;

	@Override
	public void guardar(Solicitud solicitud) {
		solicitudesRepository.save(solicitud);
	}

	@Override
	public void eliminar(Integer idSolicitud) {
		solicitudesRepository.deleteById(idSolicitud);
	}

	@Override
	public List<Solicitud> buscarTodas() {
		return solicitudesRepository.findAll();
	}

	@Override
	public Solicitud buscarPorId(Integer idSolicitud) {
		Optional<Solicitud> solicitud = solicitudesRepository.findById(idSolicitud);
		if (solicitud.isPresent()) {
			return solicitud.get();
		}
		return null;
	}

	@Override
	public Page<Solicitud> buscarTodas(Pageable page) {
		return solicitudesRepository.findAll(page);
	}

}
