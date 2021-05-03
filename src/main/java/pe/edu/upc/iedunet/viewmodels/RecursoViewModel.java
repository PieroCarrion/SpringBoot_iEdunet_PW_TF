package pe.edu.upc.iedunet.viewmodels;

public class RecursoViewModel {
	private Integer id;
	private String nombre;
	private String linkDocumento;
	private int idClase;
	private String descripcion;
	private String fechaPublicacion;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getLinkDocumento() {
		return linkDocumento;
	}
	public void setLinkDocumento(String linkDocumento) {
		this.linkDocumento = linkDocumento;
	}
	public int getIdClase() {
		return idClase;
	}
	public void setIdClase(Integer idClase) {
		this.idClase = idClase;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getFechaPublicacion() {
		return fechaPublicacion;
	}
	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}
	
}
