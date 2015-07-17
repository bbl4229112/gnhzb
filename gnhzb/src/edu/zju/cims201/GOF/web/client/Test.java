package edu.zju.cims201.GOF.web.client;



import edu.zju.cims201.GOF.web.server.ModuleService;

public class Test {
	public static void main(String[] args) {
		
		ModuleService ms = new ModuleService();
		//ms.sendBom(); 				//同步主BOM,成功
		//ms.sendParts2SynBasic();      //同步零件基本信息，成功
		//ms.sendPart2SynImg();			//
		//ms.sendPart2SynModel();
		//ms.sendPart2SynSelf();
		//ms.sendInstanceBoms2Syn();	//同步实例BOM，成功
		//ms.sendTreeNodes2SynModel();    //同步分类树主模型，成功
		//ms.sendTreeNodes2SynSelf();       //同步分类树自定义文档，成功
		
		//ms.sendTreeNodes2SynImg();
	}
}

