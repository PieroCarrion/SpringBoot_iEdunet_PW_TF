package pe.edu.upc.iedunet.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.iedunet.models.entities.Alumno;
import pe.edu.upc.iedunet.models.entities.AlumnoClase;
import pe.edu.upc.iedunet.models.entities.Profesor;
@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Integer> {
	
}
