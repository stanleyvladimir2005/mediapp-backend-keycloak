package com.mitocode.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "archivo")
public class MediaFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idFile;
	private String fileName;
	private String fileType;
	private byte[] value;
}