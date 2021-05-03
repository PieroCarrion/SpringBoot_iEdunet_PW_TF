package pe.edu.upc.iedunet.models.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.iedunet.models.entities.Colegio;

@Repository
public interface ColegioRepository extends JpaRepository<Colegio, Integer>{
	Optional<Colegio> findByCodigo(String codigo) throws Exception;
}