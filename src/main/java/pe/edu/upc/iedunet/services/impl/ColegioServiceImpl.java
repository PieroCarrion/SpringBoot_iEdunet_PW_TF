package pe.edu.upc.iedunet.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import pe.edu.upc.iedunet.models.entities.Colegio;

import pe.edu.upc.iedunet.models.repositories.ColegioRepository;
import pe.edu.upc.iedunet.services.ColegioService;

@Service
public class ColegioServiceImpl implements ColegioService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ColegioRepository colegioRepository;
	
	@Transactional
	@Override
	public Colegio save(Colegio entity) throws Exception {
		return colegioRepository.save(entity);
	}

	@Transactional
	@Override
	public Colegio update(Colegio entity) throws Exception {
		return colegioRepository.save(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		colegioRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Colegio> findById(Integer id) throws Exception {
		return colegioRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Colegio> findAll() throws Exception {
		return colegioRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Optional<Colegio> findByCodigo(String codigo) throws Exception{
		return colegioRepository.findByCodigo(codigo);
	}

}