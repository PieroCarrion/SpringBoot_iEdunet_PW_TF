package pe.edu.upc.iedunet.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.iedunet.models.entities.Alumno;
@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Integer>{
	//List<Alumno> findByColegio(int id) throws Exception;
	
}
