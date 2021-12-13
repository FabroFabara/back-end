package com.mitocode.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Paciente;
import com.mitocode.service.IPacienteService;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

	@Autowired
	private IPacienteService service;
	
	@GetMapping
	public ResponseEntity<List<Paciente>> listar() throws Exception{
		return new ResponseEntity<>(service.listar(), HttpStatus.OK);		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Paciente> listarPorId(@PathVariable("id") Integer id) throws Exception{
		Paciente obj = service.listarPorId(id);
		
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK); 		
	}
	
	@PostMapping
	public ResponseEntity<Paciente> registrar(@Valid @RequestBody Paciente p) throws Exception {
		//return new ResponseEntity<>(service.registrar(obj), HttpStatus.CREATED); //201 		
		 
		Paciente obj = service.registrar(p);
		//localhost:8080/pacientes/1
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdPaciente()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Paciente> modificar(@Valid @RequestBody Paciente paciente) throws Exception {
		return new ResponseEntity<>(service.modificar(paciente), HttpStatus.OK);		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{		
		service.eliminar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/hateoas/{id}")
	public EntityModel<Paciente> listarHateoasPorId(@PathVariable("id") Integer id) throws Exception{
		Paciente obj = service.listarPorId(id);
		
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		
		EntityModel<Paciente> recurso = EntityModel.of(obj);
		//localhost:8080/pacientes/hateoas/1
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).listarHateoasPorId(id));
		WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).listarHateoasPorId(id));
		recurso.add(link1.withRel("paciente-recurso1"));
		recurso.add(link2.withRel("paciente-recurso2"));
		
		return recurso;
		
	}
	
	@GetMapping("/pageable")
	public ResponseEntity<Page<Paciente>> listarPageable(Pageable pageable) throws Exception{
		Page<Paciente> pacientes = service.listarPageable(pageable);
		return new ResponseEntity<Page<Paciente>>(pacientes, HttpStatus.OK);
	}
	
	
}
