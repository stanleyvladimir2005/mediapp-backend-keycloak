package com.mitocode.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idRole;

	@Column(nullable = true, length = 50)
	@Size(min = 3)
	private String name;

	@Column(nullable = false, length = 150)
	private String description;
}