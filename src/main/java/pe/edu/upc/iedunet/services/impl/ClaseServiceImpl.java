package pe.edu.upc.iedunet.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.iedunet.models.entities.ActividadAcademica;
import pe.edu.upc.iedunet.models.entities.Alumno;
import pe.edu.upc.iedunet.models.entities.AlumnoClase;
import pe.edu.upc.iedunet.models.entities.Calificacion;
import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.models.entities.Comunicado;
import pe.edu.upc.iedunet.models.entities.Curso;
import pe.edu.upc.iedunet.models.repositories.ActividadAcademicaRepository;
import pe.edu.upc.iedunet.models.repositories.AlumnoClaseRepository;
import pe.edu.upc.iedunet.models.repositories.AlumnoRepository;
import pe.edu.upc.iedunet.models.repositories.ClaseRepository;
import pe.edu.upc.iedunet.models.repositories.ComunicadoRepository;
import pe.edu.upc.iedunet.services.ClaseService;
import pe.edu.upc.iedunet.viewmodels.CalificacionViewModel;
import pe.edu.upc.iedunet.viewmodels.CursoViewModel;

@Service
public class ClaseServiceImpl implements ClaseService, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ClaseRepository claseRepository;
	@Autowired
	private AlumnoClaseRepository alumnoClaseRepository;
	@Autowired
	private AlumnoRepository alumnoRepository;

	@Autowired
	private ActividadAcademicaRepository actividadAcademicaRepository;
	
	@Autowired
	private ComunicadoRepository comunicadoRepository;

	@Transactional
	@Override
	public Clase save(Clase entity) throws Exception {
		return claseRepository.save(entity);
	}

	@Transactional
	@Override
	public Clase update(Clase entity) throws Exception {
		return claseRepository.save(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		claseRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Clase> findById(Integer id) throws Exception {
		return claseRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Clase> findAll() throws Exception {
		return claseRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public List<CalificacionViewModel> findAlumnosWithExams(int id) throws Exception {

		List<CalificacionViewModel> calificaciones = new ArrayList<>();
		ActividadAcademica actividadAcademica = actividadAcademicaRepository.findById(id).get();
		Clase clase = actividadAcademica.getClase();

		// clase.setId();
		List<AlumnoClase> alumnoClases = alumnoClaseRepository.findByClase(clase);
		List<Alumno> alumnosT = new ArrayList<>();
		for (AlumnoClase x : alumnoClases) {
			alumnosT.add(alumnoRepository.findById(x.getAlumno().getId()).get());
		}
		System.err.println(alumnosT.size());

		List<Calificacion> notas = actividadAcademica.getCalificaciones();
		for (Alumno y : alumnosT) {
			System.err.println(y.getUsuario().getApellido());
			CalificacionViewModel calificacionViewModel = new CalificacionViewModel();

			calificacionViewModel.setCodigoAlumno(y.getUsuario().getCodigoUsuario());
			calificacionViewModel.setNombre(y.getUsuario().getNombre());
			calificacionViewModel.setApellido(y.getUsuario().getApellido());
			calificacionViewModel.setIdClase(clase.getId());
			calificacionViewModel.setIdAlumno(y.getId());
			calificacionViewModel.setIdExamen(actividadAcademica.getId());
			for (Calificacion nota : notas) {

				if (nota.getAlumno().getId() == y.getId()) {
					calificacionViewModel.setNota(nota.getNota());
					calificacionViewModel.setIdCalificacion(nota.getId());
					
				}
			}
			System.err.println("id calificacion"+calificacionViewModel.getIdCalificacion());
			calificaciones.add(calificacionViewModel);
			System.err.println(calificacionViewModel.getApellido() + " " + calificacionViewModel.getNota());
		}

		return calificaciones;
	}
	
	
	
	
	
	@Transactional(readOnly = true)
	@Override
	public List<CalificacionViewModel> findAlumnosWithTareas(int id) throws Exception {

		List<CalificacionViewModel> calificaciones = new ArrayList<>();
		ActividadAcademica actividadAcademica = actividadAcademicaRepository.findById(id).get();
		Clase clase = actividadAcademica.getClase();

		// clase.setId();
		List<AlumnoClase> alumnoClases = alumnoClaseRepository.findByClase(clase);
		List<Alumno> alumnosT = new ArrayList<>();
		for (AlumnoClase x : alumnoClases) {
			alumnosT.add(alumnoRepository.findById(x.getAlumno().getId()).get());
		}
		System.err.println(alumnosT.size());

		List<Calificacion> notas = actividadAcademica.getCalificaciones();
		for (Alumno y : alumnosT) {
			System.err.println(y.getUsuario().getApellido());
			CalificacionViewModel calificacionViewModel = new CalificacionViewModel();

			calificacionViewModel.setCodigoAlumno(y.getUsuario().getCodigoUsuario());
			calificacionViewModel.setNombre(y.getUsuario().getNombre());
			calificacionViewModel.setApellido(y.getUsuario().getApellido());
			calificacionViewModel.setIdClase(clase.getId());
			calificacionViewModel.setIdAlumno(y.getId());
			calificacionViewModel.setIdExamen(actividadAcademica.getId());
			for (Calificacion nota : notas) {

				if (nota.getAlumno().getId() == y.getId()) {
					calificacionViewModel.setNota(nota.getNota());
					calificacionViewModel.setIdCalificacion(nota.getId());
					
				}
			}
			System.err.println("id calificacion"+calificacionViewModel.getIdCalificacion());
			calificaciones.add(calificacionViewModel);
			System.err.println(calificacionViewModel.getApellido() + " " + calificacionViewModel.getNota());
		}

		return calificaciones;
	}
	
	
	
	

	@Override
	public List<Comunicado> findComunicadoByClase(int idClase) {
		Clase clase = claseRepository.findById(idClase).get();
		// TODO Auto-generated method stub
		List<Comunicado> listaComunicados =comunicadoRepository.findByClaseOrderByFechaDesc(clase);
		
		for (Comunicado comunicado : listaComunicados) {
			System.err.println("Comunicado "+ comunicado.getId() + " : " + comunicado.getDescripcion() + " de la clase " + comunicado.getClase().getId() + " FECHA: "+ comunicado.getFecha());
		}
		
		return listaComunicados;
	}

}
