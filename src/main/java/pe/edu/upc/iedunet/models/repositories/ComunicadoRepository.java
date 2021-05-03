package pe.edu.upc.iedunet.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.iedunet.models.entities.Clase;
import pe.edu.upc.iedunet.models.entities.Comunicado;
@Repository
public interface ComunicadoRepository extends JpaRepository<Comunicado, Integer>{
	List<Comunicado> findByClaseOrderByFechaDesc(Clase clase);
}
