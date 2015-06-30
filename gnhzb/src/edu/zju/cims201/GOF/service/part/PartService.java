package edu.zju.cims201.GOF.service.part;

import java.sql.SQLException;
import java.util.List;

import javax.activation.DataHandler;

import org.hibernate.HibernateException;

import edu.zju.cims201.GOF.hibernate.pojoA.Part;
import edu.zju.cims201.GOF.hibernate.pojoA.PartDraft;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.rs.dto.PartDTO;

public interface PartService {
	public PageDTO getUnArrangePart(int index,int size);
	public PageDTO getArrangedPart(int index, int size,long treeId);
	public String arrangingPart(long[] partIds,long treeId) throws HibernateException, SQLException;
	public String deArrangingPart(long[] partIds,long treeId) throws HibernateException, SQLException;
	public String edtiPartDes(long treeId,String description);
	public String createNewPart(long treeId, String partNumber,String partName, String description)throws HibernateException, SQLException;
	public String deletePart(long id,long treeId) throws HibernateException, SQLException;
	public List<PartDTO> getArrangePart(long treeId);
	/**
	 * 用于服务，获得需要同步基本信息的零件
	 * @return
	 */
	public List<Part> getParts2UpdateBasic();
	/**
	 * 用于服务，同步零件基本信息完毕之后，修改basicFlag信息
	 * @param parts
	 */
	public void UpdateBasicFinish(List<Part> parts);
	/**
	 * 用于服务，同步零件的示意图信息,获取需要同步的零件
	 */
	public List<Part> getParts2UpdateImg();
	/**
	 * 用于服务,同步零件的示意图信息,获取需要同步的所有零件示意图压缩文件(创建的临时文件要在同步完成后删除)
	 * 
	 */
	public DataHandler getFileZip(List<Part> parts);
	/**
	 *	用于服务,零件示意图同步完成后,更新模块化端的imgFlg，并且删除临时压缩文件
	 * @param parts
	 */
	public void UpdateImgFinish(List<Part> parts);
	/**
	 * 用户服务，同步零件的模型信息，获取需要同步的零件
	 * @return
	 */
	public List<Part> getParts2UpdateModel();
	/**
	 * 用于服务，根据零件选出其模型信息或者文档信息
	 * @param parts
	 * @param isModel true表示零件模型，false表示零件文档
	 * @return
	 */
	public List<PartDraft> getPartDraftsbyParts(List<Part> parts,boolean isModel);
	/**
	 * 用于服务，根据零件模型信息获得零件模型压缩文件,或者根据零件文档信息获得零件文档压缩文件
	 * @param partDrafts
	 * @param isModel true表示零件模型，false表示零件文档
	 * @return
	 */
	public DataHandler getFileZip(List<PartDraft> partDrafts,boolean isModel);
	/**
	 * 用于服务，零件模型同步完成后，更新模块化端的 modelFlag,并且删除临时压缩文件
	 * @param parts
	 */
	public void UpdateModelFinish(List<Part> parts);
	/**
	 * 用户服务，同步零件的自定义文档信息，获取需要同步的零件
	 * @return
	 */
	public List<Part> getParts2UpdateSelf();
	/**
	 * 用于服务，零件自定义文档同步完成后，更新模块化端的 selfFlag,并且删除临时压缩文件
	 * @param parts
	 */
	public void UpdateSelfFinish(List<Part> parts);
}
