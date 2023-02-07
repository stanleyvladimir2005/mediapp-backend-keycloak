package com.mitocode.controller;

import com.mitocode.model.Exam;
import com.mitocode.service.IExamService;
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
@RequestMapping("/v1/exams")
public class ExamController {
	
	@Autowired
	private IExamService service;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Exam>> findAll() {
		List<Exam> examenes = service.findAll();
		return new ResponseEntity<>(examenes, OK);
	}
		
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> save(@Valid @RequestBody Exam Exam) {
		Exam pac = service.save(Exam);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pac.getIdExam()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Exam> update(@PathVariable("id") Integer id, @Valid @RequestBody Exam exam) {
		exam.setIdExam(id);
		Exam exa = service.update(exam, id);
		return new ResponseEntity<>(exa,OK);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete (@PathVariable Integer id) {
		service.delete(id);
		return new ResponseEntity<>(OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Exam> findById(@PathVariable("id") Integer id) {
		Exam exam = service.findById(id);
		return new ResponseEntity<>(exam, OK);
	}
	
	@GetMapping(value="/pageableExam", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Exam>> listPageable(Pageable pageable) {
		Page<Exam> exams = service.listPageable(pageable);
		return new ResponseEntity<>(exams, OK);
	}

	@GetMapping("/hateoas/{id}")
	public EntityModel<Exam> findByIdHateoas(@PathVariable("id") Integer id) {
		Exam exa = service.findById(id);
		EntityModel<Exam> resource = EntityModel.of(exa);
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
		WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());
		resource.add(link1.withRel("exam-info1"));
		resource.add(link2.withRel("exam-full"));
		return resource;
	}
}