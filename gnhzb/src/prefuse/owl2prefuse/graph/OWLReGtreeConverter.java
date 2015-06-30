package prefuse.owl2prefuse.graph;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntResource;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.LiteralImpl;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;



import prefuse.owl2prefuse.Constants;
import prefuse.owl2prefuse.Converter;
import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;

/**
 * This class converts the given OWL Model to a Prefuse graph datastructure.
 * <p/> Project OWL2Prefuse <br/> OWLGraphConverter.java created 3 januari 2007,
 * 9:58 <p/> Copyright &copy 2006 Jethro Borsje
 * 
 * @author <a href="mailto:info@jborsje.nl">Jethro Borsje</a>
 * @version $$Revision: 1.2 $$, $$Date: 2009/09/22 12:42:27 $$
 */

public class OWLReGtreeConverter extends Converter {
	/**
	 * The created Prefuse graph.
	 */
	private Graph m_graph;
	private int i = 0;
	private int t = 0;
	private int q = 0;
	private Edge edge =null;
	private String type ="tree";
	private String firstsearchname ="";
	private String firstsearchURI ="";
	
	private String[] images;
	
	private int img_indx;
	//private String appUrl;
	List sourcelist= new ArrayList();;
	//private Map imgSrc=null;
	/**
	 * An ArrayList containing all the edges that have to be added to the
	 * Prefuse graph.
	 */
	private ArrayList<String[]> m_edges;

	/**
	 * An Hashtable containing all the nodes in Prefuse graph.
	 */
	private Hashtable<String, Node> m_nodes;
	private Hashtable<String, String> m_nodesadded;
//	private Hashtable<String, Individual> m_OntResource;
	/**
	 * An ArrayList containing the URI's of OWL classes that should not be
	 * converted into the Prefuse graph.
	 */
	private ArrayList<String> m_uselessType;

	/**
	 * Creates a new instance of OWLGraphConverter
	 * 
	 * @param p_OWLFile
	 *            The path to the OWL file that needs to be converted.
	 * @param p_directed
	 *            A boolean indicating whether the Prefuse graph needs to be
	 *            directed.
	 */
	public OWLReGtreeConverter(String p_OWLFile, boolean p_directed,
			String searchname,String gtype) {
		super(p_OWLFile);
		//this.appUrl=appUrl;
		type=gtype;
		init(p_directed, searchname);
	}

	/**
	 * Creates a new instance of OWLGraphConverter
	 * 
	 * @param p_model
	 *            The Jena model that needs to be converted.
	 * @param p_directed
	 *            A boolean indicating whether the Prefuse graph needs to be
	 *            directed.
	 */
	public OWLReGtreeConverter(OntModel p_model, boolean p_directed,
			String searchname) {
		super(p_model);
		init(p_directed, searchname);
	}

	/**
	 * Initialize the graph converter.
	 * 
	 * @param p_directed
	 *            A boolean indicating whether the Prefuse graph needs to be
	 *            directed.
	 */
	private void init(boolean p_directed, String searchname) {
		m_edges = new ArrayList<String[]>();
		m_nodes = new Hashtable<String, Node>();
	//	m_OntResource=new Hashtable<String, Individual>() ;
		m_nodesadded= new Hashtable<String, String>();
		// Create an ArrayList which contains URI's of nodes we do not want to
		// visualize, because they are too general.
		m_uselessType = new ArrayList<String>();
		m_uselessType.add("http://www.w3.org/2002/07/owl#Class");
		m_uselessType.add("http://www.w3.org/2000/01/rdf-schema#Class");
	//	m_uselessType.add("http://www.owl-ontologies.com/unnamed.owl#通用术语");

		String imgUrl= edu.zju.cims201.GOF.util.Constants.ONTO_IMG_FILE_PATH+"/";
		images = new String[20];
		images[0]=imgUrl+ "root.gif";
		images[1]=imgUrl+ "r.gif";
		for (int i = 2; i < 20; i++)
		{	
			
			images[i] = imgUrl+String.valueOf(i) + ".gif";
			//System.out.println("图片地址"+images[i]);
		}
		// Create the graph.
		createGraph(p_directed, searchname);
	}

	/**
	 * Return the created Prefuse graph.
	 * 
	 * @return The created Prefuse graph.
	 */
	public Graph getGraph() {

	
		return m_graph;
	}

	/**
	 * Create the Prefuse graph. This method creates an empty graph and adds the
	 * appropriate columns to the node- and edgestable. After that it gets the
	 * root class (owl:Thing) of the OWL graph and recursively starts building
	 * the graph from there. This method is automatically called from the
	 * constructors of this converter.
	 * 
	 * @param p_directed
	 *            A boolean indicating whether the Prefuse graph needs to be
	 *            directed.
	 */
	private void createGraph(boolean p_directed, String searchname) {
		// Create a new empty graph.
		m_graph = new Graph(p_directed);
       
		// Add the appropriate columns.
		m_graph.getNodeTable().addColumn("URI", String.class);
		m_graph.getNodeTable().addColumn("name", String.class);
		m_graph.getNodeTable().addColumn("label", String.class);
		m_graph.getNodeTable().addColumn("image", String.class);
		m_graph.getNodeTable().addColumn("type", String.class);
		m_graph.getEdgeTable().addColumn("label", String.class);
		img_indx=1;
		// Get the root node.
		OntClass rootClass = m_model.getOntClass("http://www.w3.org/2002/07/owl#Thing");

		// Build the entire tree.
		firstsearchname=searchname;
		//添加节点和边
		buildGraph(rootClass, searchname);
//     记录已经添的节点和url
		m_nodesadded.put(firstsearchURI, firstsearchname);
// 再次迭代罗列个实例，目的是创建个节点的边
		listsub();
		//System.out.println("查询术语的index是"+t);
		//将查询的术语设为焦点
		m_graph.setFocusindex(t);
		// All the edges are stored in an ArrayList, because they can only be
		// added
		// if all the appropriate nodes exist. At this point this is the case,
		// so
		// all the nodes are created.
		//根据之前存储的边和节点的内容创建边
		createEdges();
	}

	/**
	 * Build the Prefuse graph, this method is called recursively.
	 * 
	 * @param p_currentClass
	 *            The class which is being added to the graph.
	 */
	//
	private void buildGraph(OntClass p_currentClass, String searchname) {
		// If there is no root node yet, one is created.
        //为图形添加结点
			Node currNode = m_graph.addNode();
			// System.out.println(p_currentClass.getLocalName()+"的nodeindex是"+i);
			//如果当前的术语名称就是查询的术语名称，则记录的查询index就是i
			if (p_currentClass.getLocalName().equals(searchname))
	      	t = i;
			//如果不是则 i+1；
			i++;
			//为当前节点添加uri属性的值
			currNode.setString("URI", p_currentClass.getURI());
			//如果当前术语是Thing，则将其替换为”知识本体“
			if (p_currentClass.getLocalName().equals("Thing"))
				currNode.setString("name", "知识本体");
			else
            //不是则正常将该术语的名称赋值给当前结点
				currNode.setString("name", p_currentClass.getLocalName());
            //当前节点的类型为class，是一个类型
			currNode.setString("type", "class");
		    //将当前节点添加到m_nodes列表中，其中p_currentClass.getURI()是key
			m_nodes.put(p_currentClass.getURI(), currNode);
		    //用storeEdges方法为节点添加边
			storeEdges(p_currentClass,searchname);
		
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
				Node node = m_graph.addNode();
				//System.out.println("当前实例名称是"+foundIndividual.getLocalName());
				if (foundIndividual.getLocalName().equals(searchname))
				{		//如果当前实例是的名称和要查询的术语一致，则记录要定位焦点的index
				  t = i;
				  
				 node.setString("image", images[0]);
				}
				i++;
				//imgSrc=new HashMap();
				// 遍历各实例的各个属性，用于呈现在图像的右侧配合解释说明
				String indpropertys = "";
				String imgKey="";
				StmtIterator itProperties = foundIndividual.listProperties();
				while (itProperties.hasNext()) {
					Statement property = itProperties.nextStatement();
					if (!property.getObject().isResource()) {
						indpropertys += "<Strong><font color="	+ Integer.toHexString(Constants.NODE_COLOR_SELECTED & 0x00ffffff)+ ">" + property.getPredicate().getLocalName()+ "：</font></Strong>";
						indpropertys += ((LiteralImpl) property.getObject()).getValue()+ "   <br>          ";
						
		
						}
					if(property.getPredicate().getLocalName().indexOf("--")!=-1){
						imgKey=property.getPredicate().getLocalName();
						//System.out.println(imgKey);
							String rel=imgKey.substring(imgKey.indexOf("--")+2);
							Float  relation=new   Float(rel);   
							imgKey=imgKey.substring(0,imgKey.indexOf("--"));
						}
				}
				storeEdges(foundIndividual,searchname);
			    //将节点的各属性设置进node
				node.setString("URI", foundIndividual.getURI());
				node.setString("label", indpropertys);
				node.setString("name", foundIndividual.getLocalName());
				//if(category.containsKey(imgKey)) node.setString("image", category.get(imgKey).toString());
				//节点类型指定为individual
				node.setString("type", "individual");
				// Add this node to the nodes ArrayList.将结点添加到节点列表中
				m_nodes.put(foundIndividual.getURI(), node);
				//用strore方法添加边
			
			}
		}
	
	}

	
	private void buildGraph2(OntClass p_currentClass, String searchname,String imgUrl) {



			storeEdges(p_currentClass,searchname,imgUrl);

		ExtendedIterator itClasses = p_currentClass.listSubClasses(true);
		while (itClasses.hasNext()) {
	
			buildGraph2((OntClass) itClasses.next(), searchname,imgUrl);
		}

		// Walk trough the instances of the current class.
		// 遍历类别实例
		ExtendedIterator itIndividuals = p_currentClass.listInstances();


		while (itIndividuals.hasNext()) {
			Individual foundIndividual = (Individual) itIndividuals.next();

			// Only visualize nodes which have a (valid) URI. So no blank nodes.
			if (foundIndividual.getURI() != null) {

				storeEdges(foundIndividual,searchname,imgUrl);
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
	// Map category=new HashMap();
	@SuppressWarnings("unchecked")
	private void storeEdges(OntResource p_resource,String searchname,String...imgs) {
	
  //       System.out.println("现在查询的术语是"+searchname);
		String sourceURI = p_resource.getURI();
		String sourceName= p_resource.getLocalName();

        //判断当前术语的url是否是被屏蔽的术语
		if (!m_uselessType.contains(sourceURI)) {
        //如果不是将该术语的所有属性遍历出来			
			StmtIterator itProperties = p_resource.listProperties();

			while (itProperties.hasNext()) {
		
				Statement property = itProperties.nextStatement();
	
				String localName = property.getPredicate().getLocalName();
				//System.out.println(localName);
	          
				if (property.getObject().isResource()) {
                   //如果该属性是有指向性的，则列出该属性指向的术语名称			
//					  System.out.println("有指向属性的名称："+localName);
//					  if(localName.indexOf("--")!=-1)
//					  localName=localName.substring(0,localName.indexOf("--"));
					String targetURI = ((Resource) property.getObject()).getURI();
					String targetName= ((Resource) property.getObject()).getLocalName();
                    //System.out.println("targetURI="+targetURI);
//					System.out.println("有指向属性的targetName="+targetName);
//					System.out.println("有指向属性的sourceName"+sourceName);
                   //System.out.println("targetURI="+targetURI);
					float relation= 0; 
					if(localName.indexOf("--")!=-1){
						  String rel=localName.substring(localName.indexOf("--")+2);
						  relation=new   Float(rel);   
						  localName=localName.substring(0,localName.indexOf("--"));
//							if(category.containsKey(localName)==false)  
//							{category.put(localName, images[img_indx]);
							//img_indx++;
							//}
						  }
					if (!m_uselessType.contains(targetURI) &&(!targetName.equals(sourceName))&& targetURI != null&&(sourceName.equals(searchname))) {
					//如果节点目标不为自己，同时不在被屏蔽的uri中，uri不为空，且该结点术语刚好等于他自己 将firstsearchURI设为其uri
			              firstsearchURI=sourceURI;
			          	
					
						  
					
					if(!m_nodesadded.containsKey(targetURI)){
						//如果该节点还没有添加到已添加的节点类表，则分别记录节点名称
					
						
						String[] edge = new String[3];

						edge[0] = sourceURI;
						edge[1] = localName;
						edge[2] = targetURI;
					
//						System.out.println("实例名称是："+sourceName);
//						System.out.println("关注的是关系是："+localName);
//						System.out.println("关系指向的是："+targetName);
//						System.out.println("他们之间的正向相关度是："+relation);
						//将边的各种属性和值添加到边的类表中去
						
						
						m_edges.add(edge);
						//将targetname添加到sourcelist里 
						sourcelist.add(targetName);
//						if(sourceName.equals(searchname)==false)
						//imgSrc.put(targetName,category.get(localName)==null?"":category.get(localName).toString());
						//同时对以添加节点列表添加一个节点，表示这个target已经被添加了
				         m_nodesadded.put(targetURI, targetName);
					
					}
					else
					{//这时说明该节点已经被添加到以添加的节点上
						//解决节点循环的问题  
						if(type.equals("tree")){
							//避免url重复，在其后面添加一个数字变量q
						targetURI=targetURI+q;
						   //同样添加节点 
						Node node = m_graph.addNode();
						node.setString("URI",targetURI);
			
                    String indproperty="<Strong><font color="+
                    Integer.toHexString(Constants.NODE_COLOR_SELECTED & 0x00ffffff)+"><br>   请参看节点:" +targetName+ "</font></Strong>";
                 // if(category.containsKey(localName)) node.setString("image", category.get(localName).toString());
					 	
                        node.setString("label", indproperty);
						node.setString("name", targetName+"*");
						node.setString("type", "class");
        				m_nodes.put(targetURI, node);
						q++;
						}
						String[] edge = new String[3];
						edge[0] = sourceURI;
						edge[1] = localName;
						edge[2] = targetURI;
						m_edges.add(edge);
				
					}					
					}
				} else {
	
				}
			}
		}


	}
private void listsub()
{
	String key="";
	String imgUrl="";
	for(int u=0;u<sourcelist.size();u++){
		//	System.out.println(" sourcelist的大小="+ sourcelist.size());
		//	System.out.println(" 当前sourcelist的值是"+ sourcelist.get(u).toString());
			//对第一添加的节点再次循环遍历其子类或者实例 从而构建树
			OntClass rootClass = m_model.getOntClass("http://www.w3.org/2002/07/owl#Thing");
			key=sourcelist.get(u).toString();
//			if(imgSrc.containsKey(key))
//				imgUrl=imgSrc.get(key).toString();
			buildGraph2(rootClass,key ,"");					

	}	
//		String key = "";
//		for (Iterator it = imgSrc.keySet().iterator(); it.hasNext();) {
//			// System.out.println(" sourcelist的大小="+ sourcelist.size());
//			// System.out.println(" 当前sourcelist的值是"+
//			// sourcelist.get(u).toString());
//			// 对第一添加的节点再次循环遍历其子类或者实例 从而构建树
//			OntClass rootClass = m_model
//					.getOntClass("http://www.w3.org/2002/07/owl#Thing");
//			key = it.next().toString();
//			buildGraph2(rootClass, key, imgSrc.get(key).toString());
//
//		}
}
	/**
	 * Create edges for the relevant properties of the resource.
	 */
	// 创建边 并对边添加相关的关系说明
//private boolean branch=false;
//private Map types=new HashMap();
//int tt=2;
	private void createEdges() {
		for (int i = 0; i < m_edges.size(); i++) {
			String[] strEdge = m_edges.get(i);			
			Node source = m_nodes.get(strEdge[0]);
			strEdge[0]=source.getString("name");
			Node target = m_nodes.get(strEdge[2]);
			
			strEdge[2]=target.getString("name");
			Node relation = null;
			
			//if(branch==false)
			//{
			//	if(source.getString("name").equals(this.firstsearchname))
			//	{
					
			//		if(types.containsKey(strEdge[1])==false)
			//		{
			//			types.put(strEdge[1], this.images[tt]);			
			//			tt++;
			//		}
			//System.out.println(target.getString("name")+"="+target.getString("type"));
			if(target.getString("type").equals("class"))
					target.setString("image", this.images[3]);
			else
				target.setString("image", this.images[2]);
			//	}
			//	else{
			//		target.setString("image", source.getString("image"));
			//		branch=true;
			//	}
				
			//}
			//else{
			//target.setString("image", source.getString("image"));
			//}
			//处理非关系节点
			if(m_nodes.containsKey(strEdge[0]+"的"+strEdge[1]))
			{ 
				relation=m_graph.getTargetNode(edge);
				 String indproperty=relation.getString("label");
				 indproperty+="<Strong><font color="+
    	 Integer.toHexString(Constants.NODE_COLOR_SELECTED & 0x00ffffff)+"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+strEdge[2]+ "</font></Strong><br>";
			//	 System.out.println("属性描述="+indproperty);
		 m_graph.getTargetNode(edge).setString("label", indproperty);
		 edge=	 m_graph.addEdge(source, relation);
		 m_graph.addEdge(relation, target);
			}
			//处理关系节点
			else{
				if(!strEdge[1].equals("type")&&!strEdge[1].equals("subClassOf")){
			Node node = m_graph.addNode();	
			node.setString("URI",strEdge[0]+"的"+strEdge[1]);
            String indproperty="<Strong><font color="+
            Integer.toHexString(Constants.NODE_COLOR_SELECTED & 0x00ffffff)+"> "+strEdge[0]+"</font><br><font color="+Integer.toHexString(Constants.NODE_DEFAULT_COLOR & 0x00ffffff)+
        		">"+strEdge[1]+"有：</font><br><font color="+
    	    Integer.toHexString(Constants.NODE_COLOR_SELECTED & 0x00ffffff)+"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+strEdge[2]+ "</font></Strong><br>";
			node.setString("label", indproperty);
			node.setString("name", strEdge[1]);
			 node.setString("image", images[1]);
			relation=node;
			m_nodes.put(strEdge[0]+"的"+strEdge[1], relation);

			 edge=	 m_graph.addEdge(source, relation);
			 m_graph.addEdge(relation, target);
				}
			}
		

		}
	}


	public static void main(String[] args)
	{
		OWLGtreeConverter cvt=new OWLGtreeConverter("http://localhost/caltks/ontofile/test.owl", true, "固体推进剂");
		cvt.getGraph();
	}
}