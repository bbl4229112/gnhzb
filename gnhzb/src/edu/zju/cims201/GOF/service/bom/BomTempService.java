package edu.zju.cims201.GOF.service.bom;

import java.util.List;

import edu.zju.cims201.GOF.hibernate.pojoA.BomTemp;
import edu.zju.cims201.GOF.rs.dto.PartDTO;

public interface BomTempService {

	public String addAll2BomTemp(long platId, long orderId);

	public void deleteBomTempByPlatIdAndOrderId(long platId, long orderId);

	public String getBomTempByPlatIdAndOrderId(long platId, long orderId);

	public List<PartDTO> getInstance2ChooseByPlatStructId(long platStructId, long orderId);

	public String configOrderByPartIds(long platStructId, long orderId,
			long[] partIdsArr);

	public void configPartCount(long platId, long orderId,long partId, int partCount);

}
