package edu.zju.cims201.GOF.service.systemFile;

import java.io.File;
import java.sql.Blob;

import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.SystemFile;

public interface FileService {
	
	
	public Long save(File file,String filename);	
	public SystemFile getFile(Long id);
	public SystemFile getKnowledgeSourceFile(Long id);
	public SystemFile getFile(String filename,String filetype) ;
	public String convertFile(String filename,String flashname,String sourceext) ;
	public MetaKnowledge getKnowledgebySourcefile(String filename,String filetype );
}
