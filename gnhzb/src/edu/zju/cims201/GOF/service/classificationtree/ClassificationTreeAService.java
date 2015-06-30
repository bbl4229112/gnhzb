package edu.zju.cims201.GOF.service.classificationtree;

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
import edu.zju.cims201.GOF.dao.draft.TreeDraftADao;
import edu.zju.cims201.GOF.hibernate.pojoA.ClassificationTreeA;
import edu.zju.cims201.GOF.hibernate.pojoA.PartA;
import edu.zju.cims201.GOF.hibernate.pojoA.TreeDraftA;
import edu.zju.cims201.GOF.util.Constants;

@Service
@Transactional
public class ClassificationTreeAService {
	
	private ClassificationTreeADao classificationTreeADao;
	private TreeDraftADao treeDraftADao;
	
	/**
	 * 服务方法
	 * 
	 */
	public void save(ClassificationTreeA tree){
		classificationTreeADao.save(tree);
	}
	/**
	 * 服务方法
	 */
	public ClassificationTreeA findUniqueByUuid(String uuid) {
		// TODO Auto-generated method stub
		 return classificationTreeADao.findUniqueBy("uuid", uuid);
	}
	
	public ClassificationTreeADao getClassificationTreeADao() {
		return classificationTreeADao;
	}
	
	@Autowired
	public void setClassificationTreeADao(
			ClassificationTreeADao classificationTreeADao) {
		this.classificationTreeADao = classificationTreeADao;
	}
	public TreeDraftADao getTreeDraftADao() {
		return treeDraftADao;
	}
	@Autowired
	public void setTreeDraftADao(TreeDraftADao treeDraftADao) {
		this.treeDraftADao = treeDraftADao;
	}
	/**
	 * 服务方法
	 * @param treeNodeList
	 * @param treeDraftList
	 * @param zipFile
	 * @param b
	 * @throws Exception 
	 */
	public void saveTreeNodesModelOrSelf(List<ClassificationTreeA> treeNodeList,
			List<TreeDraftA> treeDraftList, DataHandler zipFile, boolean isModel) throws Exception {

		String sql = "from TreeDraftA draft where draft.ismaster = 1 and draft.classificationTree.uuid = ?";
		String path = Constants.UPLOADMODEL_PATHA;
		if(!isModel){
			sql = "from TreeDraftA draft where draft.ismaster = 0 and draft.classificationTree.uuid = ?";
			path = Constants.UPLOADSELF_PATHA;
		}
		List<ClassificationTreeA> treeNodes = new ArrayList<ClassificationTreeA>();
		//删除原有的节点模型的信息和文档
		for(ClassificationTreeA treeNode:treeNodeList){
			ClassificationTreeA treeNodeExist = classificationTreeADao.findUniqueBy("uuid",treeNode.getUuid());
			if(treeNodeExist == null){
				throw new Exception("同步树节点模型信息出错，不存在对应的分类树节点!");
			}
			//将节点提前选出来，用于维护新的节点模型信息与之对应的关系
			treeNodes.add(treeNodeExist);
			//相处对应节点的模型
			List<TreeDraftA> draftList = treeDraftADao.find(sql,treeNode.getUuid());
			for(TreeDraftA draft:draftList){
				File file = new File(path+draft.getFileName());
				if(file.exists()){
					file.delete();
				}
				treeDraftADao.delete(draft);
			}
		}
		if(treeDraftList.size()>0){
			File file1 = new File(path);
			if(!file1.exists()){
				file1.mkdirs();
			}
			//添加新的零件模型信息
			for(TreeDraftA draft:treeDraftList){
				//维护零件模型与零件对应的关系
				for(ClassificationTreeA treeNode :treeNodes){
					if(treeNode.getUuid().equals(draft.getClassificationTreeUuid())){
						draft.setClassificationTree(treeNode);
						break;
					}
				}
				draft.setId(0);
				treeDraftADao.save(draft);
			}
			//添加新的零件的文档
			DataSource dataSource = zipFile.getDataSource();
			InputStream is = dataSource.getInputStream();
			ZipInputStream zis = new ZipInputStream(is);
			ZipEntry ze = null;
			FileOutputStream fos = null;
			while((ze = zis.getNextEntry()) !=null){
				File file = new File(path+ze.getName());
				
				fos = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int count = 0;
				while((count = zis.read(buffer)) != -1){
					fos.write(buffer, 0, count);
				}
				fos.close();
			}
			zis.close();
			is.close();
		}
	}
	public void saveTreeNodesImg(List<ClassificationTreeA> treeNodeList, DataHandler zipFile) {
		// TODO Auto-generated method stub
		//更新示意图路径,删除原有示意图,保存现有示意图
		for(ClassificationTreeA treeNode:treeNodeList){
			String treeNodeUuid = treeNode.getUuid();
			ClassificationTreeA treeNodeExist = classificationTreeADao.findUniqueBy("uuid",treeNodeUuid);
			String newImgUrl = treeNode.getImgUrl();
			String oldImgUrl = treeNodeExist.getImgUrl();
			if(oldImgUrl != null && !"".equals(oldImgUrl)){
				File fileExist = new File(Constants.FILEROOTA+oldImgUrl);
				if(fileExist.exists()){
					fileExist.delete();
				}
			}
			treeNodeExist.setImgUrl(newImgUrl);
		}
		DataSource dataSource = zipFile.getDataSource();
		try {
			InputStream is = dataSource.getInputStream();
			ZipInputStream zis = new ZipInputStream(is);
			ZipEntry ze = null;
			while((ze = zis.getNextEntry())!=null){
				File f = new File(Constants.UPLOADMODELIMG_PATHA);
				if(!f.exists()){
					f.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(Constants.UPLOADMODELIMG_PATHA+ze.getName());
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
	
}
