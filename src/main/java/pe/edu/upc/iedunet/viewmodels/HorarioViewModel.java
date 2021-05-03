package pe.edu.upc.iedunet.viewmodels;

import pe.edu.upc.iedunet.helpers.HourFormatter;

public class HorarioViewModel {
	private int id;

	private String dia;

	private String horaInicio;
	private String horaFin;

	private int claseId;

	private String curso;

	private String linkToClass;

	private String profesor;

	private String linkExamen;

	private int horaInicioInt;
	private int horaFinInt;

	HourFormatter hourFormatter = new HourFormatter();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public int getClaseId() {
		return claseId;
	}

	public void setClaseId(int claseId) {
		this.claseId = claseId;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public String getLinkToClass() {
		/*if (curso != null) {
			return "https://us04web.zoom.us/j/5528160655?pwd=UHM5azc4TmhiaEJKQ3p0SGNIdnJndz09";
		}
		else {
			return "";
		}*/
		return linkToClass;
	}

	public void setLinkToClass(String linkToClass) {
		this.linkToClass = linkToClass;
	}

	public String getProfesor() {
		return profesor;
	}

	public void setProfesor(String profesor) {
		this.profesor = profesor;
	}

	public String getLinkExamen() {
		return linkExamen;
	}

	public void setLinkExamen(String linkExamen) {
		this.linkExamen = linkExamen;
	}

	public int getHoraInicioInt() {
		horaInicioInt = hourFormatter.HourToNumber(horaInicio);
		return horaInicioInt;
	}

	public int getHoraFinInt() {
		horaFinInt = hourFormatter.HourToNumber(horaFin);
		return horaFinInt;
	}

}
