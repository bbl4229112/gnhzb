package edu.zju.cims201.GOF.service.template;

import java.util.List;

import edu.zju.cims201.GOF.hibernate.pojoA.Template;



public interface TemplateService {
	
	public void saveTemplate(Template template);
	public List<Template> findTemplates();
	public Template getTemplate(Long id);
	public  String getTemplateFileName(Long id);
}
