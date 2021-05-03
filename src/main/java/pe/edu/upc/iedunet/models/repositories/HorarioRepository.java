package pe.edu.upc.iedunet.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.models.entities.Horario;
@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer>{
	List<Horario> findByClase(Clase clase) throws Exception;
}
