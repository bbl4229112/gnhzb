package edu.zju.cims201.GOF.service.onto;

import java.util.List;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;

import edu.zju.cims201.GOF.hibernate.pojo.OwlWedlog;

public interface OntologyService {
	
	public  OntModel getModel_1(String owlfile);
	
	public  OntModel getModel_2(String owlfile);
	
	//public void clearModel_1(String owlfile);
	//public  OntModel getModel_2();
	public void clearModel_1(String owlfile);
	public void clearModel_2(String owlfile);
	public void clearModelAll(String owlfile);
	//public void clearModel_2();
	//public void clearModelAll();
	
	public void writeOwlFile(String filename) throws Exception;
	public void writeOwlFile(String filename,String oldfilename) throws Exception;
	public  List<OntProperty> getOntPropertyList(String owlfile,OntClass Ontclass);
	public OwlWedlog addowlweblog(OwlWedlog log);
	public List<OwlWedlog> getowlweblog(String filename);
}
