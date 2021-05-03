package pe.edu.upc.iedunet.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.iedunet.models.entities.Alumno;
import pe.edu.upc.iedunet.models.entities.AlumnoClase;
import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.models.entities.Horario;
@Repository
public interface AlumnoClaseRepository extends JpaRepository<AlumnoClase, Integer>{
	List<AlumnoClase> findByAlumno(Alumno alumno) throws Exception;
	List<AlumnoClase> findByClase(Clase clase) throws Exception;
}
