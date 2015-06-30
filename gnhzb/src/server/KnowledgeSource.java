package server;

import java.io.File;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.annotation.Resource;

import edu.zju.cims201.GOF.hibernate.pojo.KnowledgeRelated;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemFile;
import edu.zju.cims201.GOF.service.knowledge.FullTextService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeRelatedService;
import edu.zju.cims201.GOF.service.knowledge.KnowledgeService;
import edu.zju.cims201.GOF.service.systemFile.FileService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.rs.dto.KnowledgeDTO;
import edu.zju.cims201.GOF.rs.dto.KnowledgeRelatedDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;

public class KnowledgeSource {
	
	@Resource(name = "knowledgeServiceImpl")
	private KnowledgeService kservice;
	@Resource(name = "fullTextServiceImpl")
	private FullTextService fulltextservice;
	@Resource(name = "knowledgeRelatedServiceImpl")
	private KnowledgeRelatedService krservice;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
		
	String filePosition = Constants.SOURCEFILE_PATH;	
	
	public String getFileByPath(Long knowledgeId){
      
        SystemFile file = fileService.getKnowledgeSourceFile(knowledgeId);
        Blob blob = file.getFileBinary();
        byte[] c = null;
        String xml = "";
        try {
        	InputStream ins = blob.getBinaryStream();
        	c = new byte[(int) blob.length()];
        	ins.read(c);   //这句还不能少，意思是读了c又对c做了改变吧。20120912.      	
        } catch(Exception e) {
        	e.printStackTrace();
        }
        try {       	
        	xml = new String(c, "UTF-8");
        } catch (Exception e) {
        	e.printStackTrace();
        }
//        System.out.println(xml);       
		return xml;
	}
	
	public List<KnowledgeDTO> getKnowledgeByTitle(String titleName) {
		List<MetaKnowledge> list = kservice.getKnowledgeByTitle(titleName);
		List<KnowledgeDTO> kdtos = new ArrayList<KnowledgeDTO>();
		for(int i=0;i<list.size();i++) {
			KnowledgeDTO kdto = new KnowledgeDTO();
			kdto.setId(list.get(i).getId());
			kdto.setTitleName(list.get(i).getTitlename());
			kdto.setUploaderName(list.get(i).getUploader().getName());
			kdto.setKnowledgeType(list.get(i).getKnowledgetype().getKnowledgeTypeName());
			kdto.setUploadTime(list.get(i).getUploadtime().toString().substring(0, 16));
			kdto.setKnowledgeSourceFilePath(list.get(i).getKnowledgesourcefilepath());
			kdtos.add(kdto);
		}
		return kdtos;
	}
	
	public List<KnowledgeDTO> getKnowledgeByTitle(String key, int index, int size) {
		String searchKey = key.replaceAll("  ", " ");
		PageDTO page=new PageDTO();		
		page.setFirstindex(index);
		page.setPagesize(size);
		page = fulltextservice.searchKnowledge(searchKey, page, null);
		List<KnowledgeDTO> list = page.getData();	
		List<KnowledgeDTO> kdtos = new ArrayList<KnowledgeDTO>();
		for(int i=0;i<list.size();i++) {
			KnowledgeDTO kdto = new KnowledgeDTO();
			MetaKnowledge mk = kservice.getMetaknowledge(list.get(i).getId());
			kdto.setId(mk.getId());
			kdto.setTitleName(mk.getTitlename());
			kdto.setUploadTime(mk.getUploadtime().toString().substring(0, 16));
//			kdto.setKnowledgeSourceFilePath(mk.getKnowledgeSourceFilePath());
			kdto.setUploaderName(mk.getUploader().getName());
			kdto.setKnowledgeType(mk.getKnowledgetype().getKnowledgeTypeName());
			kdtos.add(kdto);
		}
		return kdtos;
	}
	
	public long getKnowledgeTotal(String key, int index, int size) {
		String searchKey = key.replaceAll("  ", " ");
		PageDTO page=new PageDTO();		
		page.setFirstindex(index);
		page.setPagesize(size);
		page = fulltextservice.searchKnowledge(searchKey, page, null);

		return page.getTotal();
	}
	
	public long getKnowledgePages(String key, int index, int size) {
		String searchKey = key.replaceAll("  ", " ");
		PageDTO page=new PageDTO();		
		page.setFirstindex(index);
		page.setPagesize(size);
		page = fulltextservice.searchKnowledge(searchKey, page, null);
		
		return page.getTotalPage();
	}
	
	public List<KnowledgeRelatedDTO> KnowledgeRelatedList(Long xmlId, String figureId, String type) {
		String xmlFigureId = xmlId + "@" + figureId;		
		List<KnowledgeRelated> list = krservice.KnowledgeRelatedList(xmlFigureId, type);
		List<KnowledgeRelatedDTO> knowledgeRelatedDTOs = new ArrayList<KnowledgeRelatedDTO>();
		for(int i=0;i<list.size();i++) {
			MetaKnowledge k = kservice.getMetaknowledge(list.get(i).getKnowledgeId());
			KnowledgeRelatedDTO krdto = new KnowledgeRelatedDTO();			
			krdto.setId(list.get(i).getId());
			krdto.setKnowledgeId(list.get(i).getKnowledgeId());
			krdto.setTitleName(k.getTitlename());
			krdto.setUploader(k.getUploader().getName());
			krdto.setUploadTime(k.getUploadtime().toString().substring(0, 16));
			krdto.setKnowledgeType(k.getKnowledgetype().getKnowledgeTypeName());
			if(krdto.getKnowledgeType().equals("问题")) {
				krdto.setKnowledgeFilePath("知识类型为问题时无文件路径。");
			} else {				
				krdto.setKnowledgeFilePath(k.getKnowledgesourcefilepath().substring(k.getKnowledgesourcefilepath().lastIndexOf(".")));
			}
			knowledgeRelatedDTOs.add(krdto);
		}
		return knowledgeRelatedDTOs;
	}
	
	public String addRecord(Long xmlId, String figureId, Long knowledgeId, String type) {
		String xmlFigureId = xmlId + "@" + figureId;
		if(krservice.isKnowledgeRelatedExist(xmlFigureId, knowledgeId, type)) {
			return "该节点已经添加了该知识！";
		} else {
			KnowledgeRelated kr = new KnowledgeRelated();
			kr.setXmlFigureId(xmlFigureId);
			kr.setKnowledgeId(knowledgeId);
			kr.setType(type);
			kr.setCreateTime(new Date());
			String result = krservice.addKnowledgeRelated(kr);
			if(result == "1") {
				return "添加成功！";
			} else {
				return "添加失败！";
			}
		}
	}
	
	public String deleteRecord(Long id) {
		String result = krservice.deleteKnowledgeRelated(id);
		if(result == "1") {
			return "删除成功！";
		} else {
			return "删除失败！";
		}
	}
	
	private String getStringWithFileSeparator(String path){
		String s="";
		for(int i=0; i<path.length(); i++)
			if(path.charAt(i)=='\\'){
				s+=File.separator;
			}else{
				s+=path.charAt(i);
			}
		return s;
	}
}