package pe.edu.upc.iedunet.viewmodels;

import java.sql.Date;

public class ExamenViewModel {
	private int id;
	private String link;
	
	private String nombre;
	
	private String descripcion;
	
	private String hora;
	
	private Date fechaExamen;
	
	private int idClase;
	
	private int idCurso;
	
	private String fullDescripcion;
	
	private String curso;
	
	private String grado;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	
	public String getFullDescripcion() {
		return "Exam#" + link + "#" + descripcion + "#" + hora;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		System.out.println(hora);
		this.hora = hora;
	}

	public Date getFechaExamen() {
		return fechaExamen;
	}

	public void setFechaExamen(Date date) {
		this.fechaExamen = date;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdClase() {
		return idClase;
	}

	public void setIdClase(int idClase) {
		this.idClase = idClase;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public void setFullDescripcion(String fullDescripcion) {
		this.fullDescripcion = fullDescripcion;
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public String getGrado() {
		return grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}
	
	
}
