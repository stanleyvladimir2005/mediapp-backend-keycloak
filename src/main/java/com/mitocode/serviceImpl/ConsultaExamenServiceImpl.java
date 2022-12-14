package com.mitocode.serviceImpl;

import com.mitocode.model.ConsultaExamen;
import com.mitocode.repo.IConsultaExamenRepo;
import com.mitocode.service.IConsultaExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaExamenServiceImpl implements IConsultaExamenService{

	@Autowired
	private IConsultaExamenRepo repo;
	
	@Override
	public List<ConsultaExamen> listarExamenesPorConsulta(Integer idConsulta) {
		return repo.listarExamenesPorConsulta(idConsulta);
	}
}