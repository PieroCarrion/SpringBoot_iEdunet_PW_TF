package pe.edu.upc.iedunet.viewmodels;

public class ClaseViewModel {
	public int id;
	public String grado;
	public String linkClase;
	public int maxInasistencias;
	public int profesorId;
	public String cursoNombre;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGrado() {
		return grado;
	}
	public void setGrado(String grado) {
		this.grado = grado;
	}
	public String getLinkClase() {
		return linkClase;
	}
	public void setLinkClase(String linkClase) {
		this.linkClase = linkClase;
	}
	public int getMaxInasistencias() {
		return maxInasistencias;
	}
	public void setMaxInasistencias(int maxInasistencias) {
		this.maxInasistencias = maxInasistencias;
	}
	public int getProfesorId() {
		return profesorId;
	}
	public void setProfesorId(int profesorId) {
		this.profesorId = profesorId;
	}
	public String getCursoNombre() {
		return cursoNombre;
	}
	public void setCursoNombre(String cursoNombre) {
		this.cursoNombre = cursoNombre;
	}
	
}
