package edu.zju.cims201.GOF.service.kmap;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import prefuse.owl2prefuse.ModelFlyWeightFactory;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import edu.zju.cims201.GOF.hibernate.pojo.Tag;
import edu.zju.cims201.GOF.rs.dto.TagDTO;
import edu.zju.cims201.GOF.service.onto.OntologyService;
import edu.zju.cims201.GOF.util.Constants;

@Service
@Transactional
public class KmapServiceImpl implements KmapService {
    


	private OntologyService ontologyService;
	public OntologyService getOntologyService() {
		return ontologyService;
	}
     @Autowired
	public void setOntologyService(OntologyService ontologyService) {
		this.ontologyService = ontologyService;
	}
	protected OntModel m_model;
	// 用户记录已经遍历过的术语
	private String firstsearchname = "";
	// 用户记录已经遍历过的术语URI
	private String firstsearchURI = "";
	// 用于记录屏蔽掉的术语URI
	private ArrayList<String> m_uselessType;

	// private Hashtable<String, Node> m_nodes;
	// hash表结构记录已经遍历过的术语
	private Hashtable<String, String> m_nodesadded;
	private Hashtable<String, String> rem_nodesadded;
	// hash表用于记录正向的术语关系
	private Hashtable<String, String> noderelation;
	// hash表用于记录反向的术语关系
	private Hashtable<String, String> nodererelation;
	// hash表用于记录总关系之和
	private Hashtable<String, String> allnoderelation;
	// hash表用于记录相关度顺序
	private Hashtable<String, String> orderrelation;
	// 用于记录关注的属于
	private String foucsname = "";
	// 用于记录正向的树结点list
	private List sourcelist = new ArrayList();
	// 用于记录反向的树节点
	private List resourcelist = new ArrayList();

	//private String appUrl = null;

	// 用于记录所有树节点
	// private List allsourcelist= new ArrayList();

	public List<TagDTO> getTagsuggest(String tagname,String owlFileName) {
		List<TagDTO> suggestlist = new ArrayList<TagDTO>();
	
		//this.appUrl = "";
		set_Ontomodel(owlFileName);
//
//		OntClass rootClass = m_model
//				.getOntClass("http://www.w3.org/2002/07/owl#Thing");
		
//		String strQuery="PREFIX unnamed:<http://www.owl-ontologies.com/unnamed.owl#> " +
//			"PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
//			"SELECT ?term " +
//			"WHERE {" +
//					"{?term  rdf:type unnamed:术语  } UNION " +
//					"{?term  rdf:type unnamed:类别  }"+
//					"}";
		String strQuery="PREFIX unnamed:<http://www.owl-ontologies.com/unnamed.owl#> " +
				"PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				 "PREFIX owl: <http://www.w3.org/2002/07/owl#>"+ //自己加的
				"SELECT ?term " +
				"WHERE {" +
					 "?term  rdf:type ?x  ." +
				//	 "?term rdf:type owl:Class ."+
						  "FILTER (!isBlank(?x))"+
						  "FILTER (!isBlank(?term))"+
						"}";
  //  System.out.println(strQuery);
	Query query = QueryFactory.create(strQuery);
	QueryExecution qexec = QueryExecutionFactory.create(query, m_model);
	ResultSet rs = qexec.execSelect();
//	int t=0;
	while (rs.hasNext()) {
//		t++;
Resource resource = rs.nextSolution().getResource("term");
if(!resource.canAs(OntClass.class)){
String tag=resource.getLocalName();
//System.out.println("tag="+tag);
if (tagname.trim().equals("")) {
//	TagDTO ontotag = new TagDTO();
//	ontotag.setTagType("知识术语");
////	String tag = foundIndividual.getLocalName().replaceAll(
////			"http://www.owl-ontologies.com/unnamed.owl#", "")
////			.trim();
//	ontotag.setTagName(tag);
//
//	if (!suggestlist.contains(ontotag)) {
//		ontotag.setTagType(tag+"              知识术语");
//		suggestlist.add(ontotag);
//	}
}
// 根据检索的术语名词列出术语
else {
	TagDTO ontotag = new TagDTO();
//
//	String tag = foundIndividual
//			.getLocalName()
//			.toString()
//			.replaceAll(
//					"http://www.owl-ontologies.com/unnamed.owl#",
//					"").trim();
	if (tag.indexOf(tagname) != -1) {
		ontotag.setTagType(tag+"              知识术语");
		ontotag.setTagName(tag);

		if (!suggestlist.contains(ontotag)) {
			suggestlist.add(ontotag);
		}

	}
}
	}
	}
//	System.out.println("t="+t);	
//		ExtendedIterator itClasses = rootClass.listSubClasses(true);
//		while (itClasses.hasNext()) {
//			rootClass = (OntClass) itClasses.next();
//		System.out.println("遍历所有的类"+rootClass.getLocalName());
//			ExtendedIterator itIndividuals = rootClass.listInstances();
//			while (itIndividuals.hasNext()) {
//				Individual foundIndividual = (Individual) itIndividuals.next();
//				// 列出所有的术语
//				if (tagname.equals("")) {
//					TagDTO ontotag = new TagDTO();
//					ontotag.setTagType("知识术语");
//					String tag = foundIndividual.getLocalName().replaceAll(
//							"http://www.owl-ontologies.com/unnamed.owl#", "")
//							.trim();
//					ontotag.setTagName(tag);
//
//					if (!suggestlist.contains(ontotag)) {
//						ontotag.setTagType(tag+"              知识术语");
//						suggestlist.add(ontotag);
//					}
//				}
//				// 根据检索的术语名词列出术语
//				else {
//					TagDTO ontotag = new TagDTO();
//
//					String tag = foundIndividual
//							.getLocalName()
//							.toString()
//							.replaceAll(
//									"http://www.owl-ontologies.com/unnamed.owl#",
//									"").trim();
//					if (tag.indexOf(tagname) != -1) {
//						ontotag.setTagType(tag+"              知识术语");
//						ontotag.setTagName(tag);
//
//						if (!suggestlist.contains(ontotag)) {
//							suggestlist.add(ontotag);
//						}
//
//					}
//				}
//			}
//		}
		System.out.println("共推荐术语："+suggestlist.size()+"条");
		return suggestlist;
	}

	public Hashtable<String, String> getontorelation(String tag, String appUrl) {
	//	this.appUrl = appUrl;
		// 初始化hash表结构的对象
		m_nodesadded = new Hashtable<String, String>();
		rem_nodesadded = new Hashtable<String, String>();
		noderelation = new Hashtable<String, String>();
		nodererelation = new Hashtable<String, String>();
		allnoderelation = new Hashtable<String, String>();
		orderrelation = new Hashtable<String, String>();
		// 读取本体文件所在路径
		String p_OWLFile = Constants.LOCAL_ONTO_FILE_PATH;
		// 创建本体模型
		m_model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		// 读取问津
		InputStream in = FileManager.get().open(p_OWLFile);
		// 如果in为null 则抛出异常
		if (in == null) {
			throw new IllegalArgumentException("File: " + p_OWLFile
					+ " not found");
		}

		m_model.read(in, "");
		// tag = "液体火箭发动机";
		foucsname = tag;
		// 读取查询本体库中所有类和实例
		String strQuery = "";

		strQuery = "PREFIX rdf:"
				+ " <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
				+ "PREFIX base:<http://www.owl-ontologies.com/unnamed.owl#> "
				+ "SELECT ?x"
				+ " WHERE { "
				// + "OPTIONAL { ?x rdf:type base:通用术语.}"
				// + "?x rdf:type base:?y "
				// + "?x rdf:type owl:Class ."
				// + "OPTIONAL { ?x rdf:type ?y } "
				// +" FILTER (REGEX(?y '通用术语' 'i')) "

				+ "?x rdf:type owl:Class ."
				+ "OPTIONAL { ?x rdfs:subClassOf?y }" + "FILTER (!bound(?y))"
				+ "FILTER (!isBlank(?x))" + "}";
		// System.out.println("strQuery=" + strQuery);
		Query query = QueryFactory.create(strQuery);
		QueryExecution qexec = QueryExecutionFactory.create(query, m_model);
		ResultSet rs = qexec.execSelect();
		// 定义本体类的根
		OntClass rootClass = m_model
				.getOntClass("http://www.w3.org/2002/07/owl#Thing");
		// 定义本体子类的属性
		Property subClassProp = m_model
				.getProperty("http://www.w3.org/2000/01/rdf-schema#subClassOf");

		int i = 0;
		// 遍历将所有资源购入resource类型中
		while (rs.hasNext()) {
			Resource resource = rs.nextSolution().getResource("x");
			resource.addProperty(subClassProp, rootClass);
		}

		// 开始正向遍历树的各个关系节点
		// 添加需要屏蔽的术语的URI
		m_uselessType = new ArrayList<String>();
		m_uselessType.add("http://www.w3.org/2002/07/owl#Class");
		m_uselessType.add("http://www.w3.org/2000/01/rdf-schema#Class");
		// Build the entire tree.
		firstsearchname = tag;
		// 定义被查询的术语与自己的关系为1.0 起到初始化的作用，
		noderelation.put(tag, "1.0");
		nodererelation.put(tag, "1.0");
		// 添加节点和边
		// 从头遍历树
		buildGraph(rootClass, tag);
		// 记录已经添的节点和url
		m_nodesadded.put(firstsearchURI, firstsearchname);
		rebuildGraph(rootClass, tag);
		rem_nodesadded.put(firstsearchURI, firstsearchname);
		listsub();
		// 求正反相关度之和
		setallrelation();
		// for(int ii=1;ii<=orderrelation.size();ii++)
		// {
		// // System.out.println(orderrelation.get(ii+""));
		// }
		//		

		return orderrelation;
	}

	public void set_Ontomodel(String owlFileName) {
        if(null==owlFileName)
        	m_model=ontologyService.getModel_2("default");
		//m_model = ModelFlyWeightFactory.getInstance().getModel(Constants.ONTO_FILE_PATH);
        else
      //  	m_model=ontologyService.getModel_1(owlFileName);
        	m_model=ontologyService.getModel_2(owlFileName);
	}

	// 正向遍历tree
	private void buildGraph(OntClass p_currentClass, String searchname) {
		// If there is no root node yet, one is created.
		// 为图形添加结点

		// 用storeEdges方法为节点添加边
		getrelation(p_currentClass, searchname);

		// 遍历当前类的子类
		ExtendedIterator itClasses = p_currentClass.listSubClasses(true);
		while (itClasses.hasNext()) {
			// 迭代调用buildGraph方法
			buildGraph((OntClass) itClasses.next(), searchname);
		}
		// 遍历类别的实例
		ExtendedIterator itIndividuals = p_currentClass.listInstances();
		while (itIndividuals.hasNext()) {
			Individual foundIndividual = (Individual) itIndividuals.next();
			// Only visualize nodes which have a (valid) URI. So no blank nodes.
			if (foundIndividual.getURI() != null) {
				// Create the node for this instance.

				// 用strore方法添加边
				getrelation(foundIndividual, searchname);

			}
		}

	}

	// 反向遍历tree
	private void rebuildGraph(OntClass p_currentClass, String searchname) {

		getRerelation(p_currentClass, searchname);

		ExtendedIterator itClasses = p_currentClass.listSubClasses(true);
		while (itClasses.hasNext()) {

			rebuildGraph((OntClass) itClasses.next(), searchname);
		}

		// Walk trough the instances of the current class.
		// 遍历类别实例
		ExtendedIterator itIndividuals = p_currentClass.listInstances();

		while (itIndividuals.hasNext()) {
			Individual foundIndividual = (Individual) itIndividuals.next();

			// Only visualize nodes which have a (valid) URI. So no blank nodes.
			if (foundIndividual.getURI() != null) {

				getRerelation(foundIndividual, searchname);
			}
		}

	}

	/**
	 * Temporarily store the edges which need to be added the graph. All the
	 * edges are stored in an ArrayList, because they can only be added if all
	 * the appropriate nodes exist. At this point this is the case, so all the
	 * nodes are created.
	 * 
	 * @param p_resource
	 *            The Jena OntResource of which the edges need to be stored.
	 */

	private void getrelation(OntResource p_resource, String searchname) {

		// System.out.println("现在查询的术语是"+searchname);
		String sourceURI = p_resource.getURI();
		String sourceName = p_resource.getLocalName();
		// System.out.println("实例名称是++++++++++++++++++++++"+sourceName);
		// 判断当前术语的url是否是被屏蔽的术语
		if (!m_uselessType.contains(sourceURI)) {
			// 如果不是将该术语的所有属性遍历出来
			StmtIterator itProperties = p_resource.listProperties();

			while (itProperties.hasNext()) {

				Statement property = itProperties.nextStatement();

				String localName = property.getPredicate().getLocalName();

				if (property.getObject().isResource()) {
					// 如果该属性是有指向性的，则列出该属性指向的术语名称
					// System.out.println("有指向属性的名称："+localName);

					String targetURI = ((Resource) property.getObject())
							.getURI();
					String targetName = ((Resource) property.getObject())
							.getLocalName();

					if (!m_uselessType.contains(targetURI)
							&& (!targetName.equals(sourceName))
							&& targetURI != null
							&& (sourceName.equals(searchname))) {
						// 如果节点目标不为自己，同时不在被屏蔽的uri中，uri不为空，且该结点术语刚好等于他自己
						// 将firstsearchURI设为其uri
						firstsearchURI = sourceURI;
						// System.out.println("targetURI====+"+targetURI);
						if (!m_nodesadded.containsKey(targetURI)) {
							// 如果该节点还没有添加到已添加的节点类表，则分别记录节点名称

							float relation = 0;
							if (localName.indexOf("--") != -1) {
								String rel = localName.substring(localName
										.indexOf("--") + 2);
								relation = new Float(rel);
								localName = localName.substring(0, localName
										.indexOf("--"));
							}
							// System.out.println("+++++++++++++++++++++++++++++++++++++");
							// System.out.println("实例："+sourceName);
							// System.out.println("关系："+localName);
							// System.out.println("关系指向："+targetName);
							// System.out.println("正向相关度是："+relation);
							float reltemp = new Float(noderelation
									.get(sourceName));

							relation = relation * reltemp;
							// System.out.println(targetName+"与"+foucsname+"之间的正向相关度是："+relation);
							noderelation.put(targetName, relation + "");

							// 将边的各种属性和值添加到边的类表中去

							// m_edges.add(edge);
							// 将targetname添加到sourcelist里
							sourcelist.add(targetName);
							// 同时对以添加节点列表添加一个节点，表示这个target已经被添加了
							m_nodesadded.put(targetURI, targetName);

						}

					}
				} else {

				}
			}
		}

	}

	private void getRerelation(OntResource p_resource, String searchname) {

		// System.out.println("现在查询的术语是"+searchname);
		String sourceURI = p_resource.getURI();
		String sourceName = p_resource.getLocalName();
		// System.out.println("实例名称是++++++++++++++++++++++"+sourceName);
		// 判断当前术语的url是否是被屏蔽的术语
		if (!m_uselessType.contains(sourceURI)) {
			// 如果不是将该术语的所有属性遍历出来
			StmtIterator itProperties = p_resource.listProperties();

			while (itProperties.hasNext()) {

				Statement property = itProperties.nextStatement();

				String localName = property.getPredicate().getLocalName();

				if (property.getObject().isResource()) {
					// 如果该属性是有指向性的，则列出该属性指向的术语名称
					// System.out.println("有指向属性的名称："+localName);

					String targetURI = ((Resource) property.getObject())
							.getURI();
					String targetName = ((Resource) property.getObject())
							.getLocalName();
					if (!m_uselessType.contains(targetURI)
							&& (!targetName.equals(sourceName))
							&& targetURI != null
							&& (targetName.equals(searchname))) {

						// 如果节点目标不为自己，同时不在被屏蔽的uri中，uri不为空，且该结点术语刚好等于他自己
						// 将firstsearchURI设为其uri
						firstsearchURI = targetURI;
						// System.out.println("targetURI====+"+targetURI);
						if (!rem_nodesadded.containsKey(sourceURI)) {
							// 如果该节点还没有添加到已添加的节点类表，则分别记录节点名称

							float relation = 0;
							if (localName.indexOf("--") != -1) {
								String rel = localName.substring(localName
										.indexOf("--") + 2);
								relation = new Float(rel);
								localName = localName.substring(0, localName
										.indexOf("--"));
							}
							// System.out.println("+++++++++++++++++++++++++++++++++++++");
							// System.out.println("实例："+sourceName);
							// System.out.println("关系："+localName);
							// System.out.println("关系指向："+targetName);
							// System.out.println("反向相关度是："+relation);
							float reltemp = new Float(nodererelation
									.get(targetName));

							relation = relation * reltemp;
							// System.out.println(targetName+"与"+foucsname+"之间的反向相关度是："+relation);
							nodererelation.put(sourceName, relation + "");
							// 将targetname添加到sourcelist里
							resourcelist.add(sourceName);
							// 同时对以添加节点列表添加一个节点，表示这个target已经被添加了
							rem_nodesadded.put(sourceURI, sourceName);

						}

					}
				} else {

				}
			}
		}

	}

	private void listsub() {

		for (int u = 0; u < sourcelist.size(); u++) {
			// 对第一添加的节点再次循环遍历其子类或者实例 从而构建树
			OntClass rootClass = m_model
					.getOntClass("http://www.w3.org/2002/07/owl#Thing");
			buildGraph(rootClass, sourcelist.get(u).toString());

		}
		for (int u = 0; u < resourcelist.size(); u++) {
			// 对第一添加的节点再次循环遍历其子类或者实例 从而构建树
			OntClass rootClass = m_model
					.getOntClass("http://www.w3.org/2002/07/owl#Thing");
			rebuildGraph(rootClass, resourcelist.get(u).toString());

		}

	}

	// 获得所有术语的相关度
	private void setallrelation() {
		// for(int i=0;i<sourcelist.size();i++)
		// {
		// System.out.println("sourcelist"+i+""+sourcelist.get(i));
		// }
		// for(int i=0;i<resourcelist.size();i++)
		// {
		// System.out.println("resourcelist"+i+""+resourcelist.get(i));
		// }
		// System.out.println("listsize============"+sourcelist.size());
		// System.out.println("relistsize============"+resourcelist.size());
		int i = 1;
		// 遍历正向结果
		for (int u = 0; u < sourcelist.size(); u++) {

			String searchname = (String) sourcelist.get(u);
			if (!allnoderelation.containsKey(searchname)) {
				float revalue = 0;
				float rerevalue = 0;
				// 得到正向的值
				if (noderelation.containsKey(searchname)) {
					revalue = new Float(noderelation.get(searchname));
				}
				// 得到反向的值
				if (nodererelation.containsKey(searchname)) {
					rerevalue = new Float(nodererelation.get(searchname));
				}
				// 两值求和
				float allrevalue = revalue + rerevalue;
				// 将新求和的值加入到orderrelation
				orderrelation.put(i + "", searchname + "-" + allrevalue);
				// 记录已经添加过的术语
				allnoderelation.put(searchname, allrevalue + "");
				// allsourcelist.add(searchname);
				// 循环判断以排序
				for (int t = 1; t < i; t++) {
					String temprelation = orderrelation.get(t + "");
					// System.out.println("i="+i);
					// System.out.println("t="+t);
					// System.out.println("temprelation="+temprelation);

					String tempname = temprelation.substring(0, temprelation
							.indexOf("-"));
					float tempvalue = new Float(temprelation
							.substring(temprelation.indexOf("-") + 1));
					// System.out.println("tempvalue="+tempvalue);
					if (allrevalue > tempvalue) {// System.out.println(
													// "orderrelation.put("+t+","+
													// searchname+"-"+allrevalue+")");
					// System.out.println( "orderrelation.put("+i+","+
					// tempname+"-"+tempvalue+")");
						orderrelation
								.put(t + "", searchname + "-" + allrevalue);
						orderrelation.put(i + "", tempname + "-" + tempvalue);
						searchname = tempname;
						allrevalue = tempvalue;
					}
				}
				i++;
			}
		}
		// 遍历反向的结果
		for (int u = 0; u < resourcelist.size(); u++) {
			String searchname = (String) resourcelist.get(u);
			float revalue = 0;
			float rerevalue = 0;
			if (!allnoderelation.containsKey(searchname)) {
				if (noderelation.containsKey(searchname)) {
					revalue = new Float(noderelation.get(searchname));
				}
				if (nodererelation.containsKey(searchname)) {
					rerevalue = new Float(nodererelation.get(searchname));
				}
				float allrevalue = revalue + rerevalue;
				// 将新求和的值加入到orderrelation
				orderrelation.put(i + "", searchname + "-" + allrevalue);
				allnoderelation.put(searchname, allrevalue + "");
				// allsourcelist.add(searchname);
				// 循环判断以排序
				for (int t = 1; t < i; t++) {
					String temprelation = orderrelation.get(t + "");
					String tempname = temprelation.substring(0, temprelation
							.indexOf("-"));
					float tempvalue = new Float(temprelation
							.substring(temprelation.indexOf("-") + 1));
					if (allrevalue > tempvalue) {
						orderrelation
								.put(t + "", searchname + "-" + allrevalue);
						orderrelation.put(i + "", tempname + "-" + tempvalue);
						searchname = tempname;
						allrevalue = tempvalue;
					}
				}
				i++;

			}
		}

	}

//	public static void main(String args[]) {
//		SAXReader reader = new SAXReader();
//		Document doc = null;
//		try {
//			doc = reader
//					.read((new File(
//							"E:\\apache-tomcat-6.0.18\\webapps\\MapleTr\\KnowledgeMap\\Resource\\test.owl")));
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		String tag = "固体";
//
//		List list = doc.selectNodes("//*[@Resouce*=" + tag + "]");
//
//		System.out.println(list.size());
//	}
	private Map<String,String> m_edges;
	public Map<String,String> getM_edges() {
		return m_edges;
	}
	public Hashtable<String, String> getOrderrelation() {
		return orderrelation;
	}
	/* (non-Javadoc)
	 * @see com.rfsoft.caltksp.service.tag.impl.TagReleateService#setOrderrelation(java.lang.String)
	 */
	public void setOrderrelation(String tag) {
		m_edges=new HashMap<String,String>();
		//初始化hash表结构的对象
		m_nodesadded= new Hashtable<String, String>();
		rem_nodesadded= new Hashtable<String, String>();
		noderelation= new Hashtable<String, String>();
		nodererelation= new Hashtable<String, String>();
		allnoderelation= new Hashtable<String, String>();
		orderrelation= new Hashtable<String, String>();
		//读取本体文件所在路径
		String p_OWLFile =Constants.LOCAL_ONTO_FILE_PATH;
        //创建本体模型
		m_model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        //读取问津
		InputStream in = FileManager.get().open(p_OWLFile);
		//如果in为null 则抛出异常
		if (in == null) {
			throw new IllegalArgumentException("File: " + p_OWLFile+ " not found");
		}

		m_model.read(in, "");
//	 tag = "液体火箭发动机";
		foucsname=tag;
		//读取查询本体库中所有类和实例
		String strQuery = "";

		strQuery = "PREFIX rdf:"
				+ " <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
				+ "PREFIX base:<http://www.owl-ontologies.com/unnamed.owl#> "
				+ "SELECT ?x"
				+ " WHERE { "
				// + "OPTIONAL { ?x rdf:type base:通用术语.}"
				// + "?x rdf:type base:?y "
				// + "?x rdf:type owl:Class ."
				// + "OPTIONAL { ?x rdf:type ?y } "
				// +" FILTER (REGEX(?y '通用术语' 'i')) "

				+ "?x rdf:type owl:Class ."
				+ "OPTIONAL { ?x rdfs:subClassOf?y }" 
				+ "FILTER (!bound(?y))"
				+ "FILTER (!isBlank(?x))" + "}";
//		System.out.println("strQuery=" + strQuery);
		Query query = QueryFactory.create(strQuery);
		QueryExecution qexec = QueryExecutionFactory.create(query, m_model);
		ResultSet rs = qexec.execSelect();
       //定义本体类的根
		OntClass rootClass = m_model.getOntClass("http://www.w3.org/2002/07/owl#Thing");
		//定义本体子类的属性
		Property subClassProp = m_model.getProperty("http://www.w3.org/2000/01/rdf-schema#subClassOf");
	
	    int i=0; 
	    //遍历将所有资源购入resource类型中
		while (rs.hasNext()) {
			Resource resource = rs.nextSolution().getResource("x");
			resource.addProperty(subClassProp, rootClass);
		}

		//开始正向遍历树的各个关系节点
	    //添加需要屏蔽的术语的URI
		m_uselessType = new ArrayList<String>();
		m_uselessType.add("http://www.w3.org/2002/07/owl#Class");
		m_uselessType.add("http://www.w3.org/2000/01/rdf-schema#Class");
		// Build the entire tree.
		firstsearchname=tag;
		//定义被查询的术语与自己的关系为1.0 起到初始化的作用，
		noderelation.put(tag, "1.0");
		nodererelation.put(tag, "1.0");
		//添加节点和边
//从头遍历树		
		buildGraph(rootClass, tag);
//记录已经添的节点和url
		m_nodesadded.put(firstsearchURI, firstsearchname);
		rebuildGraph(rootClass, tag);
		rem_nodesadded.put(firstsearchURI, firstsearchname);
		listsub();
		//求正反相关度之和
        setallrelation();
//    	for(int ii=1;ii<=orderrelation.size();ii++)
//    	{
//    		System.out.println(orderrelation.get(ii+""));
//    	}

	}
	
	
}
