package edu.zju.cims201.GOF.web.server;

import java.util.List;

import javax.activation.DataHandler;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import edu.zju.cims201.GOF.hibernate.pojoA.ClassificationTreeA;
import edu.zju.cims201.GOF.hibernate.pojoA.PartA;



public class ModuleService {
	/**
	 * 属于设计资源的方法
	 * @param boms
	 * @return
	 */
	public boolean saveBom(String boms){
		BomService bomService  = new BomService();
		return bomService.saveBom(boms);
	}
	/**
	 * 属于设计资源的方法
	 * @param parts
	 * @return
	 * @throws Exception
	 */
	public boolean savePartsBasic(String parts) throws Exception{
		PartSynService partSynS = new PartSynService();
		return partSynS.savePartsBasic(parts);
	}
	
	public boolean savePartsImg(String parts,DataHandler fileZip){
		PartSynService partSynS = new PartSynService();
		return partSynS.savePartsImg(parts, fileZip);
	}
	
	public boolean savePartsModel(String parts,String partDrafts,DataHandler zipFile) throws Exception{
		PartSynService partSynS = new PartSynService();
		return partSynS.savePartsModel(parts, partDrafts,zipFile);
	}
	public boolean savePartsSelf(String parts,String partDrafts,DataHandler zipFile) throws Exception{
		PartSynService partSynS = new PartSynService();
		return partSynS.savePartsSelf(parts, partDrafts,zipFile);
	}
	
	/**
	 * 属于设计资源的方法
	 * @param instanceBoms
	 * @return
	 * @throws Exception
	 */
	public boolean saveInstanceBoms(String instanceBoms) throws Exception {
		InstanceBomService instanceBomService = new InstanceBomService();
		return instanceBomService.saveInstanceBoms(instanceBoms);
	}
	/**
	 * 属于设计资源的方法
	 * @param treeNodes
	 * @param treeDrafts
	 * @param zipFile
	 * @return
	 * @throws Exception
	 */
	public boolean saveTreeNodesModel(String treeNodes,String treeDrafts,DataHandler zipFile) throws Exception{
		BomDraftService bomDraftService = new BomDraftService();
		return bomDraftService.saveTreeNodesModel(treeNodes,treeDrafts,zipFile);
	}
	
	public boolean saveTreeNodesSelf(String treeNodes,String treeDrafts,DataHandler zipFile) throws Exception{
		BomDraftService bomDraftService = new BomDraftService();
		return bomDraftService.saveTreeNodesSelf(treeNodes,treeDrafts,zipFile);
	}
	/**
	 * 属于设计资源集成的方法
	 * @param treeNodes
	 * @param zipFile
	 * @return
	 */
	public boolean saveTreeNodesImg(String treeNodes,DataHandler zipFile){
		BomDraftService bomDraftService = new BomDraftService();
		return bomDraftService.saveTreeNodesImg(treeNodes,zipFile);
	}
	
	
	/**
	 * 属于模块化的方法
	 */
	public void sendBom(){
		BomService bomService  = new BomService();
		bomService.sendBom();
	}
	/**
	 * 属于模块化的方法
	 */
	public void sendParts2SynBasic(){
		PartSynService partSynService = new PartSynService();
		partSynService.synBasic();
	}
	/**
	 * 属于模块化的方法
	 */
	public void sendPart2SynImg(){
		PartSynService partSynService = new PartSynService();
		partSynService.synImg();
	}
	/**
	 * 属于模块化的方法
	 */
	public void sendPart2SynModel(){
		PartSynService partSynService = new PartSynService();
		partSynService.synModel();
	}
	/**
	 * 属于模块化的方法
	 */
	public void sendPart2SynSelf(){
		PartSynService partSynService = new PartSynService();
		partSynService.synSelf();
	}
	/**
	 * 属于模块化的方法
	 */
	public void sendInstanceBoms2Syn(){
		InstanceBomService instanceBomService = new InstanceBomService();
		instanceBomService.sendInstanceBom();
	}
	/**
	 * 属于模块化的方法
	 */
	public void sendTreeNodes2SynModel(){
		BomDraftService bomDraftService = new BomDraftService();
		bomDraftService.synModel();
	}
	/**
	 * 属于模块化的方法
	 */
	public void sendTreeNodes2SynSelf(){
		BomDraftService bomDraftService = new BomDraftService();
		bomDraftService.synSelf();
	}
	/**
	 * 属于模块化的方法
	 */
	public void sendTreeNodes2SynImg(){
		BomDraftService bomDraftService = new BomDraftService();
		bomDraftService.synImg();
	}
	
}
