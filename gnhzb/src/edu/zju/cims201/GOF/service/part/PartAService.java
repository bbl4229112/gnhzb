package edu.zju.cims201.GOF.service.part;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.classificationtree.ClassificationTreeADao;
import edu.zju.cims201.GOF.dao.part.PartADao;
import edu.zju.cims201.GOF.dao.partdraft.PartDraftADao;
import edu.zju.cims201.GOF.hibernate.pojoA.ClassificationTreeA;
import edu.zju.cims201.GOF.hibernate.pojoA.PartA;
import edu.zju.cims201.GOF.hibernate.pojoA.PartDraftA;
import edu.zju.cims201.GOF.util.Constants;
@Transactional
@Service
public class  PartAService {
	private PartADao partADao;
	private ClassificationTreeADao classificationTreeADao;
	private PartDraftADao partDraftADao;
	/**
	 * 设计资源：更新或者保存零部件基本信息
	 * @param parts 所有需要更新或者保存的零部件
	 * @throws Exception 同步异常的问题
	 */
	public void savePartsBasic(List<PartA> parts) throws Exception{
		/*
		 * 已有零件？
		 * 有附属模块？
		 */
		for(PartA part : parts){
			//去除id,防止保存出错。
			part.setId(0);
			String partUuid = part.getUuid();
			
			//判断数据库中是否有零件
			PartA partExist = partADao.findUniqueBy("uuid", partUuid);
			//该module有来自模块化的 自身id
			ClassificationTreeA module = part.getClassificationTree();
			if(partExist != null){
				part.setId(partExist.getId());
			}
			
			if(module != null){
				String moduleUuid = module.getUuid();
				ClassificationTreeA moduleExist = classificationTreeADao.findUniqueBy("uuid", moduleUuid);
				if(moduleExist == null){
					throw new Exception("数据库中无零件相对应的模块，同步出错。");
				}else{
					part.setClassificationTree(moduleExist);
				}
			}
			if(part.getId()==0){
				partADao.save(part);
				continue;
			}
			partADao.getSession().merge(part);
		}
	}
	/**
	 * 设计资源：用于同步零件的示意图文档和示意图路径信息
	 * @param partList  需要更新的零件集合
	 * @param fileZip	示意图文件压缩包
	 */
	public void savePartsImg(List<PartA> partList, DataHandler fileZip) {
		// TODO Auto-generated method stub
		//更新示意图路径,删除原有示意图,保存现有示意图
		for(PartA part:partList){
			String partUuid = part.getUuid();
			PartA partExist =partADao.findUniqueBy("uuid",partUuid);
			String newImgUrl = part.getImgUrl();
			String oldImgUrl = partExist.getImgUrl();
			if(oldImgUrl != null && !"".equals(oldImgUrl)){
				File fileExist = new File(Constants.FILEROOTA+oldImgUrl);
				if(fileExist.exists()){
					fileExist.delete();
				}
			}
			partExist.setImgUrl(newImgUrl);
		}
		DataSource dataSource = fileZip.getDataSource();
		try {
			InputStream is = dataSource.getInputStream();
			ZipInputStream zis = new ZipInputStream(is);
			ZipEntry ze = null;
			while((ze = zis.getNextEntry())!=null){
				File f = new File(Constants.UPLOADPARTIMG_PATHA);
				if(!f.exists()){
					f.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(Constants.UPLOADPARTIMG_PATHA+ze.getName());
				byte[] buffer = new byte[1024];
				int count;
				while((count = zis.read(buffer))!=-1){
					fos.write(buffer, 0, count);
				}
				fos.close();
			}
			zis.close();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 设计资源：用于同步零件主模型信息和主模型文档或者零件自定义文档信息和自定义文档
	 * @param partList
	 * @param partDraftList
	 * @param zipFile
	 * @param isModel true 零件模型，false 零件自定义文档
	 * @throws Exception 
	 */
	public void savePartsModelOrSelf(List<PartA> partList,
		List<PartDraftA> partDraftList, DataHandler zipFile,boolean isModel) throws Exception {
		// TODO Auto-generated method stub
		String sql = "from PartDraftA draft where draft.ismaster =1 and draft.partUuid = ?";
		String path = Constants.UPLOADPARTMODEL_PATHA;
		if(!isModel){
			sql = "from PartDraftA draft where draft.ismaster = 0 and draft.partUuid = ?";
			path = Constants.UPLOADPARTSELF_PATHA;
		}
		List<PartA> parts = new ArrayList<PartA>();
		//删除原有的零件模型信息和文档
		for(PartA part:partList){
			PartA partExist = partADao.findUniqueBy("uuid",part.getUuid());
			if(partExist == null){
				throw new Exception("同步零件模型信息出错，不存在对应的零件!");
			}
			//将零件提前选出来，用于维护新的零件模型信息与之对应的关系
			parts.add(partExist);
			//相处对应零件的模型
			List<PartDraftA> draftList = partDraftADao.find(sql,part.getUuid());
			for(PartDraftA draft:draftList){
				File file = new File(path+draft.getFileName());
				if(file.exists()){
					file.delete();
				}
				partDraftADao.delete(draft);
			}
		}
		if(partDraftList.size()>0){
			File file1 = new File(path);
			if(!file1.exists()){
				file1.mkdirs();
			}
			//添加新的零件模型信息
			for(PartDraftA draft:partDraftList){
				//维护零件模型与零件对应的关系
				for(PartA part :parts){
					if(part.getUuid().equals(draft.getPartUuid())){
						draft.setPart(part);
						break;
					}
				}
				draft.setId(0);
				partDraftADao.save(draft);
			}
			//添加新的零件的文档
			DataSource dataSource = zipFile.getDataSource();
			InputStream is = dataSource.getInputStream();
			ZipInputStream zis = new ZipInputStream(is);
			ZipEntry ze = null;
			FileOutputStream fos = null;
			while((ze = zis.getNextEntry()) !=null){
				File file = new File(path+ze.getName());
				
				/*if(!file.exists()){
					file.createNewFile();
				}*/
				fos = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int count = 0;
				while((count = zis.read(buffer))!=-1){
					fos.write(buffer, 0, count);
				}
				fos.close();
			}
			zis.close();
			is.close();
		}
	}
	
	public PartADao getPartADao() {
		return partADao;
	}
	@Autowired
	public void setPartADao(PartADao partADao) {
		this.partADao = partADao;
	}


	public ClassificationTreeADao getClassificationTreeADao() {
		return classificationTreeADao;
	}

	@Autowired
	public void setClassificationTreeADao(
			ClassificationTreeADao classificationTreeADao) {
		this.classificationTreeADao = classificationTreeADao;
	}
	public PartDraftADao getPartDraftADao() {
		return partDraftADao;
	}
	@Autowired
	public void setPartDraftADao(PartDraftADao partDraftADao) {
		this.partDraftADao = partDraftADao;
	}
}
