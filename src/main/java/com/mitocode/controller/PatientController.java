package com.mitocode.controller;

import com.mitocode.model.Patient;
import com.mitocode.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
@RequestMapping("/v1/patients")
public class PatientController {
	
	@Autowired
	private IPatientService service;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Patient>> findAll() {
		List<Patient> patients = service.findAll();
		return new ResponseEntity<>(patients, OK); //ResponseEntity<List<Patient>>
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> save(@Valid @RequestBody Patient patient) {
		Patient pac = service.save(patient);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pac.getIdPatient()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Patient> update(@PathVariable("id") Integer id, @Valid @RequestBody Patient patient) {
		patient.setIdPatient(id);
		Patient pac = service.update(patient, id);
		return new ResponseEntity<>(pac,OK);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return new ResponseEntity<>(OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Patient> findById(@PathVariable("id") Integer id) {
		Patient patient = service.findById(id);
		return new ResponseEntity<>(patient, OK);
	}
	
	@GetMapping(value="/pageablePatient", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Patient>> listPageable(Pageable pageable) {
		Page<Patient> pacientes = service.listPageable(pageable);
		return new ResponseEntity<>(pacientes, OK);
	}

	@GetMapping("/hateoas/{id}")
	public EntityModel<Patient> findByIdHateoas(@PathVariable("id") Integer id) {
		Patient pac = service.findById(id);
		EntityModel<Patient> resource = EntityModel.of(pac);
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
		WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());
		resource.add(link1.withRel("patient-info1"));
		resource.add(link2.withRel("patient-full"));
		return resource;
	}
}