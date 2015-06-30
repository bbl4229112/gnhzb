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

//import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;



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

public class OWLGraphtreeConverter extends Converter {
	/**
	 * The created Prefuse graph.
	 */
	private Graph m_graph;
	private int i = 0;
	private int t = 0;
	private int q = 0;
	private Edge edge =null;
	private String type ="tree";
//	private String firstsearchname ="";
//	private String firstsearchURI ="";
	
	private String[] images;
	
	

	List sourcelist= new ArrayList();;

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
	public OWLGraphtreeConverter(String p_OWLFile, boolean p_directed,
		String gtype) {
		super(p_OWLFile);
	
		type=gtype;
	//初始化	
		init(p_directed);
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
	public OWLGraphtreeConverter(OntModel p_model, boolean p_directed
			) {
		super(p_model);
		init(p_directed);
	}

	/**
	 * Initialize the graph converter.
	 * 
	 * @param p_directed
	 *            A boolean indicating whether the Prefuse graph needs to be
	 *            directed.
	 */
	private void init(boolean p_directed) {
		//记录被添加的边
		m_edges = new ArrayList<String[]>();
		//记录被添加的节点
		m_nodes = new Hashtable<String, Node>();
         //记录已经被添加的节点
		m_nodesadded= new Hashtable<String, String>();
		// Create an ArrayList which contains URI's of nodes we do not want to
		// visualize, because they are too general.
		//记录不想被展示的uri 因为他们每个owl都有太普遍了所以屏蔽掉
		m_uselessType = new ArrayList<String>();
		m_uselessType.add("http://www.w3.org/2002/07/owl#Class");
		m_uselessType.add("http://www.w3.org/2000/01/rdf-schema#Class");


		
		images = new String[20];
		images[0]=edu.zju.cims201.GOF.util.Constants.ONTO_IMG_FILE_PATH+ "/root.gif";
		images[1]=edu.zju.cims201.GOF.util.Constants.ONTO_IMG_FILE_PATH+ "/r.gif";
		for (int i = 2; i < 20; i++)
		{	
			
			images[i] = edu.zju.cims201.GOF.util.Constants.ONTO_IMG_FILE_PATH+"/"+String.valueOf(i) + ".gif";
			
		}
		// Create the graph.
		createGraph(p_directed);
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
	private void createGraph(boolean p_directed) {
		// Create a new empty graph.
		m_graph = new Graph(p_directed);
       
		// Add the appropriate columns.
		m_graph.getNodeTable().addColumn("URI", String.class);
		m_graph.getNodeTable().addColumn("name", String.class);
		m_graph.getNodeTable().addColumn("label", String.class);
		m_graph.getNodeTable().addColumn("image", String.class);
		m_graph.getNodeTable().addColumn("type", String.class);
		m_graph.getEdgeTable().addColumn("label", String.class);
	
		// Get the root node.
		OntClass rootClass = m_model.getOntClass("http://www.w3.org/2002/07/owl#Thing");

		// Build the entire tree.
//		firstsearchname=searchname;
		//添加节点和边
		buildGraph(rootClass);
//     记录已经添的节点和url
//		m_nodesadded.put(firstsearchURI, firstsearchname);
// 再次迭代罗列个实例，目的是创建各节点的边
//		listsub();
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
	private void buildGraph(OntClass p_currentClass) {
		// If there is no root node yet, one is created.
        //为图形添加结点
			Node currNode = m_graph.addNode();
			//为当前节点添加uri属性的值
			currNode.setString("URI", p_currentClass.getURI());
			//如果当前术语是Thing，则将其替换为”知识本体“
//			if (p_currentClass.getLocalName().equals("Thing"))
//				currNode.setString("name", "知识本体");
//			else
//            //不是则正常将该术语的名称赋值给当前结点
//				{
				currNode.setString("name", p_currentClass.getLocalName());
//				System.out.println("class name==="+p_currentClass.getLocalName());
//				}
            //当前节点的类型为class，是一个类型
			currNode.setString("type", "class");
	
		
		// 遍历当前类的子类
		ExtendedIterator itClasses = p_currentClass.listSubClasses(true);
		while (itClasses.hasNext()) {
			// 迭代调用buildGraph方法
			buildGraph((OntClass) itClasses.next());
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
				storeEdges(foundIndividual);
			    //将节点的各属性设置进node
				node.setString("URI", foundIndividual.getURI());
				node.setString("label", indpropertys);
				node.setString("name", foundIndividual.getLocalName());
				//if(category.containsKey(imgKey)) node.setString("image", category.get(imgKey).toString());
				//节点类型指定为individual
				node.setString("type", "individual");
				// Add this node to the nodes ArrayList.将结点添加到节点列表中
			//	System.out.println("$$$$m_nodes.put=="+foundIndividual.getURI());
				m_nodes.put(foundIndividual.getURI(), node);
				//用strore方法添加边
			
			}
		}
		
	    //将当前节点添加到m_nodes列表中，其中p_currentClass.getURI()是key
	//	System.out.println("&&&&m_nodes.put=="+p_currentClass.getURI());
		m_nodes.put(p_currentClass.getURI(), currNode);
	    //用storeEdges方法为节点添加边
		storeEdges(p_currentClass);
	
	}

	
//	private void buildGraph2(OntClass p_currentClass, String searchname) {
//
//			storeEdges(p_currentClass,searchname);
//
//		ExtendedIterator itClasses = p_currentClass.listSubClasses(true);
//		while (itClasses.hasNext()) {
//	
//			buildGraph2((OntClass) itClasses.next(), searchname);
//		}
//
//		// Walk trough the instances of the current class.
//		// 遍历类别实例
//		ExtendedIterator itIndividuals = p_currentClass.listInstances();
//
//
//		while (itIndividuals.hasNext()) {
//			Individual foundIndividual = (Individual) itIndividuals.next();
//
//			// Only visualize nodes which have a (valid) URI. So no blank nodes.
//			if (foundIndividual.getURI() != null) {
//
//				storeEdges(foundIndividual,searchname);
//			}
//		}
//	
//	}
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
	private void storeEdges(OntResource p_resource) {
	

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
					String targetURI = ((Resource) property.getObject()).getURI();
					String targetName= ((Resource) property.getObject()).getLocalName();

					float relation= 0; 
					if(localName.indexOf("--")!=-1){
						  String rel=localName.substring(localName.indexOf("--")+2);
						  relation=new   Float(rel);   
						  localName=localName.substring(0,localName.indexOf("--"));
						  }
					if (!m_uselessType.contains(targetURI) &&(!targetName.equals(sourceName))&& targetURI != null) {//&&(sourceName.equals(searchname))
					//如果节点目标不为自己，同时不在被屏蔽的uri中，uri不为空，且该结点术语刚好等于他自己 将firstsearchURI设为其uri
			         //     firstsearchURI=sourceURI;
			          	
					
						  
					
					if(!m_nodesadded.containsKey(targetURI)){
						//如果该节点还没有添加到已添加的节点类表，则分别记录节点名称
				
						String[] edge = new String[3];
						edge[0] = sourceURI;
						edge[1] = localName;
						edge[2] = targetURI;
//					    if(sourceName.equals("术语")){
//						System.out.println("#####首次节点####");
//						System.out.println("实例名称是："+sourceName);
//						System.out.println("关注的是关系是："+localName);
//						System.out.println("关系指向的是："+targetName);
//					}
						//System.out.println("他们之间的正向相关度是："+relation);
						//将边的各种属性和值添加到边的类表中去
						
						
						m_edges.add(edge);
						//将targetname添加到sourcelist里 
						//sourcelist.add(targetName);
						//同时对已添加节点列表添加一个节点，表示这个target已经被添加了
				         m_nodesadded.put(targetURI, targetName);
					
					}
					else
					{//这时说明该节点已经被添加到其它添加的节点上
						//解决节点循环的问题  
						if(type.equals("tree")){
							//避免url重复，在其后面添加一个数字变量q
						//targetURI=targetURI+q;
						   //同样添加节点 
						Node node = m_graph.addNode();
						node.setString("URI",targetURI+q);
			
                    String indproperty="<Strong><font color="+
                    Integer.toHexString(Constants.NODE_COLOR_SELECTED & 0x00ffffff)+"><br>   请参看节点:" +targetName+ "</font></Strong>"; 	
                        node.setString("label", indproperty);
						node.setString("name", targetName+"*");
						node.setString("type", "class");
						
        				m_nodes.put(targetURI+q, node);
						q++;
						}
//						System.out.println("----重复节点----");
//						System.out.println("实例名称是："+sourceName);
//						System.out.println("关注的是关系是："+localName);
//						System.out.println("关系指向的是："+targetName);
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
//private void listsub()
//{
//	String key="";
//
//	for(int u=0;u<sourcelist.size();u++){
//
//			//对第一次添加的节点再次循环遍历其子类或者实例 从而构建树
//			OntClass rootClass = m_model.getOntClass("http://www.w3.org/2002/07/owl#Thing");
//			key=sourcelist.get(u).toString();
//			buildGraph2(rootClass,key);					
//
//	}	
//
//}
	/**
	 * Create edges for the relevant properties of the resource.
	 */
	// 创建边 并对边添加相关的关系说明 findIn

	private void createEdges() {
		for (int i = 0; i < m_edges.size(); i++) {
			String[] strEdge = m_edges.get(i);			
			Node source = m_nodes.get(strEdge[0]);
			strEdge[0]=source.getString("name");
	//		System.out.println(strEdge[0]+"---->"+strEdge[1]+"---->"+strEdge[2]);
			//System.out.println(strEdge[2]);
			Node target = m_nodes.get(strEdge[2]);	
		//	System.out.println("###"+strEdge[2]+"=="+target.getString("name"));
			strEdge[2]=target.getString("name");
			Node relation = null;
			if(target.getString("type").equals("class"))
			    target.setString("image", this.images[3]);
			else
				target.setString("image", this.images[2]);
		
			//处理非关系节点
			if(m_nodes.containsKey(strEdge[0]+"的"+strEdge[1]))
			{ 
			//	System.out.println("***"+strEdge[0]+"的"+strEdge[1]+"是"+strEdge[2]);
				//relation=m_graph.getTargetNode(edge);
			relation=m_nodes.get(strEdge[0]+"的"+strEdge[1]);
				 String indproperty=relation.getString("label");
				 indproperty+="<Strong><font color="+
    	 Integer.toHexString(Constants.NODE_COLOR_SELECTED & 0x00ffffff)+"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+strEdge[2]+ "</font></Strong><br>";
			//	 System.out.println("属性描述="+indproperty);
				 relation.setString("label", indproperty);
		//m_graph.getTargetNode(edge).setString("label", indproperty);
		// edge=m_graph.addEdge(source, relation);
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
	//		System.out.println("@@@@@"+strEdge[0]+"的"+strEdge[1]+"是"+strEdge[2]);
			m_graph.addEdge(source, relation);
		    m_graph.addEdge(relation, target);
				}
			}
		

		}
	}
	 public Hashtable<String, Node> getM_nodes()
	    {
	    	return this.m_nodes;
	    	
	    }

	public static void main(String[] args)
	{
		OWLGraphtreeConverter cvt=new OWLGraphtreeConverter("prefuse/owl2prefuse/test.owl", true, "运载火箭");
		cvt.getGraph();
	}
}