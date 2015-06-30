package edu.zju.cims201.GOF.web.part;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.rs.dto.PartDTO;
import edu.zju.cims201.GOF.service.part.PartService;
import edu.zju.cims201.GOF.util.JSONUtil;

public class PartAction extends ActionSupport implements ServletResponseAware {


	private static final long serialVersionUID = -8217368043507611227L;
	private HttpServletResponse response;
	private PartService partService;
	PrintWriter out;
	
	private int index;
	private int size;
	private long treeId;
	private long id;
	private String partIds;
	private String partNumber;
	private String partName;
	private String description;
	
	
	public void getUnArrangePart() throws IOException{
		PageDTO pd =partService.getUnArrangePart(index, size);
		String partsStr = JSONUtil.write(pd);
		out =response.getWriter();
		out.print(partsStr);
	}
	/**
	 * 获取已分类零部件，进行分页
	 * @throws IOException
	 */
	public void getArrangedPart() throws IOException{
		PageDTO pd =partService.getArrangedPart(index, size, treeId);
		String partsStr =JSONUtil.write(pd);
		out= response.getWriter();
		out.print(partsStr);
	}
	
	public void getArrangedPart2() throws IOException{
		List<PartDTO> ps = partService.getArrangePart(treeId);
		String partsStr = JSONUtil.write(ps);
		out = response.getWriter();
		out.print(partsStr);
	}
	
	public void arrangingPart() throws IOException,HibernateException, SQLException{
		String partIdsStr = partIds.substring(1, partIds.length()-1);
		String[] partIdsArrStr = partIdsStr.split(",");
		int length = partIdsArrStr.length;
		long[] partIdsArr = new long[length];
		for(int i = 0;i<length;i++){
			partIdsArr[i]=Long.parseLong(partIdsArrStr[i]);
		}
		
		String msg =partService.arrangingPart(partIdsArr, treeId);
		out =response.getWriter();
		out.print(msg);
		
	}
	
	public void deArrangingPart() throws IOException, HibernateException, SQLException{
		String partIdsStr =partIds.substring(1,partIds.length()-1);
		String[] partIdsArrStr = partIdsStr.split(",");
		int length =partIdsArrStr.length;
		long[] partIdsArr = new long[length];
		for(int i =0;i<length;i++){
			partIdsArr[i] = Long.parseLong(partIdsArrStr[i]);
		}
		
		String msg = partService.deArrangingPart(partIdsArr,treeId);
		out =response.getWriter();
		out.print(msg);	
	}
	
	public void edtiPartDes() throws IOException{
		String msg =partService.edtiPartDes(treeId, description);
		out =response.getWriter();
		out.print(msg);	
	}
	
	public void createNewPart() throws IOException, HibernateException, SQLException{
		String msg =partService.createNewPart(treeId,partNumber,partName, description);
		out =response.getWriter();
		out.print(msg);	
	}
	
	public void deletePart() throws IOException, HibernateException, SQLException{
		String msg =partService.deletePart(id,treeId);
		out =response.getWriter();
		out.print(msg);	
	}
	
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	public long getTreeId() {
		return treeId;
	}
	public void setTreeId(long treeId) {
		this.treeId = treeId;
	}
	public String getPartIds() {
		return partIds;
	}
	public void setPartIds(String partIds) {
		this.partIds = partIds;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public PartService getPartService() {
		return partService;
	}
	@Autowired
	public void setPartService(PartService partService) {
		this.partService = partService;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		this.response=arg0;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

}
