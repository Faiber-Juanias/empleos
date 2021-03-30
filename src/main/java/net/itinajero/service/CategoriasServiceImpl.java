package net.itinajero.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.itinajero.iservice.ICategoriasService;
import net.itinajero.model.Categoria;

@Service
public class CategoriasServiceImpl implements ICategoriasService {

	List<Categoria> listaCategorias = null;
	
	public CategoriasServiceImpl() {
		listaCategorias = new LinkedList<>();
		
		Categoria ca1 = new Categoria();
		ca1.setId(1);
		ca1.setNombre("Ventas");
		ca1.setDescripcion("Departamento de Ventas");
		
		Categoria ca2 = new Categoria();
		ca2.setId(2);
		ca2.setNombre("Contabilidad");
		ca2.setDescripcion("Departamento de Contabilidad");
		
		Categoria ca3 = new Categoria();
		ca3.setId(3);
		ca3.setNombre("Transporte");
		ca3.setDescripcion("Departamento de Transporte");
		
		Categoria ca4 = new Categoria();
		ca4.setId(4);
		ca4.setNombre("Informatica");
		ca4.setDescripcion("Departamento de Informatica");
		
		Categoria ca5 = new Categoria();
		ca5.setId(5);
		ca5.setNombre("Construccion");
		ca5.setDescripcion("Departamento de Construccion");
		
		listaCategorias.add(ca1);
		listaCategorias.add(ca2);
		listaCategorias.add(ca3);
		listaCategorias.add(ca4);
		listaCategorias.add(ca5);		
		
	}
	
	@Override
	public void guardar(Categoria categoria) {
		listaCategorias.add(categoria);
		System.out.println("Categoría guardada");
	}

	@Override
	public List<Categoria> buscarTodas() {
		return listaCategorias;
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		for (Categoria cat : listaCategorias) {
			if (cat.getId() == idCategoria) {
				return cat;
			}
		}
		return null;
	}

	@Override
	public void eliminar(Integer idCategoria) {
		// TODO Auto-generated method stub
		
	}

}
