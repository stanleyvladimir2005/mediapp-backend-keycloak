package com.mitocode.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@Entity
@IdClass(ConsultExamPK.class)
public class ConsultExam {

	@Id
	private Exam exam;

	@Id
	private Consult consult;
}