package com.mitocode.serviceImpl;

import com.mitocode.dto.ConsultListExamDTO;
import com.mitocode.dto.ConsultProductDTO;
import com.mitocode.model.Consult;
import com.mitocode.repo.IConsultExamRepo;
import com.mitocode.repo.IConsultRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.service.IConsultService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultServiceImpl extends CRUDImpl<Consult,Integer>  implements IConsultService {

	@Autowired
	private IConsultRepo repo;
	
	@Autowired
	private IConsultExamRepo ceRepo;

	@Override
	protected IGenericRepo<Consult, Integer> getRepo() {
		return repo;
	}

	@Override
	@Transactional
	public Consult saveTransactional(ConsultListExamDTO consultDTO) {
		//Se inserta la consulta con su detalle consulta
		Consult cons;
		consultDTO.getConsult().getDetails().forEach(x -> x.setConsult(consultDTO.getConsult()));
		cons = repo.save(consultDTO.getConsult());

		//Se inserta el examen..usamos la posicion en memoria de lo que insertamos en consulta
		consultDTO.getListExam().forEach(e -> ceRepo.saveExam(consultDTO.getConsult().getIdConsult(), e.getIdExam()));
		return cons;		
	}

	public List<Consult> search(String dni, String fullname) {
		return repo.search(dni, fullname);
	}

	@Override
	public List<Consult> searchByDates(LocalDateTime date1, LocalDateTime date2) {
		return repo.searchByDates(date1, date2);
	}

	@Override
	public List<ConsultProductDTO> callProcedureOrFunction() {
		List<ConsultProductDTO> consults = new ArrayList<>();
		repo.callProcedureOrFunction().forEach( x -> {
			ConsultProductDTO cr = new ConsultProductDTO();
			cr.setQuantity(Integer.parseInt(String.valueOf(x[0])));
			cr.setConsultDate(String.valueOf(x[1]));
			consults.add(cr);
		});
		return consults;
	}

	@Override
	public byte[] generateReport() {
		byte[] data = null;
		try {
			File file = new ClassPathResource("/reports/consultas.jasper").getFile();
			JasperPrint print = JasperFillManager.fillReport(file.getPath(), null, new JRBeanCollectionDataSource(this.callProcedureOrFunction()));
			data = JasperExportManager.exportReportToPdf(print);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}