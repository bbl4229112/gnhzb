package edu.zju.cims201.GOF.service.part;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.cims201.GOF.dao.classificationtree.ClassificationTreeDao;
import edu.zju.cims201.GOF.dao.part.PartDao;
import edu.zju.cims201.GOF.dao.partdraft.PartDraftDao;
import edu.zju.cims201.GOF.hibernate.pojoA.ClassificationTree;
import edu.zju.cims201.GOF.hibernate.pojoA.Part;
import edu.zju.cims201.GOF.hibernate.pojoA.PartDraft;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.rs.dto.PartDTO;
import edu.zju.cims201.GOF.util.Constants;
/**
 * 在没有执行创建事物特性表的功能的时候，不创建事物特性表，直到执行该功能才创建事物特性表，执行的时候，同时添加所有归类零部件的信息。
 * 同理于删除事物特性表。
 * 在归类、取消归类零部件和创建、删除零部件的时候，先判断是否有事物特性表，有的话执行相应操作，没有则不执行。
 * @author CQZ
 *
 */
@Service
@Transactional
public class PartServiceImpl implements PartService {
	
	private PartDao partDao;
	private ClassificationTreeDao classificationTreeDao;
	private PartDraftDao partDraftDao;
	
	public PartDao getPartDao() {
		return partDao;
	}
	@Autowired
	public void setPartDao(PartDao partDao) {
		this.partDao = partDao;
	}
	/**
	 * 不进行分页，直接根据treeId获取零部件记录
	 */
	public List<PartDTO> getArrangePart(long treeId){
		List<Part> parts =partDao.find("from Part p where p.isarranged=1 and p.classificationTree.id=?",treeId);
		List<PartDTO> partsList = new ArrayList<PartDTO>();
		for(Part p:parts){
			PartDTO partDTO =new PartDTO();
			partDTO.setId(p.getId());
			partDTO.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(p.getCreateTime()));
			partDTO.setDescription(p.getDescription());
			partDTO.setIsarranged(p.getIsarranged());
			partDTO.setPartBigVersion(p.getPartBigVersion());
			partDTO.setPartName(p.getPartName());
			partDTO.setPartNumber(p.getPartNumber());
			partDTO.setPartSmallVersion(p.getPartSmallVersion());
			partDTO.setState(p.getState());
			partsList.add(partDTO);
		}
		return partsList;
	}
	/**
	 * 获得已分类的零部件(分页显示)
	 * @param index 分页索引
	 * @param size 每页条数
	 * @param
	 * @return 返回页类信息（表格数据、总数据条数、总页数）
	 */
	public PageDTO getArrangedPart(int index, int size,long treeId){
		List<Part> parts =partDao.find("from Part p where p.isarranged=1 and p.classificationTree.id=?",treeId);
		List<PartDTO> partsList = new ArrayList<PartDTO>();
		for(Part p:parts){
			//partDao.getSession().evict(p);

			//p.setClassificationTree(null);
			//partsList.add(p);
			PartDTO partDTO =new PartDTO();
			partDTO.setId(p.getId());
			if(p.getCreateTime()!=null){	
				partDTO.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(p.getCreateTime()));
			}else{
				partDTO.setCreateTime("");
			}
			partDTO.setDescription(p.getDescription());
			partDTO.setIsarranged(p.getIsarranged());
			partDTO.setPartBigVersion(p.getPartBigVersion());
			partDTO.setPartName(p.getPartName());
			partDTO.setPartNumber(p.getPartNumber());
			partDTO.setPartSmallVersion(p.getPartSmallVersion());
			partDTO.setState(p.getState());
			partsList.add(partDTO);
		}
		List<PartDTO> subPartsList = new ArrayList<PartDTO>();
		int total =partsList.size();
		for(int i=index*size;i<((index+1)*size<total?(index+1)*size:total);i++){
			subPartsList.add(partsList.get(i));
		}
		PageDTO pd =new PageDTO();
		pd.setPagesize(size);
		pd.setData(subPartsList);
		pd.setTotal(total);
		int totalPage =0;
		if(size!=0){
			if(total%size==0){
				totalPage = total/size;
			}else{
				totalPage = total/size+1;
			}
		}
		pd.setTotalPage(totalPage);
		return pd;
	}
	
	/**
	 * 获得未分类的零部件（分页显示）
	 * @param index 分页索引
	 * @param size 每页条数
	 * @return 返回页类信息（表格数据、总数据条数、总页数）
	 */
	public PageDTO getUnArrangePart(int index, int size) {

		List<Part> parts =partDao.find("from Part p where p.isarranged=0");
		List<PartDTO> partsList = new ArrayList<PartDTO>();
		for(Part p:parts){
			//partDao.getSession().evict(p);
			//System.out.println("将时间转化为"+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(p.getCreateTime()));
			//p.setClassificationTree(null);
			PartDTO partDTO =new PartDTO();
			partDTO.setId(p.getId());
			if(p.getCreateTime()!=null){	
				partDTO.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(p.getCreateTime()));
			}else{
				partDTO.setCreateTime("");
			}

			partDTO.setDescription(p.getDescription());
			partDTO.setIsarranged(p.getIsarranged());
			partDTO.setPartBigVersion(p.getPartBigVersion());
			partDTO.setPartName(p.getPartName());
			partDTO.setPartNumber(p.getPartNumber());
			partDTO.setPartSmallVersion(p.getPartSmallVersion());
			partDTO.setState(p.getState());
			partsList.add(partDTO);
		}
		List<PartDTO> subPartsList = new ArrayList<PartDTO>();
		int total =partsList.size();
		for(int i=index*size;i<((index+1)*size<total?(index+1)*size:total);i++){
			subPartsList.add(partsList.get(i));
		}
		PageDTO pd =new PageDTO();
		pd.setPagesize(size);
		pd.setData(subPartsList);
		pd.setTotal(total);
		int totalPage =0;
		if(size!=0){
			if(total%size==0){
				totalPage = total/size;
			}else{
				totalPage = total/size+1;
			}
		}
		pd.setTotalPage(totalPage);
		return pd;
	
	}
	/**
	 * 将零部件分类
	 * @param partIds 所有需要分类的零部件id所组成的数组
	 * @param treeId 零部件所需要分类到的模块树节点 
	 * @throws SQLException 
	 * @throws HibernateException 
	 */
	public String arrangingPart(long[] partIds,long treeId) throws HibernateException, SQLException{
		//是否存在表的标志，默认不存在
		boolean flag =false;
		//查询是否存在该模块事物特性表
		ClassificationTree cTree=classificationTreeDao.get(treeId);
		String tableName =cTree.getCode();
		tableName = tableName.replaceAll("-", "_");
		@SuppressWarnings("deprecation")
		ResultSet rs=classificationTreeDao.getSession().connection().getMetaData().getTables(null,null,tableName,new String[]{"TABLE"});
		//如果有
		if(rs.next()){
			flag = true;
		}
		
		int length = partIds.length;
		
		for(int i=0;i<length;i++){
			Part part=partDao.get(partIds[i]);
			if(flag){
				long part_id =part.getId();
				String part_number =part.getPartNumber();
				String part_name =part.getPartName();
				//向相应事物特性表中插入零件数据
				String insertSql ="insert into "+tableName+"(part_id,part_number,part_name) values ("+part_id+",'"+part_number+"','"+part_name+"')";
				classificationTreeDao.getSession().createSQLQuery(insertSql).executeUpdate();
			}
			part.setClassificationTree(cTree);
			//表示需要同步
			part.setBasicFlag(1);
			//用于同步时识别零件所属模块
			part.setModuleUuid(cTree.getUuid());
			System.out.println(cTree.getUuid()+"***************************************");
			part.setIsarranged(1);
			partDao.save(part);
		}
		return "添加分类成功！";
	}
	
	/**
	 * 将零部件取消分类，取消分类的时候，要删除事物特性表中的数据
	 * @param partIds 所有需要取消分类的零部件id所组成的数组
	 * @param treeId 零部件取消分类的模块树节点
	 * @throws HibernateException
	 * @throws SQLException 
	 * 
	 */
	
	public String deArrangingPart(long[] partIds,long treeId) throws HibernateException, SQLException{
		//是否存在模块事物特性表的标志，默认没有
		boolean flag = false;
		ClassificationTree cTree = classificationTreeDao.get(treeId);
		String tableName =cTree.getCode();
		tableName=tableName.replaceAll("-", "_");
		@SuppressWarnings("deprecation")
		ResultSet rs = classificationTreeDao.getSession().connection().getMetaData().getTables(null, null, tableName, new String[]{"TABLE"});
		if(rs.next()){
			flag = true;
		}
		int length = partIds.length;
		for(int i = 0;i<length;i++){
			if(flag){
				//删除事物特性表中的数据
				String deleteSql = "delete from "+tableName+" where part_id ="+partIds[i];
				classificationTreeDao.getSession().createSQLQuery(deleteSql).executeUpdate();
			}
			Part part = partDao.get(partIds[i]);
			part.setIsarranged(0);
			part.setClassificationTree(null);
			//表示需要同步
			part.setBasicFlag(1);
			//用于同步时识别零件所属模块
			part.setModuleUuid(null);
			
			partDao.save(part);
			//partDao.getSession().flush();
		}

		return "取消分类成功！";
	}
	
	
	/**
	 * 编辑已分类零部件的描述信息
	 * @param treeId 零部件所属分类树节点
	 * @param description 零部件描述信息
	 */
	public String edtiPartDes(long treeId,String description){
		Part part=partDao.findUniqueBy("id", treeId);
		part.setDescription(description);
		//表示需要同步
		part.setBasicFlag(1);
		partDao.save(part);
		return "修改成功！";
	}
	/**
	 * 创建新的零件（指定模块）的时候，判断有无事物特性表，无则不执行事物特性表相关操作，有则执行相关操作。
	 */
	public String createNewPart(long treeId, String partNumber,String partName, String description) throws HibernateException, SQLException{
		Part partExist=partDao.findUniqueBy("partNumber", partNumber);
		if(partExist != null){
			return "零件号已存在，请重新添加！";
		}

		ClassificationTree cTree=classificationTreeDao.get(treeId);
		Part part= new Part();
		part.setClassificationTree(cTree);
		part.setModuleUuid(cTree.getUuid());
		part.setCreateTime(new Date());
		part.setDescription(description);
		part.setIsarranged(1);
		part.setPartName(partName);
		part.setPartNumber(partNumber);
		part.setUuid(UUID.randomUUID().toString());
		//同步标识
		part.setBasicFlag(1);
		part.setImgFlag(0);
		part.setModelFlag(0);
		part.setSelfFlag(0);
		partDao.save(part);
		long partId=part.getId();

		
		String tableName =cTree.getCode();
		tableName=tableName.replaceAll("-", "_");
		
		@SuppressWarnings("deprecation")
		ResultSet rs=classificationTreeDao.getSession().connection().getMetaData().getTables(null,null,tableName,new String[]{"TABLE"});
		//存在事物特性表，执行相关操作。
		if(rs.next()){ 
			String insertSql = "insert into "+tableName+"(part_id,part_number,part_name) values ("+partId+",'"+partNumber+"','"+partName+"')";
			classificationTreeDao.getSession().createSQLQuery(insertSql).executeUpdate();
		}
		
		return "零件添加成功";
	}
	
	/**
	 * 删除零件表的信息，判断有无事物特性表，有则同时删除事物特表中零件的信息，无则不执行操作
	 * @throws SQLException 
	 * @throws HibernateException 
	 */
	public String deletePart(long id,long treeId) throws HibernateException, SQLException{

		ClassificationTree cTree=classificationTreeDao.get(treeId);
		String tableName =cTree.getCode();
		tableName=tableName.replaceAll("-", "_");
		
		@SuppressWarnings("deprecation")
		ResultSet rs = classificationTreeDao.getSession().connection().getMetaData().getTables(null, null, tableName, new String[]{"TABLE"});
		if(rs.next()){
			//删除事物特性表中的零件信息
			String deleteSql = "delete from "+tableName+" where part_id ="+id;
			classificationTreeDao.getSession().createSQLQuery(deleteSql).executeUpdate();
		}
		
		partDao.delete(id);
		return "删除成功！";
	}
	
	/**
	 * 用于服务，获得需要同步基本信息的零件
	 * @return
	 */
	public List<Part> getParts2UpdateBasic(){
		//找到需要更新基本信息的零件
		List<Part> parts = partDao.findBy("basicFlag", 1);
		List<Part> partsRe = new ArrayList<Part>();
		for(Part part : parts){
			partDao.getSession().evict(part);
			ClassificationTree ct = part.getClassificationTree();
			if(ct != null){
				classificationTreeDao.getSession().evict(ct);
				ct.setChildren(null);
				ct.setParent(null);
				ct.setCodeClass(null);
			}
			part.setClassificationTree(ct);
			partsRe.add(part);
		}
		return partsRe;
	}
	
	/**
	 * 用于服务，同步零件基本信息完毕之后，修改basicFlag信息
	 * @param parts
	 */
	public void UpdateBasicFinish(List<Part> parts){
		for(Part part : parts){
			//表示已经更新完毕
			part.setBasicFlag(0);
			partDao.save(part);
		}
	}
	/**
	 * 用于服务，同步零件零件的示意图信息,获取需要同步的零件
	 */
	public List<Part> getParts2UpdateImg(){
		List<Part> parts = partDao.find("from Part p where p.imgFlag = 1 and p.imgUrl is not null");
		int length = parts.size();
		for(int i=0;i<length;i++){
			Part p = parts.get(i);
			partDao.getSession().evict(p);
			p.setClassificationTree(null);
			p.setCreateTime(null);
		}
		return parts;
	}
	/**
	 * 用于服务,同步零件的示意图信息,获取需要同步的所有零件示意图压缩文件(创建的临时文件要在同步完成后删除)
	 * 
	 */
	public DataHandler getFileZip(List<Part> parts){
		List<File> fileList = new ArrayList<File>();
		File fileDirectory = new File(Constants.UPLOADPARTIMG_PATH);
		if(!fileDirectory.exists()){
			fileDirectory.mkdirs();
		}
		File file=null;
		String filePath ="";
		File tempZipFile = new File(Constants.UPLOADPARTIMG_PATH+"temp.zip");
		//pack the file to zip then put it into the DataHandler
		for(Part p : parts){
			filePath = Constants.FILEROOT+p.getImgUrl();
			file = new File(filePath);
			fileList.add(file);
		}

		try {
			FileOutputStream fos = new FileOutputStream(tempZipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);
			BufferedInputStream bis = null;
			FileInputStream fis = null;
			for(File f :fileList){
				if(f.isFile()){
					ZipEntry ze = new ZipEntry(f.getName());
					zos.putNextEntry(ze);
					fis = new FileInputStream(f);
					bis = new BufferedInputStream(fis,1024);
					byte[] buffer = new byte[1024];
					int count;
					while((count=bis.read(buffer)) != -1){
						zos.write(buffer, 0, count);
					}
					
				}
			}
			if(bis !=null){
				bis.close();
			}
			if(fis !=null){
				fis.close();
			}
			if(zos !=null){
				zos.close();
			}
			if(fos !=null){
				fos.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileDataSource dataSource = new FileDataSource(tempZipFile);
		DataHandler fileZip = new DataHandler(dataSource);
		
		return fileZip;
	}
	
	public void UpdateImgFinish(List<Part> parts){
		for(Part part :parts){
			Part p = partDao.get(part.getId());
			p.setImgFlag(0);
		}
		
		File tempZip = new File(Constants.UPLOADPARTIMG_PATH+"temp.zip");
		if(tempZip.exists()){
			tempZip.delete();
		}
	}
	
	/**
	 * 用户服务，同步零件的模型信息，获取需要同步的零件
	 * @return
	 */
	public List<Part> getParts2UpdateModel() {
		List<Part> parts = partDao.find("from Part p where p.modelFlag = 1 ");
		int count = parts.size();
		for(int i =0 ;i<count;i++) {
			Part part = parts.get(i);
			partDao.getSession().evict(part);
			part.setClassificationTree(null);
			part.setCreateTime(null);
		}
		return parts;
	}

	/**
	 * 用于服务，根据零件选出其模型信息
	 * @param parts
	 * @return
	 */
	public List<PartDraft> getPartDraftsbyParts(List<Part> parts,boolean isModel){
		String sql =null;
		if(isModel){
			sql = "from PartDraft pDraft where pDraft.ismaster = 1 and pDraft.part.id = ?";
		}else{
			sql = "from PartDraft pDraft where pDraft.ismaster = 0 and pDraft.part.id = ?";
		}
		List<PartDraft> partDraftList = new ArrayList<PartDraft>();
		for(Part part : parts){	
			List<PartDraft> list = partDraftDao.find(sql,part.getId());
			if(list.size() != 0){
				partDraftList.addAll(list);
			}
		}
		int count = partDraftList.size();
		for(int i=0;i<count;i++){
			PartDraft draft = partDraftList.get(i);
			partDraftDao.getSession().evict(draft);
			draft.setPart(null);
			draft.setDraftType(null);
		}
		System.out.println(partDraftList.size());
		return partDraftList;
	}
	
	/**
	 * 用于服务，根据零件模型信息获得零件模型压缩文件,或者根据零件文档信息获得零件文档压缩文件
	 * @param partDrafts
	 * @param isModel true表示零件模型，false表示零件文档
	 * @return
	 */
	public DataHandler getFileZip(List<PartDraft> partDrafts,boolean isModel){
		String path =Constants.UPLOADPARTMODEL_PATH;
		if(!isModel){
			path = Constants.UPLOADPARTSELF_PATH;
		}
		List<File> drafts = new ArrayList<File>();
		File fileDirectory = new File(path);
		if(!fileDirectory.exists()){
			fileDirectory.mkdirs();
		}
		
		File tempZipFile = new File(path+"temp.zip");
		
		for(PartDraft draft :partDrafts) {
			
			File draftFile = new File(path+draft.getFileName());
			if(draftFile.exists()){
				drafts.add(draftFile);
			}
		}
		
		try {
			FileOutputStream fos = new FileOutputStream(tempZipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);
			FileInputStream  fis =null;
			BufferedInputStream bis =null;
			
			for(File f :drafts){
				ZipEntry ze = new ZipEntry(f.getName());
				zos.putNextEntry(ze);
				fis = new FileInputStream(f);
				bis = new BufferedInputStream(fis,1024);
				byte[] buffer = new byte[1024];
				int count=-1;
				while((count = bis.read(buffer))!=-1){
					zos.write(buffer, 0, count);
				}
			}
			if(bis !=null){
				bis.close();
			}
			if(fis !=null){
				fis.close();
			}
			if(zos !=null){
				zos.close();
			}
			if(fos !=null){
				fos.close();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		FileDataSource dataSource = new FileDataSource(tempZipFile);
		DataHandler zipFile = new DataHandler(dataSource);
		return zipFile;
	}
	/**
	 * 用于服务，零件模型同步完成后，更新模块化端的 modelFlag,并且删除临时压缩文件
	 * @param parts
	 */
	public void UpdateModelFinish(List<Part> parts){
		for(Part part : parts){
			Part p= partDao.get(part.getId());
			p.setModelFlag(0);
		}
		File f = new File(Constants.UPLOADPARTMODEL_PATH+"temp.zip");
		if(f.exists()){
			f.delete();
		}
	}
	
	/**
	 * 用户服务，同步零件的自定义文档信息，获取需要同步的零件
	 * @return
	 */
	public List<Part> getParts2UpdateSelf(){
		List<Part> parts = partDao.find("from Part p where p.selfFlag = 1 ");
		int count = parts.size();
		for(int i =0 ;i<count;i++) {
			Part part = parts.get(i);
			partDao.getSession().evict(part);
			part.setClassificationTree(null);
			part.setCreateTime(null);
		}
		return parts;
	}
	
	/**
	 * 用于服务，零件自定义文档同步完成后，更新模块化端的 selfFlag,并且删除临时压缩文件
	 * @param parts
	 */
	public void UpdateSelfFinish(List<Part> parts){
		for(Part part : parts){
			Part p= partDao.get(part.getId());
			p.setSelfFlag(0);
		}
		File f = new File(Constants.UPLOADPARTSELF_PATH+"temp.zip");
		if(f.exists()){
			f.delete();
		}
	}
	
	public PartDraftDao getPartDraftDao() {
		return partDraftDao;
	}
	@Autowired
	public void setPartDraftDao(PartDraftDao partDraftDao) {
		this.partDraftDao = partDraftDao;
	}
	public ClassificationTreeDao getClassificationTreeDao() {
		return classificationTreeDao;
	}
	@Autowired
	public void setClassificationTreeDao(ClassificationTreeDao classificationTreeDao) {
		this.classificationTreeDao = classificationTreeDao;
	}

}
