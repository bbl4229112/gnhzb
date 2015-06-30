package edu.zju.cims201.GOF.web.biaozhun;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.orm.Page;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.hibernate.pojoA.Biaozhun;
import edu.zju.cims201.GOF.hibernate.pojo.Knowledge;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.rs.dto.BiaozhunDTO;
import edu.zju.cims201.GOF.rs.dto.KnowledgeDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.service.biaozhun.BiaozhunService;
import edu.zju.cims201.GOF.util.JSONUtil;

@Namespace("/standard")
public class BiaozhunAction extends ActionSupport implements ServletResponseAware{
	
	private HttpServletResponse response;
	private BiaozhunService biaozhunService;
	private String key;
	private int size;
	private int index;
	
	public String listBiaozhuns() {
		
		Page<Biaozhun> page=null;
		if(this.size==0)
			 page=new Page<Biaozhun>(10);
		else
			page=new Page<Biaozhun>(this.size);
		page.setPageNo(this.index+1);	
        Page<Biaozhun> newpage = this.biaozhunService.searchBiaozhun(key, page);
		 List<Biaozhun> biozhunlist = newpage.getResult();			
		 List<BiaozhunDTO> dtos = new ArrayList<BiaozhunDTO>();
		 for (Biaozhun biaozhun : biozhunlist) {
			 BiaozhunDTO kdto = new BiaozhunDTO(biaozhun);
			 dtos.add(kdto);
		 }		 
		 
		 PageDTO pagedto = new PageDTO();
		 pagedto.setData(dtos);
		 pagedto.setTotal(page.getTotalCount());

		 JSONUtil ju  = new JSONUtil();
		 ju.write(response, pagedto);
		 
		 return null;

	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
		
	}
	public BiaozhunService getBiaozhunService() {
		return biaozhunService;
	}

	@Autowired
	public void setBiaozhunService(BiaozhunService biaozhunService) {
		this.biaozhunService = biaozhunService;
	}
	

}
