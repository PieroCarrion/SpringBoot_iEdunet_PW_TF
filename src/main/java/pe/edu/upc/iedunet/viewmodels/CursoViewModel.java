package pe.edu.upc.iedunet.viewmodels;

import java.util.List;

import pe.edu.upc.iedunet.models.entities.ActividadAcademica;

public class CursoViewModel {
	private int id;
	private String nombreSeccionCurso;
	private String docenteCurso;
	private String horarioCurso;
	private String nombreCurso;
	private String emailProfesor;
	private String linkClase;
	private String grado;
	private int idClase;
	private List<ActividadAcademica> actividades;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombreSeccionCurso() {
		return nombreSeccionCurso;
	}
	public void setNombreSeccionCurso(String nombreSeccionCurso) {
		this.nombreSeccionCurso = nombreSeccionCurso;
	}
	public String getDocenteCurso() {
		return docenteCurso;
	}
	public void setDocenteCurso(String docenteCurso) {
		this.docenteCurso = docenteCurso;
	}
	public String getHorarioCurso() {
		return horarioCurso;
	}
	public void setHorarioCurso(String horarioCurso) {
		this.horarioCurso = horarioCurso;
	}
	public String getNombreCurso() {
		return nombreCurso;
	}
	public void setNombreCurso(String nombreCurso) {
		this.nombreCurso = nombreCurso;
	}
	public String getEmailProfesor() {
		return emailProfesor; 
	}
	public void setEmailProfesor(String emailProfesor) {
		this.emailProfesor = emailProfesor;
	}
	public String getLinkClase() {
		return linkClase;
	}
	public void setLinkClase(String linkClase) {
		this.linkClase = linkClase;
	}
	public int getIdClase() {
		return idClase;
	}
	public void setIdClase(int idClase) {
		this.idClase = idClase;
	}
	public String getGrado() {
		return grado;
	}
	public void setGrado(String grado) {
		this.grado = grado;
	}
	public List<ActividadAcademica> getActividades() {
		return actividades;
	}
	public void setActividades(List<ActividadAcademica> actividades) {
		this.actividades = actividades;
	}
}