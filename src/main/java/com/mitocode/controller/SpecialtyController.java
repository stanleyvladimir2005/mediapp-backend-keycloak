package com.mitocode.controller;

import com.mitocode.model.Specialty;
import com.mitocode.service.ISpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1/specialtys")
public class SpecialtyController {
	
	@Autowired
	private ISpecialtyService service;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Specialty>> findAll() {
		List<Specialty> specialtys = service.findAll();
		return new ResponseEntity<>(specialtys, OK);
	}
		
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> save(@Valid @RequestBody Specialty Specialty) {
		Specialty spe = service.save(Specialty);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(spe.getIdSpecialty()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping ("/{id}")
	public ResponseEntity<Specialty> update(@PathVariable("id") Integer id,@Valid @RequestBody Specialty specialty) {
		specialty.setIdSpecialty(id);
		Specialty spe = service.update(specialty,id);
		return new ResponseEntity<>(spe,OK);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
		service.delete(id);
		return new ResponseEntity<>(OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Specialty> findById(@PathVariable("id") Integer id) {
		Specialty specialty = service.findById(id);
		return new ResponseEntity<>(specialty, OK);
	}
	
	@GetMapping(value="/pageableSpecialty", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Specialty>> listPageable(Pageable pageable) {
		Page<Specialty> specialty = service.listPageable(pageable);
		return new ResponseEntity<>(specialty, HttpStatus.OK);
	}

	@GetMapping("/hateoas/{id}")
	public EntityModel<Specialty> findByIdHateoas(@PathVariable("id") Integer id) {
		Specialty esp = service.findById(id);
		EntityModel<Specialty> resource = EntityModel.of(esp);
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
		WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());
		resource.add(link1.withRel("specialty-info1"));
		resource.add(link2.withRel("specialty-full"));
		return resource;
	}
}