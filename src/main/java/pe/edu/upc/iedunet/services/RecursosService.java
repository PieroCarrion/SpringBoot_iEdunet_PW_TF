package pe.edu.upc.iedunet.services;

import java.util.List;

import pe.edu.upc.iedunet.models.entities.Recurso;

public interface RecursosService extends CrudService<Recurso, Integer> {
	List<Recurso> findAllByClase(int idClase) throws Exception;
}
