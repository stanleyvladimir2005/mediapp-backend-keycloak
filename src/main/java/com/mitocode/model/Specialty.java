package com.mitocode.model;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "specialty")
@SQLDelete(sql = "UPDATE specialty SET status = false WHERE id_specialty = ?")
@Where(clause = "status = true")
public class Specialty {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idSpecialty;
	
	@Column(name = "specialtyName", nullable = false, length = 50)
	@Size(min=3, message ="{especialidad_name.size}")
	private String specialtyName;
	
	@Column(name = "status")
	private Boolean status;
}