package pe.edu.upc.iedunet.models.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.iedunet.models.entities.ActividadAcademica;
import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.models.entities.Horario;

@Repository
public interface ActividadAcademicaRepository extends JpaRepository<ActividadAcademica, Integer>{
	List<ActividadAcademica> findByClaseAndFechaMaxEntregaGreaterThanEqual(Clase clase, Date fecha) throws Exception;
	List<ActividadAcademica> findByClase(Clase clase) throws Exception;
	
}
