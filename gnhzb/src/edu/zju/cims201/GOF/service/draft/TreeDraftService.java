package edu.zju.cims201.GOF.service.draft;

import java.io.File;
import java.util.List;

import edu.zju.cims201.GOF.hibernate.pojoA.TreeDraft;

public interface TreeDraftService {
	
	public String uploadModel(Long treeId,String draftName,String description,File file,String fileFileName) throws Exception;
	public String uploadSelf(Long treeId,String selfName,String description,File self,String selfFileName,String typeName) throws Exception;
	public List<TreeDraft>  getModelByTreeId(Long treeId);
	public List<TreeDraft> 	getSelfByTreeId(Long treeId);
	public String deleteModel(Long id,String fileName);
	public String deleteSelfDefiDoc(Long id,String fileName);
	public String uploadImg(Long treeId,File pic,String picFileName);
	public String getModelImgUrl(Long treeId);
	
}

