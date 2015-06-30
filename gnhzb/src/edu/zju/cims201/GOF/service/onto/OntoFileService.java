package edu.zju.cims201.GOF.service.onto;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.zju.cims201.GOF.rs.dto.OWLFileDTO;

public interface OntoFileService {
	
	public File getFile(String owlFileName);
	public String getFileDIR();
	
	public File[] getDirFile(String dir);
	public List<OWLFileDTO> getOWLFile(String dir);
	
	public boolean deleteFile(String filepath);
	public void setLocalFilePath(String localFilePath )throws IOException;
	
	public String getVersionName();
	
	public String getLocalFilePath();
}
