
package pe.edu.upc.iedunet.services;

import java.util.List;

import pe.edu.upc.iedunet.models.entities.Profesor;
import pe.edu.upc.iedunet.viewmodels.CursoViewModel;
import pe.edu.upc.iedunet.viewmodels.HorarioViewModel;
import pe.edu.upc.iedunet.models.entities.Comunicado;

public interface ProfesorService extends CrudService<Profesor, Integer>{
	List<Profesor> findByColegio(int id) throws Exception;
	List<HorarioViewModel> findHorario(int id) throws Exception;
	CursoViewModel findCurso(int idProfesor, int idCurso) throws Exception;
	Comunicado saveComunicado(Comunicado comunicado) throws Exception;
}
