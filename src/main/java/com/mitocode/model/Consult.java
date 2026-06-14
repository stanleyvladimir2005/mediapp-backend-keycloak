package com.mitocode.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "consult")
public class Consult {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idConsult;

	@ManyToOne
	@JoinColumn(name = "id_pacient", nullable = false)
	private Patient patient;

	@ManyToOne
	@JoinColumn(name = "id_medic", nullable = false)
	private Medic medic;

	@ManyToOne
	@JoinColumn(name = "id_specialty", nullable = false)
	private Specialty specialty;

	@Column(name="number_consult",length = 3, nullable = false)
	private String numberConsult;

	@JsonSerialize(using = ToStringSerializer.class) //iso date format
	@Column(name = "consult_date")
	private LocalDateTime consultDate;

	@OneToMany(mappedBy = "consult", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<ConsultDetail> details;
}