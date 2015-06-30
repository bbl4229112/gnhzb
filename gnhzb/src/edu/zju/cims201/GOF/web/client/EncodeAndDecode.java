package edu.zju.cims201.GOF.web.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;  
import net.sf.json.JsonConfig;
import edu.zju.cims201.GOF.hibernate.pojoA.ClassificationTree;
import edu.zju.cims201.GOF.hibernate.pojoA.CodeClass;

public class EncodeAndDecode {
	public static void main(String[] args) {
		CodeClass cc = new CodeClass();
		cc.setClassname("产品");
		ClassificationTree ct = new ClassificationTree();
		ct.setText("root1");
		ct.setCodeClass(cc);
		List<ClassificationTree> list = new ArrayList<ClassificationTree>();
		for(int i=0;i<3;i++){
			ClassificationTree child = new ClassificationTree();
			child.setText("child"+i);
			List<ClassificationTree> list1 = new ArrayList<ClassificationTree>();
			for(int j=0;j<3;j++){
				ClassificationTree child1 =new ClassificationTree();
				child1.setText("child"+i+j);
				list1.add(child1);
			}
			child.setChildren(list1);
			ct.setCodeClass(cc);
			list.add(child);
			
		}
		ct.setChildren(list);
		//String strObjects = JSONUtil.write(ct);
		JSONObject jsO1 =JSONObject.fromObject(ct);
		System.out.println("对象变为字符串：");
		//System.out.println(strObjects);
		System.out.println(jsO1.toString());
	
		//Object o= JSONUtil.read(strObjects);
		
		System.out.println("字符串变对象：");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(ClassificationTree.class);
		Map<String,Class<ClassificationTree>> classMap = new HashMap<String,Class<ClassificationTree>>();
		classMap.put("children", ClassificationTree.class);
		jsonConfig.setClassMap(classMap);
		

		//ClassificationTree root2 =(ClassificationTree)JSONObject.toBean(JSONObject.fromObject(jsO1.toString()), ClassificationTree.class);
		ClassificationTree root2 =(ClassificationTree)JSONObject.toBean(JSONObject.fromObject(jsO1.toString()), jsonConfig);
		System.out.println("父名字："+root2.getText());
		System.out.println("所属产品："+root2.getCodeClass().getClassname());
		List<ClassificationTree> root2Children = root2.getChildren();
		//没有指定children属性的类型，无法转换，用JsonConfig
		//System.out.println(root2Children.get(0).getText());
		/*for(ClassificationTree ctTemp: root2Children){
			System.out.println(ctTemp.getText());
		}*/
		printChildrenName(root2);
	}
	
	public static void printChildrenName(ClassificationTree ct){
		if(ct!=null){
			System.out.println(ct.getText());
			int n = ct.getChildren().size();
			for(int i =0;i<n;i++){
				printChildrenName(ct.getChildren().get(i));
			}
		}
	}
}
