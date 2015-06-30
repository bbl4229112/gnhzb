package edu.zju.cims201.GOF.service.template;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.template.TemplateDao;
import edu.zju.cims201.GOF.hibernate.pojoA.Template;
import edu.zju.cims201.GOF.util.Constants;


@Service
@Transactional
public class TemplateServiceImpl implements TemplateService{
	
	private TemplateDao templateDao;
	
	public void saveTemplate(Template template) {
		// TODO Auto-generated method stub
		templateDao.save(template);
	}

	public TemplateDao getTemplateDao() {
		return templateDao;
	}
	
	@Autowired
	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	public List<Template> findTemplates() {
		// TODO Auto-generated method stub
		return templateDao.getAll();
	}

	public Template getTemplate(Long id) {
		
		return templateDao.get(id);
	}

	public String getTemplateFileName(Long id) {
		// TODO Auto-generated method stub
		Template template =templateDao.get(id);
		String fileName = Constants.TEMPLATE_PATH+template.getTemplateUuid()+File.separator+template.getXmlFileName();	
		return fileName;
	}

	
}
