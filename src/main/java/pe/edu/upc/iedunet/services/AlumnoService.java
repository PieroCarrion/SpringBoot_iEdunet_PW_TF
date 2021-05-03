package pe.edu.upc.iedunet.services;

import java.util.List;

import pe.edu.upc.iedunet.models.entities.Alumno;
import pe.edu.upc.iedunet.models.entities.Curso;
import pe.edu.upc.iedunet.viewmodels.CursoViewModel;
import pe.edu.upc.iedunet.viewmodels.HorarioViewModel;

public interface AlumnoService extends CrudService<Alumno, Integer> {
	List<Alumno> findByColegio(int id) throws Exception;
	List<HorarioViewModel> findHorario(int id) throws Exception;
	List<CursoViewModel> findCursos(int id) throws Exception;
	CursoViewModel findCurso(int idAlumno, int idCurso) throws Exception;
	String findLinkClase(int idAlumno,int idCurso) throws Exception;
}