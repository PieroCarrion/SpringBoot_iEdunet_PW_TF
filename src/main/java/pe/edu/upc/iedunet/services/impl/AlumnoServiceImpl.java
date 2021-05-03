package pe.edu.upc.iedunet.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.iedunet.helpers.HourFormatter;
import pe.edu.upc.iedunet.models.entities.ActividadAcademica;
import pe.edu.upc.iedunet.models.entities.Alumno;
import pe.edu.upc.iedunet.models.entities.AlumnoClase;
import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.models.entities.Curso;
import pe.edu.upc.iedunet.models.entities.Horario;
import pe.edu.upc.iedunet.models.entities.Profesor;
import pe.edu.upc.iedunet.models.entities.Usuario;
import pe.edu.upc.iedunet.models.repositories.ActividadAcademicaRepository;
import pe.edu.upc.iedunet.models.repositories.AlumnoClaseRepository;
import pe.edu.upc.iedunet.models.repositories.AlumnoRepository;
import pe.edu.upc.iedunet.models.repositories.ClaseRepository;
import pe.edu.upc.iedunet.models.repositories.CursoRepository;
import pe.edu.upc.iedunet.models.repositories.HorarioRepository;
import pe.edu.upc.iedunet.models.repositories.ProfesorRepository;
import pe.edu.upc.iedunet.models.repositories.UsuarioRepository;
import pe.edu.upc.iedunet.services.AlumnoService;
import pe.edu.upc.iedunet.viewmodels.CursoViewModel;
import pe.edu.upc.iedunet.viewmodels.HorarioViewModel;

@Service
public class AlumnoServiceImpl implements AlumnoService, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private AlumnoRepository alumnoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private HorarioRepository horarioRepository;

	@Autowired
	private AlumnoClaseRepository alumnoClaseRepository;

	@Autowired
	private ClaseRepository claseRepository;

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private ActividadAcademicaRepository actividadAcademicaRepository;

	@Transactional
	@Override
	public Alumno save(Alumno entity) throws Exception {
		usuarioRepository.save(entity.getUsuario());
		return alumnoRepository.save(entity);
	}

	@Transactional
	@Override
	public Alumno update(Alumno entity) throws Exception {
		usuarioRepository.save(entity.getUsuario());
		return alumnoRepository.save(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		Long x = findById(id).get().getUsuario().getId();
		alumnoRepository.deleteById(id);
		usuarioRepository.deleteById(x);
		System.out.println("ID a Eliminar en Tabla Alumno: " + id + "---" + "ID a Eliminar en Tabla Usuario: " + x);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Alumno> findById(Integer id) throws Exception {
		return alumnoRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Alumno> findAll() throws Exception {
		return alumnoRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Alumno> findByColegio(int id) throws Exception {
		// TODO Auto-generated method stub
		// return alumnoRepository.findByColegio(id);
		return null;
	}

	@Override
	public List<HorarioViewModel> findHorario(int idAlumno) throws Exception {
		Alumno alumno = new Alumno();
		alumno.setId(idAlumno);
		// Obtener los alumnosclases
		List<AlumnoClase> alumnoClases = alumnoClaseRepository.findByAlumno(alumno);

		// obtener las clases
		List<Clase> clases = new ArrayList<Clase>();

		for (AlumnoClase x : alumnoClases) {
			clases.add(claseRepository.findById(x.getClase().getId()).get());
		}

		// obtener los horarios
		List<Horario> horarios = new ArrayList<Horario>();

		for (Clase clase : clases) {
			horarios.addAll(horarioRepository.findByClase(clase));
		}

		List<HorarioViewModel> list = new ArrayList<HorarioViewModel>();

		HourFormatter hourFormatter = new HourFormatter();

		for (Horario x : horarios) {
			HorarioViewModel obj = new HorarioViewModel();
			obj.setId(x.getId());
			obj.setHoraInicio(hourFormatter.HourToString(x.getHoraInicio()));
			obj.setClaseId(x.getClase().getId());
			obj.setCurso(x.getClase().getCurso().getNombre());
			obj.setDia(x.getDia());
			obj.setHoraFin(hourFormatter.HourToString(x.getHoraFin()));
			obj.setLinkToClass(x.getClase().getLinktoClass());
			Usuario profesor = x.getClase().getProfesor().getUsuario();
			obj.setProfesor(profesor.getNombre() + ' ' + profesor.getApellido());
			list.add(obj);
		}

		return list;
	}

	@Override
	public List<CursoViewModel> findCursos(int idAlumno) throws Exception {
		HourFormatter hourFormatter = new HourFormatter(); // Para pasar la hora de Int a String y viceversa
		List<CursoViewModel> cursos = new ArrayList<>();
		Alumno alumno = new Alumno();
		alumno.setId(idAlumno);
		List<AlumnoClase> alumnoClases = alumnoClaseRepository.findByAlumno(alumno);
		List<Clase> clasesT = new ArrayList<>();
		for (AlumnoClase x : alumnoClases) {
			clasesT.add(claseRepository.findById(x.getClase().getId()).get());
		}
		for (Clase y : clasesT) {
			CursoViewModel curso = new CursoViewModel();
			curso.setNombreSeccionCurso(y.getCurso().getNombre() + " " + y.getGrado());

			Profesor profesor = y.getProfesor();
			curso.setDocenteCurso(profesor.getUsuario().getApellido() + ", " + profesor.getUsuario().getNombre());
			curso.setIdClase(y.getId());
			curso.setId(y.getCurso().getId());
			curso.setLinkClase(y.getLinktoClass());
			curso.setEmailProfesor(profesor.getUsuario().getEmail());

			String horario = "";
			for (Horario z : y.getHorarios()) {
				horario += z.getDia() + " " + hourFormatter.HourToString(z.getHoraInicio()) + "-"
						+ hourFormatter.HourToString(z.getHoraFin()) + ", ";
			}
			curso.setHorarioCurso(horario);
			curso.setNombreCurso(y.getCurso().getNombre());

			cursos.add(curso);
		}
		return cursos;
	}

	@Override
	public CursoViewModel findCurso(int idAlumno, int idClase) throws Exception {
		HourFormatter hourFormatter = new HourFormatter(); // Para pasar la hora de Int a String y viceversa

		Clase claseT = claseRepository.findById(idClase).get();

		Curso obj = cursoRepository.findById(claseT.getCurso().getId()).get();
		CursoViewModel curso = new CursoViewModel();
		curso.setNombreSeccionCurso(claseT.getCurso().getNombre() + " " + claseT.getGrado());

		Profesor profesor = claseT.getProfesor();

		curso.setDocenteCurso(profesor.getUsuario().getApellido() + ", " + profesor.getUsuario().getNombre());
		curso.setIdClase(claseT.getId());
		curso.setId(claseT.getCurso().getId());
		curso.setLinkClase(claseT.getLinktoClass());
		curso.setEmailProfesor(profesor.getUsuario().getEmail());
		curso.setGrado(claseT.getGrado());
		List<ActividadAcademica> actividades = new ArrayList<>();
		actividades = actividadAcademicaRepository.findByClase(claseT);
		for (ActividadAcademica x : actividades) {
			System.out.print(x.getDescripcion());
		}
		if (actividades != null) {
			curso.setActividades(actividades);
		}
		String horario = "";
		for (Horario z : claseT.getHorarios()) {
			horario += z.getDia() + " " + hourFormatter.HourToString(z.getHoraInicio()) + "-"
					+ hourFormatter.HourToString(z.getHoraFin()) + ", ";
		}
		curso.setHorarioCurso(horario);
		curso.setNombreCurso(obj.getNombre());
		return curso;
	}

	@Override
	public String findLinkClase(int idAlumno, int idCurso) throws Exception {
		Alumno alumno = new Alumno();
		alumno.setId(idAlumno);
		List<AlumnoClase> alumnoClases = alumnoClaseRepository.findByAlumno(alumno);
		List<Clase> clasesT = new ArrayList<>();
		for (AlumnoClase x : alumnoClases) {
			clasesT.add(claseRepository.findById(x.getClase().getId()).get());
		}
		Clase claseT = new Clase();
		for (Clase y : clasesT) {
			if (y.getCurso().getId() == idCurso) {
				claseT = y;
			}
		}
		return claseT.getLinktoClass();
	}
}