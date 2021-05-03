package pe.edu.upc.iedunet.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.iedunet.models.entities.Curso;
@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer>{
	
}
