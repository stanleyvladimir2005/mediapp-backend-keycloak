package com.mitocode.model;

import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serial;
import java.io.Serializable;


@EqualsAndHashCode
@Embeddable
public class ConsultExamPK implements Serializable{

	@Serial
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@EqualsAndHashCode.Include
	@JoinColumn(name = "id_exam", nullable = false)
	private Exam exam;
	
	@ManyToOne
	@EqualsAndHashCode.Include
	@JoinColumn(name = "id_consult", nullable = false)
	private Consult consult;
}