package com.mitocode.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "rol")
public class Rol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idRol;

	@Column(nullable = true, length = 50)
	@Size(min = 3, message = "Apellidos debe tener minimo 3 caracteres")
	private String name;

	@Column(nullable = false, length = 150)
	private String description;
}