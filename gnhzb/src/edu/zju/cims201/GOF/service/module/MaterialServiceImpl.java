package edu.zju.cims201.GOF.service.module;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import  edu.zju.cims201.GOF.hibernate.pojo.Material;
import  edu.zju.cims201.GOF.hibernate.pojo.MaterialEmission;


@Service
@Transactional
public class MaterialServiceImpl implements MaterialService {
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	public Material addMaterial(Material m) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(m);
		return m;
	}

	public void deleteMaterial(int id) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().get(Material.class, id));
	}

	public List<Material> getMaterialByStage(int stage, int cId, int userId) {
		// TODO Auto-generated method stub
		List<Material> mList = sessionFactory.getCurrentSession().createQuery("from Material m where m.stage = ?").setParameter(0, stage).list();
		for(Material m:mList){
			List<MaterialEmission> meList = sessionFactory.getCurrentSession().createQuery("from MaterialEmission me where me.materialId = ? and me.componentId = ? and me.userId = ?")
			.setParameter(0, m.getId())
			.setParameter(1, cId)
			.setParameter(2, userId)
			.list();
			if(meList.size() > 0){
				m.setEmission(meList.get(0).getEmission());
				m.setSum(meList.get(0).getSum());			
			}else{
				m.setEmission(0.0);
				m.setSum(0.0);
			}
		}
		return sessionFactory.getCurrentSession().createQuery("from Material m where m.stage = ?").setParameter(0, stage).list();
	}

	public Material updateMaterial(Material m) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(m);
		return m;
	}

	public Material getMaterial(int id) {
		// TODO Auto-generated method stub
		return (Material)sessionFactory.getCurrentSession().get(Material.class, id);
	}

	public void saveCarbonEmission(List<MaterialEmission> meList) {
		// TODO Auto-generated method stub
		for(MaterialEmission me:meList){
			if(me.getEmission()==0 && me.getSum() == 0){
				
			}else{
				List<MaterialEmission> tmeList = sessionFactory.getCurrentSession().createQuery("from MaterialEmission me where me.materialId = ? and me.componentId = ? and me.userId = ?")
				.setParameter(0, me.getMaterialId())
				.setParameter(1, me.getComponentId())
				.setParameter(2, me.getUserId())
				.list();
				if(tmeList.size()>0){
					tmeList.get(0).setEmission(me.getEmission());
					tmeList.get(0).setSum(me.getSum());
					sessionFactory.getCurrentSession().update(tmeList.get(0));
				}else{
					sessionFactory.getCurrentSession().save(me);
				}
			}
		}
	}

	public List carbonEmissionStatistic(int componentId, int materialId, int userId) {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("select me.emission, count(me.id) from MaterialEmission me where me.componentId = ? and me.materialId = ?  group by me.emission order by count(me.id)")
		.setParameter(0, componentId)
		.setParameter(1, materialId)
		.list();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
}
