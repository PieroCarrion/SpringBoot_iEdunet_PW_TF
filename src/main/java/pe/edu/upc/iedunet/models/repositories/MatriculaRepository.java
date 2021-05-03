package pe.edu.upc.iedunet.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.iedunet.models.entities.Matricula;
@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Integer>{

}
