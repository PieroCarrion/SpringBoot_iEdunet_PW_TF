package pe.edu.upc.iedunet.services;

import java.util.List;
import pe.edu.upc.iedunet.models.entities.ActividadAcademica;
import pe.edu.upc.iedunet.models.entities.Calificacion;
import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.models.repositories.CalificacionRepository;
import pe.edu.upc.iedunet.viewmodels.CalificacionViewModel;
import pe.edu.upc.iedunet.viewmodels.ExamenViewModel;
import pe.edu.upc.iedunet.viewmodels.ReporteViewModel;
import pe.edu.upc.iedunet.viewmodels.TareaViewModel;


public interface ActividadAcademicaService extends CrudService<ActividadAcademica, Integer> {
	public ExamenViewModel findExam(Integer id) throws Exception;
	public List<ExamenViewModel> findExamsByClase(Integer id) throws Exception;
	public List<TareaViewModel> findHomeworksByClase(Integer id) throws Exception;
	public List<ExamenViewModel> findAllExams(Integer id) throws Exception;
	public List<TareaViewModel> findAllTareas(Integer id) throws Exception;
	public void save(Calificacion tareas);
	public Calificacion saveCalificacion(CalificacionViewModel calificacionViewModel) throws Exception;
	public List<TareaViewModel> findAllHomeworksByClase(Integer id) throws Exception;
	public List<ReporteViewModel> findAAbyClase(Integer id) throws Exception;
	public List<ActividadAcademica> findByClase(Integer id) throws Exception;
	public List<ReporteViewModel> findByCalificacionAlumno(Integer idAlumno, Integer idActividad) throws Exception;
	public CalificacionRepository getCalificacionRepository() throws Exception;
}
