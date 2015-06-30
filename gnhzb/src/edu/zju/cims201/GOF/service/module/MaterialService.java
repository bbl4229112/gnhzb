package edu.zju.cims201.GOF.service.module;

import java.util.List;

import  edu.zju.cims201.GOF.hibernate.pojo.Material;
import  edu.zju.cims201.GOF.hibernate.pojo.MaterialEmission;



public interface MaterialService {
	//获取某一个阶段的�?��的材�?
	public List<Material> getMaterialByStage(int stage, int cId, int userId);
	//新增材料
	public Material addMaterial(Material m);
	//更新材料
	public Material updateMaterial(Material m);
	//删除材料
	public void deleteMaterial(int id);
	//根据id获取材料
	public Material getMaterial(int id);
	//保存碳排放量
	public void saveCarbonEmission(List<MaterialEmission> meList);
	//返回碳排放量的统计�?
	public List carbonEmissionStatistic(int componentId, int materialId, int userId);
}
