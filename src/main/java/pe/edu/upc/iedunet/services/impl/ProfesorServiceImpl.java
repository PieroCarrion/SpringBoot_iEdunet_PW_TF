package pe.edu.upc.iedunet.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.iedunet.helpers.HourFormatter;
import pe.edu.upc.iedunet.models.entities.Profesor;
import pe.edu.upc.iedunet.models.entities.Alumno;
import pe.edu.upc.iedunet.models.entities.AlumnoClase;
import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.models.entities.Comunicado;
import pe.edu.upc.iedunet.models.entities.Curso;
import pe.edu.upc.iedunet.models.entities.Horario;
import pe.edu.upc.iedunet.models.entities.Usuario;
import pe.edu.upc.iedunet.models.repositories.ProfesorRepository;
import pe.edu.upc.iedunet.models.repositories.ClaseRepository;
import pe.edu.upc.iedunet.models.repositories.ComunicadoRepository;
import pe.edu.upc.iedunet.models.repositories.CursoRepository;
import pe.edu.upc.iedunet.models.repositories.HorarioRepository;
import pe.edu.upc.iedunet.models.repositories.UsuarioRepository;
import pe.edu.upc.iedunet.services.ProfesorService;
import pe.edu.upc.iedunet.viewmodels.CursoViewModel;
import pe.edu.upc.iedunet.viewmodels.HorarioViewModel;

@Service
public class ProfesorServiceImpl implements ProfesorService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProfesorRepository profesorRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ClaseRepository claseRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private HorarioRepository horarioRepository;
	
	@Autowired
	private ComunicadoRepository comunicadoRepository;
	
	@Transactional
	@Override
	public Profesor save(Profesor entity) throws Exception {
		usuarioRepository.save(entity.getUsuario());
		return profesorRepository.save(entity);
		
	}

	@Transactional
	@Override
	public Profesor update(Profesor entity) throws Exception {
		usuarioRepository.save(entity.getUsuario());
		System.out.println("Update" + entity.getId());
		return profesorRepository.save(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		Long x = findById(id).get().getUsuario().getId();
		profesorRepository.deleteById(id);
		usuarioRepository.deleteById(x);
		System.out.println("ID a Eliminar en Tabla Profesor: " + id + "---" + "ID a Eliminar en Tabla Usuario: " + x);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Profesor> findById(Integer id) throws Exception {
		return profesorRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Profesor> findAll() throws Exception {
		return profesorRepository.findAll();
	}
	@Transactional(readOnly = true)
	@Override
	public List<Profesor> findByColegio(int id) throws Exception {
		return null;
		//return profesorRepository.findByColegio(id);
	}

	@Override
	public List<HorarioViewModel> findHorario(int idProfesor) throws Exception {
		Profesor profesor = new Profesor();
		profesor.setId(idProfesor);
		//Obtener los profesoresclases
		List<Clase> clases = claseRepository.findByProfesor(profesor);
		
		
		
		//obtener los horarios
		List<Horario> horarios = new ArrayList<Horario>();
		
		for (Clase clase : clases) {
			horarios.addAll(horarioRepository.findByClase(clase));
			System.err.println(clase.getProfesor());
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
		
			list.add(obj);
		}
		
		return list;
	}
	
	@Override
	public CursoViewModel findCurso(int idProfesor, int idClase) throws Exception {
		HourFormatter hourFormatter = new HourFormatter(); // Para pasar la hora de Int a String y viceversa


		Clase claseT = claseRepository.findById(idClase).get();

		Curso obj = cursoRepository.findById(claseT.getCurso().getId()).get();
		CursoViewModel curso = new CursoViewModel();
		
		
		curso.setNombreSeccionCurso(claseT.getCurso().getNombre() + " " + claseT.getGrado());
		curso.setIdClase(claseT.getId());
		curso.setId(claseT.getCurso().getId());
		curso.setLinkClase(claseT.getLinktoClass());
		curso.setGrado(claseT.getGrado());
		curso.setEmailProfesor(claseT.getProfesor().getUsuario().getEmail());
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
	public Comunicado saveComunicado(Comunicado comunicado) throws Exception {
		// TODO Auto-generated method stub
		return comunicadoRepository.save(comunicado);
	}

}

