package pe.edu.upc.iedunet.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.iedunet.models.entities.Alumno;
import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.models.entities.Profesor;
@Repository
public interface ClaseRepository extends JpaRepository<Clase, Integer>{
	List<Clase> findByProfesor(Profesor profesor) throws Exception;
}
