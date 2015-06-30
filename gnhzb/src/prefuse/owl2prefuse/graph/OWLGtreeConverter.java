package prefuse.owl2prefuse.graph;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.LiteralImpl;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

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

public class OWLGtreeConverter extends Converter {
	/**
	 * The created Prefuse graph.
	 */
	private Graph m_graph;
	private int i = 0;
	private int t = 0;
	private int q = 0;
	private String firstsearchname ="";
	private String firstsearchURI ="";
	
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
	public OWLGtreeConverter(String p_OWLFile, boolean p_directed,
			String searchname) {
		super(p_OWLFile);
	//	System.out.println("searchname===1======"+searchname);
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
	public OWLGtreeConverter(OntModel p_model, boolean p_directed,
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

		// Get the root node.
		OntClass rootClass = m_model
				.getOntClass("http://www.w3.org/2002/07/owl#Thing");

		// Build the entire tree.
		firstsearchname=searchname;
		buildGraph(rootClass, searchname);
		//buildGraph1(rootClass, searchname);
		m_nodesadded.put(firstsearchURI, firstsearchname);
		listsub();
		System.out.println("创建知识图谱");
		m_graph.setFocusindex(t);
		// All the edges are stored in an ArrayList, because they can only be
		// added
		// if all the appropriate nodes exist. At this point this is the case,
		// so
		// all the nodes are created.
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
	//	System.out.println("searchname==============="+searchname);
	//	if (!p_currentClass.getLocalName().equals("扩展术语")) {
			Node currNode = m_graph.addNode();
			// System.out.println(p_currentClass.getLocalName()+"的nodeindex是"+i);
			if (p_currentClass.getLocalName().equals(searchname))
	      	t = i;
		//	System.out.println("(p_currentClass.getLocalName()="+p_currentClass.getLocalName());
		//	System.out.println("i="+i);
		
			i++;
			currNode.setString("URI", p_currentClass.getURI());
			// System.out.println(" p_currentClass.getURI()="+
			// p_currentClass.getURI());
			if (p_currentClass.getLocalName().equals("Thing"))
				currNode.setString("name", "知识本体");
			else
				currNode.setString("name", p_currentClass.getLocalName());
			// System.out.println("name="+p_currentClass.getLocalName());
			currNode.setString("type", "class");
			
			currNode.setString("image", "./images/0060929871.01.MZZZZZZZ.jpg");
			//
			// Add this node to the nodes ArrayList.
			m_nodes.put(p_currentClass.getURI(), currNode);
		//	m_OntResource.put(p_currentClass.getURI(), p_currentClass);
			// Add the edges, connected to this node, to the edges ArrayList.
			storeEdges(p_currentClass,searchname);
	//	}
		// Walk trough the subclasses of the current class.
		ExtendedIterator itClasses = p_currentClass.listSubClasses(true);
		while (itClasses.hasNext()) {
			// Recurse trough the subclasses of the current node.
			buildGraph((OntClass) itClasses.next(), searchname);
		}

		// Walk trough the instances of the current class.
		// 遍历类别实例
		ExtendedIterator itIndividuals = p_currentClass.listInstances();
		// ExtendedIterator itIndividuals=
		// p_currentClass.listDeclaredProperties();

		while (itIndividuals.hasNext()) {
			Individual foundIndividual = (Individual) itIndividuals.next();

			// Only visualize nodes which have a (valid) URI. So no blank nodes.
			if (foundIndividual.getURI() != null) {
				// Create the node for this instance.
				Node node = m_graph.addNode();
				if (foundIndividual.getLocalName().equals(searchname))
					t = i;
	//System.out.println("foundIndividual.getLocalName()="+foundIndividual.getLocalName());
//	System.out.println("i="+i);
				  i++;
				// 获得实例的各个属性
				String indpropertys = "";
				StmtIterator itProperties = foundIndividual.listProperties();
				while (itProperties.hasNext()) {
					Statement property = itProperties.nextStatement();
					if (!property.getObject().isResource()) {
						indpropertys += "<Strong><font color="
								+ Integer
										.toHexString(Constants.NODE_COLOR_SELECTED & 0x00ffffff)
								+ ">" + property.getPredicate().getLocalName()
								+ "：</font></Strong>";
						indpropertys += ((LiteralImpl) property.getObject())
								.getValue()
								+ "   <br> <br>         ";
					}
				}
				node.setString("URI", foundIndividual.getURI());
				//    
				// System.out.println("indpropertys="+indpropertys);

				node.setString("label", indpropertys);
				node.setString("name", foundIndividual.getLocalName());
				node.setString("type", "individual");

				// Add this node to the nodes ArrayList.
				m_nodes.put(foundIndividual.getURI(), node);
		//		m_OntResource.put(foundIndividual.getURI(), foundIndividual);
				
				// Add the edges, connected to this node, to the edges
				// ArrayList.
				storeEdges(foundIndividual,searchname);
			}
		}
	
	}

	
	private void buildGraph2(OntClass p_currentClass, String searchname) {

	//	if (!p_currentClass.getLocalName().equals("扩展术语")) {

			storeEdges(p_currentClass,searchname);
	//	}
		// Walk trough the subclasses of the current class.
		ExtendedIterator itClasses = p_currentClass.listSubClasses(true);
		while (itClasses.hasNext()) {
			// Recurse trough the subclasses of the current node.
			buildGraph2((OntClass) itClasses.next(), searchname);
		}

		// Walk trough the instances of the current class.
		// 遍历类别实例
		ExtendedIterator itIndividuals = p_currentClass.listInstances();


		while (itIndividuals.hasNext()) {
			Individual foundIndividual = (Individual) itIndividuals.next();

			// Only visualize nodes which have a (valid) URI. So no blank nodes.
			if (foundIndividual.getURI() != null) {

				storeEdges(foundIndividual,searchname);
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
	List sourcelist= new ArrayList();
	private void storeEdges(OntResource p_resource,String searchname) {
	
//	System.out.println("re     searchname1======="+searchname);
		String sourceURI = p_resource.getURI();
		String sourceName= p_resource.getLocalName();
//		System.out.println("实例名称是++++++++++++++++++++++"+sourceName);
//	System.out.println("sourceName="+sourceName);
		// Property properys=(Property)p_resource;
		if (!m_uselessType.contains(sourceURI)) {
			StmtIterator itProperties = p_resource.listProperties();

			while (itProperties.hasNext()) {
		
				Statement property = itProperties.nextStatement();
				// p_resource.listPropertyValues(properys);
				String localName = property.getPredicate().getLocalName();
//				  if(localName.indexOf("--")!=-1)
//					  localName=localName.substring(0,localName.indexOf("--"));
			
				if (property.getObject().isResource()) {
			
					String targetURI = ((Resource) property.getObject()).getURI();
					String targetName= ((Resource) property.getObject()).getLocalName();
//					System.out.println("targetURI="+targetURI);
//					System.out.println("targetName="+targetName);
//					System.out.println("sourceName"+sourceName);
				//	System.out.println("targetURI="+targetURI);
			
					if (!m_uselessType.contains(targetURI) &&(!targetName.equals(sourceName))&& targetURI != null&&(targetName.equals(searchname))) {
					
			              firstsearchURI=targetURI;
					
//						System.out.println("sourceURI="+sourceURI);
//						System.out.println("sourceName"+sourceName);
//						System.out.println("targetURI="+targetURI);
//						System.out.println("targetName="+targetName);
//					if(!m_nodesadded.containsKey(targetURI))
//					{
//						m_nodesadded.put(targetURI, targetName);
//					}
					if(!m_nodesadded.containsKey(sourceURI)){
						float relation= 0;
						  if(localName.indexOf("--")!=-1){
							  String rel=localName.substring(localName.indexOf("--")+2);
							  relation=new   Float(rel);   
							  localName=localName.substring(0,localName.indexOf("--"));
							  }
//						System.out.println("实例名称是："+sourceName);
//						System.out.println("关注的是关系是："+localName);
//						System.out.println("关系指向的是："+targetName);
//						System.out.println("他们之间的反向相关度是："+relation);
						String[] edge = new String[3];

						edge[0] = sourceURI;
						edge[1] = localName;
						edge[2] = targetURI;
						m_edges.add(edge);
						sourcelist.add(sourceName);
				       m_nodesadded.put(sourceURI, sourceName);
					
					}
					else
					{//解决节点循环的问题
						System.out.println("解决节点循环的问题");
						Node node = m_graph.addNode();
						node.setString("URI",sourceURI+q);
			
                    String indproperty="<Strong><font color="+
                    Integer.toHexString(Constants.NODE_COLOR_SELECTED & 0x00ffffff)+
                    "><br><br>   请参看节点：" +sourceName+ "</font></Strong>";

						node.setString("label", indproperty);
						node.setString("name", sourceName+"*");
						node.setString("type", "");
						
				
						m_nodes.put(sourceURI+q, node);
						String[] edge = new String[3];
						edge[0] = sourceURI+q;
						edge[1] = localName;
						edge[2] = targetURI;
						m_edges.add(edge);
						q++;
					}
					//		Individual  ontsource = m_OntResource.get(sourceURI);
				
						//storeEdges(ontsource,sourceName);
						
					// Build the entire tree.
			
					
					}
				} else {
			//		System.out.println(((LiteralImpl)property.getObject()).getValue());
				}
			}
		}


	}
private void listsub()
{

		for(int u=0;u<sourcelist.size();u++){
//			System.out.println(" sourcelist.size()="+ sourcelist.size());
//			System.out.println(" sourcelist.get(u).toString()="+ sourcelist.get(u).toString());
			OntClass rootClass = m_model.getOntClass("http://www.w3.org/2002/07/owl#Thing");
	//		m_nodesadded.put(targetURI, (Resource) property.getObject());
			buildGraph2(rootClass, sourcelist.get(u).toString());					
//		}	
	}	
}
	/**
	 * Create edges for the relevant properties of the resource.
	 */
	// 创建边 并对边添加相关的关系说明
	private void createEdges() {
		for (int i = 0; i < m_edges.size(); i++) {
			String[] strEdge = m_edges.get(i);
			// System.out.println("strEdge[0]="+strEdge[0]);
			// System.out.println("strEdge[1]="+strEdge[1]);
			// System.out.println("strEdge[2]="+strEdge[2]);
//			if (strEdge[0]
//					.equals("http://www.owl-ontologies.com/unnamed.owl#扩展术语")) {
//				continue;
//			}
//			if (strEdge[2]
//					.equals("http://www.owl-ontologies.com/unnamed.owl#扩展术语")) {
//				continue;
//			}
			// Get the source and the target node.
			Node source = m_nodes.get(strEdge[0]);
			Node target = m_nodes.get(strEdge[2]);

			Edge edge = m_graph.addEdge(source, target);

			if (strEdge[1].equals("type"))
				strEdge[1] = "实例";
			if (strEdge[1].equals("subClassOf"))
				strEdge[1] = "父类";
			edge.setString("label", strEdge[1]);
			// System.out.println(strEdge[0]+"是"+strEdge[2]+"的"+strEdge[1]);

		}
	}

	public static void main(String[] args)
	{
	
		OWLGtreeConverter cvt=new OWLGtreeConverter("E:/java/Jena-2.5.5/testing/reasoners/bugs/wine.owl",true,"固体推进剂");
		Graph graph=cvt.getGraph();
	}
}