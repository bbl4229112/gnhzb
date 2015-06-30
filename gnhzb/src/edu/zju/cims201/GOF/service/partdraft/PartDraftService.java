package edu.zju.cims201.GOF.service.partdraft;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.zju.cims201.GOF.hibernate.pojoA.PartDraft;

public interface PartDraftService {
	public String uploadPartImg(Long partId,File pic,String picFileName);
	public String getPartImgUrl(long partId);
	public String uploadPartModel(long partId, String draftName,String description, File file, String fileFileName) throws Exception;
	public List<PartDraft>  getDraftByPartId(long partId);
	public String uploadPartSelf(long partId, String selfName, String description,File self, String selfFileName, String typeName) throws Exception; 
	public boolean isMasterByFileName(String fileName);
	public String deleteDraft(long id, String fileName)throws IOException;
}
