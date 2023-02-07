package com.mitocode.service;

import com.mitocode.dto.ConsultListExamDTO;
import com.mitocode.dto.ConsultProductDTO;
import com.mitocode.model.Consult;
import java.time.LocalDateTime;
import java.util.List;

public interface IConsultService extends ICRUD <Consult, Integer> {
	
	Consult saveTransactional(ConsultListExamDTO consultDTO);
	List<Consult> search(String dni, String fullname);
	List<Consult> searchByDates(LocalDateTime date1, LocalDateTime date2);
	List<ConsultProductDTO> callProcedureOrFunction();
	byte[] generateReport() throws Exception;
}