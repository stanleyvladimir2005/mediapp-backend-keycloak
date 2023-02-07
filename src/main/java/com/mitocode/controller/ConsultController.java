package com.mitocode.controller;

import com.mitocode.dto.ConsultDTO;
import com.mitocode.dto.ConsultListExamDTO;
import com.mitocode.dto.ConsultProductDTO;
import com.mitocode.dto.FilterConsultDTO;
import com.mitocode.model.Consult;
import com.mitocode.model.MediaFile;
import com.mitocode.service.IConsultService;
import com.mitocode.service.IMediaFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1/consults")
public class ConsultController {
	
	@Autowired
	private IConsultService service;
	
	@Autowired
	private IMediaFileService mediaFileService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Consult>> findAll() {
		List<Consult> consults =  service.findAll();
		return new ResponseEntity<>(consults, HttpStatus.OK);
	}
		
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> save(@Valid @RequestBody ConsultListExamDTO Consulta) {
		Consult obj = service.saveTransactional(Consulta);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdConsult()).toUri();
		return ResponseEntity.created(location).build();			
	}

	@PutMapping("/{id}")
	public ResponseEntity<Consult> update(@PathVariable("id") Integer id,@Valid @RequestBody Consult consult) {
		consult.setIdConsult(id);
		Consult cons = service.update(consult,id);
		return new ResponseEntity<>(cons,OK);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
	    return new ResponseEntity<>(OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Consult> findById(@PathVariable("id") Integer id) {
		Consult consult = service.findById(id);
		return new ResponseEntity<>(consult, OK);
	}
	
	@GetMapping(value="/pageable", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Consult>> listPageable(Pageable pageable) {
		Page<Consult> consulta  = service.listPageable(pageable);
		return new ResponseEntity<>(consulta, HttpStatus.OK);
	}
	
	@GetMapping(value = "/hateoas", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ConsultDTO> listarHateoas() {
		List<Consult> consults;
		List<ConsultDTO> consultDTO = new ArrayList<>();
		consults = service.findAll();

		for (Consult c : consults) {
			ConsultDTO d = new ConsultDTO();
			d.setIdConsulta(c.getIdConsult());
			d.setMedic(c.getMedic());
			d.setPatient(c.getPatient());

			WebMvcLinkBuilder linkTo = linkTo(methodOn(ConsultController.class).findById((c.getIdConsult())));
			d.add(linkTo.withSelfRel());

			WebMvcLinkBuilder linkTo1 = linkTo(methodOn(PatientController.class).findById((c.getPatient().getIdPatient())));
			d.add(linkTo1.withSelfRel());

			WebMvcLinkBuilder linkTo2 = linkTo(methodOn(MedicController.class).findById((c.getMedic().getIdMedic())));
			d.add(linkTo2.withSelfRel());
			consultDTO.add(d);
		}
		return consultDTO;
	}

	@PostMapping("search/others")
	public ResponseEntity<List<Consult>> search(@RequestBody FilterConsultDTO filter) {
		List<Consult> consults = service.search(filter.getDui(), filter.getFullName());
		return new ResponseEntity<>(consults, OK);
	}

	@GetMapping("/search/date")
	public ResponseEntity<List<Consult>> searchByDates(@RequestParam(value = "date1") String date1, @RequestParam(value = "date2") String date2){
		List<Consult> consults = service.searchByDates(LocalDateTime.parse(date1), LocalDateTime.parse(date2));
		return new ResponseEntity<>(consults, OK);
	}

	@GetMapping("/callProcedure")
	public ResponseEntity<List<ConsultProductDTO>> callProcOrFunction(){
		List<ConsultProductDTO> consults = service.callProcedureOrFunction();
		return new ResponseEntity<>(consults, OK);
	}

	@GetMapping(value = "/generateReport", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE) // APPLICATION_PDF_VALUE
	public ResponseEntity<byte[]> generateReport() throws Exception{
		byte[] data;
		data = service.generateReport();
		return new ResponseEntity<>(data, OK);
	}

	@PostMapping(value = "/saveFile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Void> saveFile(@RequestParam("file") MultipartFile file) throws Exception{
		MediaFile mf = new MediaFile();
		mf.setFileType(file.getContentType());
		mf.setFileName(file.getOriginalFilename());
		mf.setValue(file.getBytes());
		mediaFileService.save(mf);
		return new ResponseEntity<>(OK);
	}

	@GetMapping(value = "/readFile/{idFile}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> readFile(@PathVariable("idFile") Integer idFile) {
		byte[] arr = mediaFileService.findById(idFile).getValue();
		return new ResponseEntity<>(arr, OK);
	}
}