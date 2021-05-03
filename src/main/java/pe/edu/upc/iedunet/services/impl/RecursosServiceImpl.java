package pe.edu.upc.iedunet.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.iedunet.models.entities.Recurso;
import pe.edu.upc.iedunet.models.repositories.ClaseRepository;
import pe.edu.upc.iedunet.models.repositories.RecursoRepository;
import pe.edu.upc.iedunet.services.RecursosService;

@Service
public class RecursosServiceImpl implements RecursosService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private RecursoRepository recursoRepository;
	@Autowired
	private ClaseRepository claseRepository;
	
	@Transactional
	@Override
	public Recurso save(Recurso entity) throws Exception {
		return recursoRepository.save(entity);
	}

	@Transactional
	@Override
	public Recurso update(Recurso entity) throws Exception {
		return recursoRepository.save(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		recursoRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Recurso> findById(Integer id) throws Exception {
		return recursoRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Recurso> findAll() throws Exception {
		return recursoRepository.findAll();
	}
	@Transactional(readOnly = true)
	@Override
	public List<Recurso> findAllByClase(int idClase) throws Exception {
		return  claseRepository.findById(idClase).get().getRecursos();
	}
	
}
