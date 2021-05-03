package pe.edu.upc.iedunet.viewmodels;

import java.sql.Date;

public class TareaViewModel {
	private int id;
	private String link;
	
	private String linkDoc;
	
	private String nombre;
	
	private String descripcion;
	
	private String hora;
	
	private Date fechaMaxEntrega;
	private Date fechaPublicacion;
	
	private int idClase;
	
	private int idActividad;
	
	private String fullDescripcion;
	
	private String curso;
	
	private String grado;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public Date getFechaMaxEntrega() {
		return fechaMaxEntrega;
	}

	public void setFechaMaxEntrega(Date fechaMaxEntrega) {
		this.fechaMaxEntrega = fechaMaxEntrega;
	}

	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public int getIdClase() {
		return idClase;
	}

	public void setIdClase(int idClase) {
		this.idClase = idClase;
	}

	public String getFullDescripcion() {
		return "HW#" + link + "#" + descripcion + "#" + hora;
	}

	public void setFullDescripcion(String fullDescripcion) {
		this.fullDescripcion = fullDescripcion;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}
	
	public String getGrado() {
		return grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public String getLinkDoc() {
		return linkDoc;
	}

	public void setLinkDoc(String linkDoc) {
		this.linkDoc = linkDoc;
	}

	public int getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}
	
}
