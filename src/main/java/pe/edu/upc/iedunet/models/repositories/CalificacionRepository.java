package pe.edu.upc.iedunet.models.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.iedunet.models.entities.ActividadAcademica;
import pe.edu.upc.iedunet.models.entities.Alumno;
import pe.edu.upc.iedunet.models.entities.Calificacion;
import pe.edu.upc.iedunet.models.entities.Clase;
@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Integer> {
	List<Calificacion> findByAlumno(Alumno alumno) throws Exception; 
	Optional<Calificacion> findByAlumnoAndActividadAcademica(Alumno alumno, ActividadAcademica aa) throws Exception; 
}
