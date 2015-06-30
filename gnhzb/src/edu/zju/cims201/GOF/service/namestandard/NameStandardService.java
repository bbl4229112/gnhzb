package edu.zju.cims201.GOF.service.namestandard;

import java.util.List;

import edu.zju.cims201.GOF.hibernate.pojoA.NameStandard;

public interface NameStandardService {
	public void save(NameStandard ns);
	public List<NameStandard> getAll();
}
