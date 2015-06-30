package edu.zju.cims201.GOF.util.asm.render;

import edu.zju.cims201.GOF.hibernate.pojo.Ktype;

public interface Render {
	
	void render(Ktype target,String template,String outpath);
}
