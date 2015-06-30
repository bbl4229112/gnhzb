package edu.zju.cims201.GOF.service.bom;

public interface ModuleConfigStatusService {

	public String initConfigStatus(long platId, long orderId);

	public String moduleConfiged(long platStructId, long orderId);

}
