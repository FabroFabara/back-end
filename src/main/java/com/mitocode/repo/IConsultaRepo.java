package com.mitocode.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mitocode.model.Consulta;

public interface IConsultaRepo extends IGenericRepo<Consulta, Integer>{
	
	//JPQL Java Persistence Query Language
	@Query("FROM Consulta c WHERE c.paciente.dni = :dni OR LOWER(c.paciente.nombres) LIKE %:nombreCompleto% OR LOWER(c.paciente.apellidos) LIKE %:nombreCompleto%")
	List<Consulta> buscar(@Param("dni") String dni, @Param("nombreCompleto") String nombreCompleto);
	
	//>=24-07-21 // <25-07-21 
	@Query("FROM Consulta c WHERE c.fecha BETWEEN :fechaConsulta AND :fechaSgte")
	List<Consulta> buscarFecha(@Param("fechaConsulta") LocalDateTime fechaConsulta, @Param("fechaSgte") LocalDateTime fechaSgte);

	@Query(value = "select * from fn_listarResumen()", nativeQuery = true)
	List<Object[]> listarResumen();
	
	//cantidad	fecha
	//[6, 03/07/2021]
	//[4, 24/07/2021]
		
}
