package edu.zju.cims201.GOF.service.onto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import prefuse.owl2prefuse.ModelFlyWeightFactory;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import edu.zju.cims201.GOF.dao.knowledge.OwlWeblogDao;
import edu.zju.cims201.GOF.hibernate.pojo.OwlWedlog;
import edu.zju.cims201.GOF.service.ServiceException;
import edu.zju.cims201.GOF.util.Constants;

@Service
public class OntologyServiceImpl implements OntologyService{
	
	
	private  HashMap<String, OntModel> model_1_map=null; 
	private  HashMap<String, OntModel> model_2_map=null; 
	private  HashMap<String, List<OntProperty>> ontProperty_map=null; 
	@Resource(name="ontoFileServiceImpl")
	private  OntoFileService FileService;
	@Resource(name="owlWeblogDao")
	private  OwlWeblogDao owllogdao;
	public  OntModel getModel_1(String owlfile){
		
		if(model_1_map==null){
			model_1_map=new HashMap<String, OntModel>();
			
			OntModel model_temp = addModelToMap(owlfile);
			return model_temp;	
			
		}else {
			
			if(owlfile.equals("default")){
				String defaultKey=Constants.LOCAL_ONTO_FILE_PATH.replace(Constants.LOCAL_ONTO_FILE_DIR_PATH+"\\", "");
				if(model_1_map.containsKey(defaultKey)){
					return model_1_map.get(defaultKey);
				}else{
					OntModel model_temp = addModelToMap(defaultKey);
					return model_temp;
				}
			}else{
				if(model_1_map.containsKey(owlfile)){
					//System.out.println("从map中得到"+owlfile);
					return model_1_map.get(owlfile);
				}else{
					OntModel model_temp = addModelToMap(owlfile);
					return model_temp;
				}
			}		
		}
	}
	private List<OntProperty> addOntPropertyList(String owlfile,OntClass Ontclass)
	{
		List<OntProperty> ontoproplist=new ArrayList<OntProperty>();
		
		//System.out.println("开始");
		ExtendedIterator propsIT = Ontclass.listDeclaredProperties();
		//System.out.println("开始循环");
		while(propsIT.hasNext()){
			//System.out.println("结果");
			OntProperty prop=(OntProperty) propsIT.next();
			ontoproplist.add(prop);
		}
		ontProperty_map.put(owlfile+"@"+Ontclass.getLocalName(), ontoproplist);
		return ontoproplist;
	}
	public  List<OntProperty> getOntPropertyList(String owlfile,OntClass Ontclass)
	{
		
		if(ontProperty_map==null){
			ontProperty_map=new HashMap<String, List<OntProperty>>();
			List<OntProperty> ontolist=addOntPropertyList( owlfile, Ontclass);
			return ontolist;	
			
		}else {
			
		
				if(ontProperty_map.containsKey(owlfile+"@"+Ontclass.getLocalName())){
					
					return ontProperty_map.get(owlfile+"@"+Ontclass.getLocalName());
				}else{
					List<OntProperty> ontolist=addOntPropertyList( owlfile, Ontclass);
					return ontolist;	
				}
				
		}
		
		
	}
	private OntModel addModelToMap(String owlfile ){
		OntModel model_1_temp=ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
		//System.out.println("model_1==null");
		try {
			FileInputStream file = new FileInputStream(FileService.getFile(owlfile));
			model_1_temp.read(file,null);
		} catch (FileNotFoundException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		if(owlfile.equals("default")){
			owlfile=Constants.LOCAL_ONTO_FILE_PATH.replace(Constants.LOCAL_ONTO_FILE_DIR_PATH+"\\", "");
		}
		model_1_map.put(owlfile, model_1_temp);
		//System.out.println("map不为空");
		//System.out.println("model_1_map put model "+owlfile);
		return model_1_temp;
	}
	
	
	public  OntModel getModel_2(String owlfile){
		OntModel model_temp = addModel2ToMap(owlfile);

		return model_temp;
//		if(model_2_map==null){
//			model_2_map=new HashMap<String, OntModel>();
//		//	System.out.println("map2为空");
//			OntModel model_temp = addModel2ToMap(owlfile);
//			return model_temp;	
//			
//		}else {
//			
//			if(owlfile.equals("default")){
//				String defaultKey=Constants.LOCAL_ONTO_FILE_PATH.replace(Constants.LOCAL_ONTO_FILE_DIR_PATH+"\\", "");
//				if(model_2_map.containsKey(defaultKey)){
//					//System.out.println("从map2得到default"+defaultKey);
//					return model_2_map.get(defaultKey);
//				}else{
//					OntModel model_temp = addModel2ToMap(defaultKey);
//					//model_temp.listClasses()
//					//System.out.println("加入map2得到default"+defaultKey);
//					return model_temp;
//				}
//			}else{
//				if(model_2_map.containsKey(owlfile)){
//					//System.out.println("从map中得到"+owlfile);
//					return model_2_map.get(owlfile);
//				}else{
//					OntModel model_temp = addModel2ToMap(owlfile);
//					return model_temp;
//				}
//			}		
//		}
	}
	
	private OntModel addModel2ToMap(String owlfile ){
		OntModel model_1_temp;

		if(owlfile.equals("default")){
	
			model_1_temp= ModelFlyWeightFactory.getInstance().getModel(Constants.LOCAL_ONTO_FILE_PATH);

		}
		else
		{	model_1_temp= ModelFlyWeightFactory.getInstance().getModel(Constants.LOCAL_ONTO_FILE_DIR_PATH+"\\"+owlfile);
		}

		return model_1_temp;
	}
	
	public void clearModel_1(String owlfile){
		//System.out.println("从map1清除"+owlfile);
		if(model_1_map!=null){
			if(owlfile.equals("default")){
				owlfile=Constants.LOCAL_ONTO_FILE_PATH.replace(Constants.LOCAL_ONTO_FILE_DIR_PATH+"\\", "");
			}
			if(model_1_map.containsKey(owlfile)){
				model_1_map.get(owlfile).getDocumentManager().clearCache();
				//System.out.println("从map1中remove"+owlfile);
				model_1_map.remove(owlfile);
				model_1_map.keySet().remove(owlfile);
			}
		}
		ModelFlyWeightFactory.getInstance().removeMode(Constants.LOCAL_ONTO_FILE_DIR_PATH+"\\"+owlfile);
	}
	
	
	public void clearModel_2(String owlfile){
		
		if(owlfile.equals("default")){
			owlfile=Constants.LOCAL_ONTO_FILE_PATH.replace(Constants.LOCAL_ONTO_FILE_DIR_PATH+"\\", "");
		}
		ModelFlyWeightFactory.getInstance().removeMode(Constants.LOCAL_ONTO_FILE_DIR_PATH+"\\"+owlfile);
	}
	
	public void clearModelAll(String owlfile){
		clearModel_1(owlfile);
		clearModel_2(owlfile);
	}
	
	public void writeOwlFile(String owlFileName) throws Exception{
		
		
		
		File newFile=FileService.getFile(owlFileName);
		if(!newFile.exists())
			newFile.createNewFile();
		try {
			FileOutputStream file= new FileOutputStream(newFile);
			OntModel ontModel = getModel_1(owlFileName);
			ontModel.write(file,"RDF/XML-ABBREV");
			file.close();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}finally{
		//	clearModel_1(owlFileName);
		}
		
	}

	public void writeOwlFile(String owlFileName, String oldfilename) throws Exception {
		// TODO 自动生成方法存根
		File newFile=FileService.getFile(owlFileName);
		if(!newFile.exists())
			newFile.createNewFile();
		try {
			FileOutputStream file= new FileOutputStream(newFile);
			OntModel ontModel = getModel_1(oldfilename);
			ontModel.write(file,"RDF/XML-ABBREV");
			file.close();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		
	}
	@Transactional
	public OwlWedlog addowlweblog(OwlWedlog log) {

		try {
			owllogdao.save(log);
			owllogdao.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("本体修改保存日志存储错误");
		}

		return log;
	}
	@Transactional
	public List<OwlWedlog> getowlweblog(String filename) {

		 List<OwlWedlog> owllist=new ArrayList<OwlWedlog>();
		 //owllist=owllogdao.findBy("owlfilename", filename);
			String queryString="from OwlWedlog m where m.owlfilename=? order by m.timesample desc";
			 owllist=owllogdao.createQuery(queryString, filename).list();
			 System.out.println("owllist length="+owllist.size());
		return owllist;
	}
}
