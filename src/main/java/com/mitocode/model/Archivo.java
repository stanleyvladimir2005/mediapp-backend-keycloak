package com.mitocode.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "archivo")
public class Archivo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idArchivo;

	private String filename;
	private String filetype;
	private byte[] value;
}
