package pe.edu.upc.iedunet.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.iedunet.models.entities.Periodo;
import pe.edu.upc.iedunet.models.repositories.PeriodoRepository;
import pe.edu.upc.iedunet.services.PeriodoService;

@Service
public class PeriodoServiceImpl implements PeriodoService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PeriodoRepository periodoRepository;
	
	@Transactional
	@Override
	public Periodo save(Periodo entity) throws Exception {
		return periodoRepository.save(entity);
	}

	@Transactional
	@Override
	public Periodo update(Periodo entity) throws Exception {
		return periodoRepository.save(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		periodoRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Periodo> findById(Integer id) throws Exception {
		return periodoRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Periodo> findAll() throws Exception {
		return periodoRepository.findAll();
	}
	@Transactional(readOnly = true)
	@Override
	public List<Periodo> findByColegio(int id) throws Exception {
		return periodoRepository.findByColegio(id);
	}


}
