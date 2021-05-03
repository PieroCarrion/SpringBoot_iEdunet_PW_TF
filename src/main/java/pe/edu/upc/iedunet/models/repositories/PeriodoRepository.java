package pe.edu.upc.iedunet.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.iedunet.models.entities.Periodo;
@Repository
public interface PeriodoRepository extends JpaRepository<Periodo, Integer>{
	List<Periodo> findByColegio(int id) throws Exception;
}
