package edu.zju.cims201.GOF.service.onto;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;



import org.springframework.stereotype.Service;

import edu.zju.cims201.GOF.rs.dto.OWLFileDTO;
import edu.zju.cims201.GOF.util.ClassLoaderUtil;
import edu.zju.cims201.GOF.util.ConfigUtil;
import edu.zju.cims201.GOF.util.Constants;


@Service
public class OntoFileServiceImpl implements OntoFileService {
	
	private String fileBaseName=Constants.FILEBASENAME;
	private  String fileDIR=Constants.LOCAL_ONTO_FILE_DIR_PATH;
	//用户可以指定某一文件路径为当前系统owl文件
	private  String localFilePath=Constants.LOCAL_ONTO_FILE_PATH;
	
	private  ConfigUtil configUtil=Constants.config;
	
	public void setFileDIR(String fileDIR){
		this.fileDIR=fileDIR;
	}
	public String getFileDIR(){
		return fileDIR;
	}
	
	public void setFileBaseName(String fileBaseName){
		this.fileBaseName=fileBaseName;
	}
	public String getFileBaseName(){
		return fileBaseName;
	}
	
	public void setLocalFilePath(String localFilePath) throws IOException{
		configUtil.setValue("local_ontofile_path", localFilePath);
		String path=ClassLoaderUtil.getExtendResource("application.properties").getFile();
		path=URLDecoder.decode(path, "utf-8");
		configUtil.saveFile(path, "changed");
		Constants.LOCAL_ONTO_FILE_PATH=localFilePath;
		this.localFilePath=localFilePath;
	}
	public String getLocalFilePath(){
		return localFilePath;
	}
	public File getFile(String owlFileName){
		if(owlFileName.equals("default")){
			return new File(localFilePath);
		}
		return new File(fileDIR,owlFileName);
	}
	
	public List<OWLFileDTO> getOWLFile(String dir){
		File dirFile=new File(dir);
		List<OWLFileDTO> owlfiles=new ArrayList<OWLFileDTO>();
		File[] files=null;
		if(dirFile.isDirectory()){
			files=dirFile.listFiles(new FilenameFilter(){
				public boolean accept(java.io.File dir, String name) {
					// TODO 自动生成方法存根
					if(name.toLowerCase().endsWith(".owl")&&(!name.toLowerCase().equals(Constants.FIELD_PATH.toLowerCase())))
						return true;
					return false;
				}
				
			});
		for (int i = 0; i < files.length; i++) {
			OWLFileDTO filedto=new OWLFileDTO();
			filedto.setFilename(files[i].getName());
			filedto.setLastchangetime(DateFormat.getDateInstance(DateFormat.FULL).format(new Date(files[i].lastModified())));
			owlfiles.add(filedto);
		}	
		}
		return owlfiles;
	}
	public File[] getDirFile(String dir){
		File dirFile=new File(dir);
		return dirFile.listFiles();
	}
	public boolean deleteFile(String filepath) {
		// TODO 自动生成方法存根
		File deleted=new File(fileDIR+"\\"+filepath);
		if(deleted.isFile()){
			return deleted.delete();
		}else {
			File[] files=deleted.listFiles();
			for(File f:files){
				if(!f.delete())
					return false;
			}
			return deleted.delete();
		}	
	}
	
	public String getVersionName(){
		DateFormat dateFormat=new SimpleDateFormat("yyMMddHHmmss");
		Date date =new Date();
		String suffix=dateFormat.format(date);
		String baseName=fileBaseName;
		System.out.println(baseName);
		String[] temp=baseName.split("\\u002E");
		String newName=temp[0]+suffix+"."+temp[1];
		return newName;
	}
}
