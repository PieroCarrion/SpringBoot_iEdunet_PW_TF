package pe.edu.upc.iedunet.services;

import java.util.List;

import pe.edu.upc.iedunet.models.entities.Alumno;
import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.models.entities.Comunicado;
import pe.edu.upc.iedunet.viewmodels.CalificacionViewModel;

public interface ClaseService extends CrudService<Clase, Integer> {
	List<CalificacionViewModel> findAlumnosWithExams(int id) throws Exception;
	List<CalificacionViewModel> findAlumnosWithTareas(int id) throws Exception;
	List<Comunicado> findComunicadoByClase(int idClase);
}
