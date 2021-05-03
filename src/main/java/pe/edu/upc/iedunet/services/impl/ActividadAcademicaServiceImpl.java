package pe.edu.upc.iedunet.services.impl;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import pe.edu.upc.iedunet.helpers.HourFormatter;
import pe.edu.upc.iedunet.models.entities.ActividadAcademica;

import pe.edu.upc.iedunet.models.entities.Alumno;

import pe.edu.upc.iedunet.models.entities.Calificacion;
import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.models.repositories.ActividadAcademicaRepository;
import pe.edu.upc.iedunet.models.repositories.AlumnoRepository;
import pe.edu.upc.iedunet.models.repositories.CalificacionRepository;
import pe.edu.upc.iedunet.models.repositories.ClaseRepository;
import pe.edu.upc.iedunet.models.repositories.UsuarioRepository;
import pe.edu.upc.iedunet.services.ActividadAcademicaService;
import pe.edu.upc.iedunet.viewmodels.CalificacionViewModel;
import pe.edu.upc.iedunet.viewmodels.ExamenViewModel;
import pe.edu.upc.iedunet.viewmodels.ReporteViewModel;
import pe.edu.upc.iedunet.viewmodels.TareaViewModel;

@Service
public class ActividadAcademicaServiceImpl implements ActividadAcademicaService, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ActividadAcademicaRepository actividadAcademicaRepository;

	
	@Autowired
	private AlumnoRepository alumnoRepository;


	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ClaseRepository claseRepository;

	@Autowired
	private CalificacionRepository calificacionRepository;

	@Transactional
	@Override
	public ActividadAcademica save(ActividadAcademica entity) throws Exception {
		return actividadAcademicaRepository.save(entity);
	}

	@Transactional
	@Override
	public ActividadAcademica update(ActividadAcademica entity) throws Exception {
		return actividadAcademicaRepository.save(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		actividadAcademicaRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<ActividadAcademica> findById(Integer id) throws Exception {
		return actividadAcademicaRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<ActividadAcademica> findAll() throws Exception {
		return actividadAcademicaRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public ExamenViewModel findExam(Integer id) throws Exception {
		int Espera = 10;
		HourFormatter hourFormatter = new HourFormatter();

		ActividadAcademica actividadAcademica = findById(id).get();
		Clase clase = actividadAcademica.getClase();

		// Obtengo los string de la descripcion de la DB (Tipo#Link#Nombre#Hora)
		String[] descr = actividadAcademica.getDescripcion().split("#");

		// Obtengo la info referente a la fecha y hora del examen y actual
		Date fechaExamen = (Date) actividadAcademica.getFechaMaxEntrega();
		java.util.Date fechaActual = new java.util.Date();
		int HoraExamen = hourFormatter.HourToNumber(descr[3]);
		int HoraActual = fechaActual.getHours() * 100 + fechaActual.getMinutes();

		// Realizo las validaciones respectivas para ver si se puede dar el examen
		if (descr[0].equals("Exam")) {
			if (fechaExamen.getYear() == fechaActual.getYear() && fechaExamen.getMonth() == fechaActual.getMonth()
					&& fechaExamen.getDate() == fechaActual.getDate()) {
				ExamenViewModel examen = new ExamenViewModel();
				examen.setId(actividadAcademica.getId());
				examen.setCurso(clase.getCurso().getNombre());
				examen.setGrado(clase.getGrado());
				examen.setFechaExamen(fechaExamen);
				examen.setIdCurso(clase.getCurso().getId());

				examen.setHora(descr[3]);
				if (HoraActual < HoraExamen) {// Si estoy entrando antes de la hora
					examen.setLink("");
					examen.setDescripcion("Aún no es la hora del examen");
				} else if (HoraActual >= HoraExamen && HoraActual <= HoraExamen + Espera) {// Si estoy entrando a la
																							// hora o {espera} min tarde
					examen.setLink(descr[1]);
					examen.setDescripcion(descr[2]);
				} else {// Si es que he entrado mas tarde de lo debido
					examen.setLink("");
					examen.setDescripcion("Usted ha ingresado muy tarde a la evaluación");
				}

				return examen;
			} else {
				ExamenViewModel examen = new ExamenViewModel();
				examen.setId(actividadAcademica.getId());
				examen.setCurso(clase.getCurso().getNombre());
				examen.setHora(descr[3]);
				examen.setGrado(clase.getGrado());
				examen.setFechaExamen(fechaExamen);
				examen.setDescripcion(descr[2] + " *EXAMEN FUERA DE FECHA*");
				examen.setLink("");

				return examen;
			}
		} else {
			throw new Exception("Examen no existente");
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<ExamenViewModel> findAllExams(Integer id) throws Exception {
		List<ExamenViewModel> listAllExams = new ArrayList<ExamenViewModel>();

		Clase claseOwner = new Clase();
		claseOwner.setId(id);

		List<ActividadAcademica> allExams = actividadAcademicaRepository.findByClase(claseOwner);
		System.err.println(allExams.size() + "Tamaño" + allExams.get(0).getDescripcion());

		for (ActividadAcademica actividadAcademica : allExams) {
			String[] descr = actividadAcademica.getDescripcion().split("#");

			if (descr[0].equals("Exam")) {
				Date fechaExamen = (Date) actividadAcademica.getFechaMaxEntrega();

				ExamenViewModel examen = new ExamenViewModel();
				examen.setId(actividadAcademica.getId());
				examen.setCurso(actividadAcademica.getClase().getCurso().getNombre());
				examen.setGrado(actividadAcademica.getClase().getGrado());
				examen.setFechaExamen(fechaExamen);
				examen.setIdCurso(actividadAcademica.getClase().getCurso().getId());
				examen.setLink(descr[1]);
				examen.setDescripcion(descr[2]);
				listAllExams.add(examen);
			}
		}

		return listAllExams;

	}
	
	@Transactional(readOnly = true)
	@Override
	public List<TareaViewModel> findAllTareas(Integer id) throws Exception {
		List<TareaViewModel> listAllTareas = new ArrayList<TareaViewModel>();

		Clase claseOwner = new Clase();
		claseOwner.setId(id);

		List<ActividadAcademica> allTareas = actividadAcademicaRepository.findByClase(claseOwner);
		//System.err.println(allExams.size() + "Tamaño" + allExams.get(0).getDescripcion());

		for (ActividadAcademica actividadAcademica : allTareas) {
			String[] descr = actividadAcademica.getDescripcion().split("#");

			if (descr[0].equals("HW")) {
				Date fechaMax = (Date) actividadAcademica.getFechaMaxEntrega();
				Date fechaPubli = (Date) actividadAcademica.getFechaPublicacion();
				TareaViewModel tarea = new TareaViewModel();
				tarea.setId(actividadAcademica.getId());
				tarea.setCurso(actividadAcademica.getClase().getCurso().getNombre());
				tarea.setGrado(actividadAcademica.getClase().getGrado());
				tarea.setFechaMaxEntrega(fechaMax);
				tarea.setFechaPublicacion(fechaPubli);
				tarea.setIdClase(actividadAcademica.getClase().getCurso().getId());
				tarea.setLink(descr[1]);
				tarea.setDescripcion(descr[2]);
				tarea.setHora(descr[3]);
				listAllTareas.add(tarea);
			}
		}

		return listAllTareas;

	}

	@Transactional(readOnly = true)
	@Override
	public List<ExamenViewModel> findExamsByClase(Integer id) throws Exception {// *
		HourFormatter hourFormatter = new HourFormatter();

		Clase claseOwner = new Clase();
		claseOwner.setId(id);

		java.util.Date fecha = new java.util.Date(); // fecha hoy

		// List<ActividadAcademica> allExams =
		// actividadAcademicaRepository.findByClase(claseOwner);
		List<ActividadAcademica> allExams = actividadAcademicaRepository
				.findByClaseAndFechaMaxEntregaGreaterThanEqual(claseOwner, fecha);

		List<ExamenViewModel> listExamsOnTime = new ArrayList<ExamenViewModel>();

		for (ActividadAcademica actividadAcademica : allExams) {// RECORRO TODOS LOS EXAMENES A LA FECHA OBTENIDOS PARA
																// AGREGARLOS A SU RESPECTIVO VIEWMODEL
			String[] descr = actividadAcademica.getDescripcion().split("#");
			if (descr[0].equals("Exam")) {
				System.out.println(actividadAcademica.getDescripcion());

				ExamenViewModel examen = new ExamenViewModel();
				examen.setId(actividadAcademica.getId());
				examen.setFechaExamen((Date) actividadAcademica.getFechaMaxEntrega());
				examen.setHora(descr[3]);
				examen.setDescripcion(descr[2]);
				examen.setCurso(actividadAcademica.getClase().getCurso().getNombre());
				examen.setGrado(actividadAcademica.getClase().getGrado());
				// examen.setIdCurso(clase.getCurso().getId());

				listExamsOnTime.add(examen);
			}
		}

		return listExamsOnTime;
	}

	@Transactional(readOnly = true)
	@Override
	public List<TareaViewModel> findHomeworksByClase(Integer id) throws Exception {// *
		HourFormatter hourFormatter = new HourFormatter();

		Clase claseOwner = new Clase();
		claseOwner.setId(id);

		java.util.Date fecha = new java.util.Date(); // fecha hoy

		// List<ActividadAcademica> allExams =
		// actividadAcademicaRepository.findByClase(claseOwner);
		List<ActividadAcademica> allHomeworks = actividadAcademicaRepository
				.findByClaseAndFechaMaxEntregaGreaterThanEqual(claseOwner, fecha);

		List<TareaViewModel> listHomeworksOnTime = new ArrayList<TareaViewModel>();

		for (ActividadAcademica actividadAcademica : allHomeworks) {// RECORRO TODOS LOS EXAMENES A LA FECHA OBTENIDOS
																	// PARA
			// AGREGARLOS A SU RESPECTIVO VIEWMODEL
			String[] descr = actividadAcademica.getDescripcion().split("#");
			if (descr[0].equals("HW")) {
				System.out.println(actividadAcademica.getDescripcion());

				TareaViewModel homework = new TareaViewModel();
				homework.setId(actividadAcademica.getId());
				homework.setFechaMaxEntrega((Date) actividadAcademica.getFechaMaxEntrega());
				homework.setFechaPublicacion((Date) actividadAcademica.getFechaPublicacion());
				homework.setHora(descr[3]);
				homework.setDescripcion(descr[2]);
				homework.setCurso(actividadAcademica.getClase().getCurso().getNombre());
				homework.setGrado(actividadAcademica.getClase().getGrado());
				homework.setIdClase(actividadAcademica.getClase().getId());

				listHomeworksOnTime.add(homework);
			}
		}

		return listHomeworksOnTime;
	}

	@Transactional(readOnly = true)
	@Override
	public List<TareaViewModel> findAllHomeworksByClase(Integer id) throws Exception {
		HourFormatter hourFormatter = new HourFormatter();

		Clase claseOwner = new Clase();
		claseOwner.setId(id);

		java.util.Date fecha = new java.util.Date(); // fecha hoy

		List<TareaViewModel> allHomeworks = new ArrayList<TareaViewModel>();
		List<ActividadAcademica> allTareas = actividadAcademicaRepository.findByClase(claseOwner);

		for (ActividadAcademica actividadAcademica : allTareas) {// RECORRO TODOS LOS EXAMENES A LA FECHA OBTENIDOS
																	// PARA
			// AGREGARLOS A SU RESPECTIVO VIEWMODEL
			String[] descr = actividadAcademica.getDescripcion().split("#");
			if (descr[0].equals("HW")) {

				TareaViewModel homework = new TareaViewModel();
				homework.setId(actividadAcademica.getId());
				homework.setFechaMaxEntrega((Date) actividadAcademica.getFechaMaxEntrega());
				homework.setFechaPublicacion((Date) actividadAcademica.getFechaPublicacion());
				homework.setHora(descr[3]);
				homework.setDescripcion(descr[2]);
				homework.setCurso(actividadAcademica.getClase().getCurso().getNombre());
				homework.setGrado(actividadAcademica.getClase().getGrado());
				homework.setIdClase(actividadAcademica.getClase().getId());

				allHomeworks.add(homework);
			}
		}

		return allHomeworks;
	}

	@Transactional
	@Override
	public Calificacion saveCalificacion(CalificacionViewModel calificacionViewModel) throws Exception {

		Calificacion calificacion = new Calificacion();
		Alumno alumno = new Alumno();
		ActividadAcademica actividadAcademica = actividadAcademicaRepository
				.findById(calificacionViewModel.getIdExamen()).get();
		List<Calificacion> notas = actividadAcademica.getCalificaciones();
		alumno.setId(calificacionViewModel.getIdAlumno());
		calificacion.setAlumno(alumno);

		actividadAcademica.setId(calificacionViewModel.getIdExamen());
		calificacion.setActividadAcademica(actividadAcademica);
		calificacion.setNota(calificacionViewModel.getNota());
		calificacion.setFechaRevision(calificacionViewModel.getFechaRevision());
		calificacion.setComentario(calificacionViewModel.getDescripcion());
		calificacion.setId(calificacionViewModel.getIdCalificacion());
		for (Calificacion nota : notas) {

			if (nota.getAlumno().getId() == calificacionViewModel.getIdAlumno()) {

				calificacion.setId(nota.getId());

			}
		}

		System.err.println(
				calificacionViewModel.getIdAlumno() + " " + calificacionViewModel.getIdExamen() + "Prueba view model");
		System.err.println("Estoy en serviceimpl" + calificacion.getActividadAcademica().getId() + " "
				+ calificacion.getAlumno().getId());
		calificacionRepository.save(calificacion);

		return calificacion;

	}

	@Override
	public void save(Calificacion tareas) {
		// TODO Auto-generated method stub
		System.err.println("SE GUARDAR LA CALIFICACION CON EL LINK " + tareas.getLinkDoc() + " DEL ALUMNO "
				+ tareas.getAlumno().getId() + " DE LA ACTIVIDAD " + tareas.getActividadAcademica().getId());

		calificacionRepository.save(tareas);
	}

//////// REPORTESSSSS////////
	@Transactional(readOnly = true)
	@Override
	public List<ReporteViewModel> findAAbyClase(Integer id) throws Exception {
		System.err.println("SI ENTRO");
		ActividadAcademica actividadacademica = actividadAcademicaRepository.findById(id).get();
		List<ReporteViewModel> ListActividades = new ArrayList<ReporteViewModel>();
			String[] descr = actividadacademica.getDescripcion().split("#");
			List<Calificacion> calificaciones = actividadacademica.getCalificaciones();
			for (Calificacion calificacion : calificaciones) {
				Alumno alumno = calificacion.getAlumno();
				ReporteViewModel reporteActividad = new ReporteViewModel();

				reporteActividad.setApellidoAlumno(alumno.getUsuario().getApellido());
				reporteActividad.setNombreAlumno(alumno.getUsuario().getNombre());
				reporteActividad.setDescripcion(actividadacademica.getDescripcion());
				reporteActividad.setFechaExamen(calificacion.getFechaRevision());
				reporteActividad.setFechaMaxEntrega(actividadacademica.getFechaMaxEntrega());
				reporteActividad.setFechaPublicacion(actividadacademica.getFechaPublicacion());
				reporteActividad.setGrado(actividadacademica.getClase().getGrado());
				reporteActividad.setIdClase(actividadacademica.getClase().getId());
				reporteActividad.setNota(calificacion.getNota());
				reporteActividad.setIdUsuario(alumno.getId());
				reporteActividad.setIdActividad(actividadacademica.getId());
				reporteActividad.setNombre(actividadacademica.getDescripcion());
				ListActividades.add(reporteActividad);
				System.err.println(reporteActividad.getNombreAlumno()+ " " + reporteActividad.getGrado()+ " " +reporteActividad.getNota() + " " +reporteActividad.getIdClase()+ " " + reporteActividad.getIdActividad());

		}
		return ListActividades;
	}
	
	@Transactional(readOnly= true)
	@Override
	public List<ActividadAcademica> findByClase(Integer id) throws Exception {
		Clase claseOwner = claseRepository.findById(id).get();
		List<ActividadAcademica> actividades = actividadAcademicaRepository.findByClase(claseOwner);
		for (ActividadAcademica actividadAcademica2 : actividades) {
			String[] descr = actividadAcademica2.getDescripcion().split("#");
			actividadAcademica2.setDescripcion(descr[2]);
		}
		return actividades;
	}

	@Override
	public List<ReporteViewModel> findByCalificacionAlumno(Integer idAlumno, Integer idClase) throws Exception {
		System.err.println("SI ENTRO");
		Alumno alumnoOwner = alumnoRepository.findById(idAlumno).get();
	//	ActividadAcademica actividad = actividadAcademicaRepository.findById(idActividad).get();
		List<Calificacion> calificaciones = calificacionRepository.findByAlumno(alumnoOwner);
		List<ReporteViewModel> ListActividades = new ArrayList<ReporteViewModel>();
		for (Calificacion calificacion : calificaciones) {
			String[] descr = calificacion.getActividadAcademica().getDescripcion().split("#");
			if(calificacion.getActividadAcademica().getClase().getId() == idClase) {
				ReporteViewModel reporteActividad = new ReporteViewModel();
				reporteActividad.setApellidoAlumno(alumnoOwner.getUsuario().getApellido());
				reporteActividad.setNombreAlumno(alumnoOwner.getUsuario().getNombre());
				reporteActividad.setDescripcion(descr[2]);
				reporteActividad.setFechaExamen(calificacion.getFechaRevision());
				reporteActividad.setFechaMaxEntrega(calificacion.getActividadAcademica().getFechaMaxEntrega());
				reporteActividad.setFechaPublicacion(calificacion.getActividadAcademica().getFechaPublicacion());
				reporteActividad.setGrado(calificacion.getActividadAcademica().getClase().getGrado());
				reporteActividad.setIdClase(calificacion.getActividadAcademica().getClase().getId());
				reporteActividad.setNota(calificacion.getNota());
				reporteActividad.setIdUsuario(alumnoOwner.getId());
				reporteActividad.setIdActividad(calificacion.getActividadAcademica().getId());
				reporteActividad.setNombre(calificacion.getActividadAcademica().getDescripcion());
				ListActividades.add(reporteActividad);
			}
			
		}
		return ListActividades;
	}

	@Override
	public CalificacionRepository getCalificacionRepository() {
		return calificacionRepository;
	}

	
	

}